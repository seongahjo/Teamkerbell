package com.sajo.teamkerbell.server;

import com.google.gson.Gson;
import com.nhncorp.mods.socket.io.SocketIOServer;
import com.nhncorp.mods.socket.io.SocketIOSocket;
import com.nhncorp.mods.socket.io.impl.DefaultSocketIOServer;
import com.nhncorp.mods.socket.io.spring.DefaultEmbeddableVerticle;
import com.sajo.teamkerbell.service.AlarmService;
import com.sajo.teamkerbell.service.MinuteService;
import com.sajo.teamkerbell.service.ProjectService;
import com.sajo.teamkerbell.VO.ServerUser;
import com.sajo.teamkerbell.entity.Minute;
import com.sajo.teamkerbell.entity.Project;
import com.sajo.teamkerbell.entity.User;
import com.sajo.teamkerbell.service.UserService;
import com.sajo.teamkerbell.util.CommonUtils;
import com.sajo.teamkerbell.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.json.JsonObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class VertxServer extends DefaultEmbeddableVerticle {
    private static SocketIOServer io = null;
    private static HashMap<String, Integer> rooms = new HashMap<>(); //ProjectIdx / Wrjter_Id
    private static HashMap<String, ServerUser> clients = new HashMap<>(); // socketId,User

    private UserService userService;

    private ProjectService projectService;

    private AlarmService alarmService;

    private MinuteService minuteService;

    @Autowired
    public VertxServer(UserService userService, ProjectService projectService, AlarmService alarmService, MinuteService minuteService) {
        this.userService = userService;
        this.projectService = projectService;
        this.alarmService = alarmService;
        this.minuteService = minuteService;
    }

    private void logging(String projectIdx, String message, String userId) {
        log.info("[ROOM {}] {} [User {}]", projectIdx, message, userId);
    }

    private void logging(String projectIdx, String message) {
        log.info("[ROOM {}] {} ", projectIdx, message);
    }

    enum SocketServerType {
        WRITER("writer"),
        JOIN("join"),
        MESSAGE("msg"),
        FILE("file"),
        REFRESH("refreshToAll"),
        SAVE("save"),
        INVITE("invite");
        private String name;

        SocketServerType(String name) {
            this.name = name;
        }
    }

    enum SocketClientType {
        DELETE("deleteuser"),
        REFRESH("refresh"),
        WRITE("write"),
        RESPONSE("response"),
        ADD("adduser"),
        FINISH("finish"),
        ALARM("alarm");

        private String name;

        SocketClientType(String name) {
            this.name = name;
        }
    }

    private void join(SocketIOSocket socket, JsonObject event) {
        String projectIdx = event.getString("projectIdx");
        ServerUser su = new ServerUser(projectIdx, event.getInteger("userIdx"), event.getString("userId"), event.getString("userName"), event.getString("userImg"), socket.getId());
        clients.put(socket.getId(), su); // Socket에 해당하는 Room저장
        if (!projectIdx.equals("")) {
            logging(projectIdx, "Trying to Enter the room", su.getId());
            if (rooms.get(projectIdx) != null)//방이 존재할경우
                logging(projectIdx, "Room exists");
            else {
                //방이 존재하지 않을경우
                rooms.put(projectIdx, -1);
                log.info(projectIdx, "Room is created");
            }
            socket.join(projectIdx);
            clients.values().forEach(room -> io.sockets().in(room.getProjectIdx()).emit(SocketClientType.ADD.name, room.getId()));
            logging(projectIdx, "Entering room succeed", su.getId());
        }
    }

    private void disconnect(SocketIOSocket socket) {
        ServerUser su = clients.get(socket.getId());
        if (su != null && !su.getProjectIdx().equals("")) {
            String projectIdx = su.getProjectIdx();
            log.info("[ROOM {}] Trying to Exit the room [USER {}]", su.getId());
            socket.leave(projectIdx);
            clients.remove(socket.getId());
            //su ==> 나가는 사람
            boolean flag = clients.values().stream().anyMatch(user -> user.getName().equals(su.getName()) && user.getProjectIdx().equals(su.getProjectIdx()));
            if (flag) {
                logging(projectIdx, "Succeeding to exit room ", su.getId());
                io.sockets().in(projectIdx).emit(SocketClientType.DELETE.name, su.getName());
            }
            if (rooms.get(projectIdx) == (su.getUserIdx())) {
                rooms.replace(projectIdx, -1);
                logging(projectIdx, "Writer Initialize");
            }
            if (io.sockets().clients(projectIdx) == null) {
                rooms.remove(projectIdx);
                logging(projectIdx, "Room is deleted");
            }
        }
    }

    private static void init(Vertx vertx, HttpServer server) {
        io = new DefaultSocketIOServer(vertx, server);
    }

    @Override
    public void start(Vertx vertx) {
        int port = 9999;
        HttpServer server = vertx.createHttpServer(); //HTTP Server 생성
        init(vertx, server);
        io.sockets().onConnection(socket -> {
            socket.on(SocketServerType.JOIN.name, event -> join(socket, event)); //Join End
            socket.onDisconnect(event -> disconnect(socket)); //Disconnect End
            socket.on(SocketServerType.MESSAGE.name, event -> message(socket, event)); //Msg End
            socket.on(SocketServerType.FILE.name, event -> file(socket, event));//img end
            socket.on(SocketServerType.WRITER.name, event -> writer(socket)); //writer end
            socket.on(SocketServerType.REFRESH.name, event -> refresh(socket, event));
            socket.on(SocketServerType.SAVE.name, event -> save(socket, event)); //save end
            socket.on(SocketServerType.INVITE.name, this::invite);
        });// onConnection end
        server.listen(port);
    }

    private void invite(JsonObject event) {
        Integer userIdx = Integer.parseInt(event.getString("userIdx"));
        clients.values().forEach(user -> {
            if (user.getUserIdx().equals(userIdx))
                io.sockets().socket(user.getSocketId(), false).emit(SocketClientType.ALARM.name);
        });
    }

    private void refresh(SocketIOSocket socket, JsonObject event) {
        ServerUser su = clients.get(socket.getId());
        String memo = event.getString("memo");
        String projectIdx = su.getProjectIdx();
        io.sockets().in(projectIdx).emit(SocketClientType.REFRESH.name, memo);
    }

    private void save(SocketIOSocket socket, JsonObject event) {
        ServerUser su = clients.get(socket.getId());
        User u = userService.getUser(su.getId());
        String projectIdx = su.getProjectIdx();
        String memo = event.getString("memo");
        Project pj = projectService.getProject(Integer.parseInt(projectIdx));
        pj.setMinute(memo);

        logging(projectIdx, new StringBuilder().append("Trying to save memo ").append(memo).toString(), su.getId());

                /*
                Create Minute
                 */
        Minute minute = minuteService.getMinute(pj, new Date());
        if (minute == null) {
            minute = new Minute(memo, new Date());
            logging(projectIdx, "Minute is created", su.getId());
        }
        minute.setContent(memo);
        minute.setDate(new Date());
        minute.setProject(pj);
        minuteService.save(minute);
        projectService.save(u, pj);
        rooms.replace(projectIdx, -1);
        try {
            FileUtil.makeMinute(Integer.parseInt(projectIdx), memo);
        } catch (NumberFormatException e) {
            log.error("makeMinute error", e);
        }
        io.sockets().in(projectIdx).emit(SocketClientType.REFRESH.name, memo);
        io.sockets().in(projectIdx).emit(SocketClientType.FINISH.name);
        logging(projectIdx, new StringBuilder().append("Succeeding to save memo ").append(memo).toString(), su.getId());
    }

    private void writer(SocketIOSocket socket) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();

        ServerUser su = clients.get(socket.getId());
        String projectIdx = su.getProjectIdx();
        Integer writerId = su.getUserIdx();
        logging(projectIdx, "Trying to be writer", su.getId());
        map.put("writer", su.getId());
        if (rooms.get(projectIdx) != -1) {
            map.put("flag", "no");
            String json = gson.toJson(map);
            socket.emit(SocketClientType.WRITE.name, json);
            logging(projectIdx, "Failing to be writer", su.getId());
        } else {
            map.put("flag", "yes");
            String json = gson.toJson(map);
            rooms.replace(projectIdx, writerId); //쓰는 사람의 id로 변경
            socket.emit(SocketClientType.WRITE.name, json);
            map.put("flag", "no");
            json = gson.toJson(map);
            io.sockets().in(projectIdx).except(socket.getId()).emit(SocketClientType.WRITE.name, json);
            logging(projectIdx, "Succeeding to be writer ", su.getId());
        }
    }

    private void file(SocketIOSocket socket, JsonObject event) {
        // msg = 파일이름
        ServerUser su = clients.get(socket.getId());
        String projectIdx = su.getProjectIdx();
        event.putString("img", su.getImg());
        event.putString("user", su.getName());
        StringBuilder sb = new StringBuilder();
        if (event.getString("type").equals("image")) {
            sb.append("<img src=../loadImg?name=");
            sb.append(event.getElement("msg").asObject().getString("stored"));
            sb.append(" style=\'width:200px;height:150px\'>");
            event.putString("msg", sb.toString());
        } else {
            sb.append("<i class='fa fa-file-text-o fa-2x'></i>");
            sb.append("<a href='../file?name=");
            sb.append(event.getElement("msg").asObject().getString("stored"));
            sb.append("'><span class='file_name_tag' style='color:#ffffff;'> ");
            sb.append(event.getElement("msg").asObject().getString("original"));
            sb.append("</span></a>");
            event.putString("msg", sb.toString());
        }
        io.sockets().in(projectIdx).emit(SocketClientType.RESPONSE.name, event);
        logging(projectIdx, "Sending File", su.getId());
    }

    private void message(SocketIOSocket socket, JsonObject event) {
        ServerUser su = clients.get(socket.getId());
        String projectIdx = su.getProjectIdx();
        event.putString("msg", CommonUtils.encodeContent(event.getString("msg")));
        event.putString("img", su.getImg());
        event.putString("user", su.getName());
        io.sockets().in(projectIdx).emit(SocketClientType.RESPONSE.name, event);
        logging(projectIdx, event.getString("msg"), su.getId());
    }


}
