package com.shape.web.server;

import com.nhncorp.mods.socket.io.SocketIOServer;
import com.nhncorp.mods.socket.io.SocketIOSocket;
import com.nhncorp.mods.socket.io.impl.DefaultSocketIOServer;
import com.nhncorp.mods.socket.io.spring.DefaultEmbeddableVerticle;
import com.shape.web.VO.ServerUser;
import com.shape.web.entity.Minute;
import com.shape.web.entity.Project;
import com.shape.web.service.MinuteService;
import com.shape.web.service.ProjectService;
import com.shape.web.service.UserService;
import com.shape.web.util.CommonUtils;
import com.shape.web.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.json.JsonObject;

import java.util.Date;
import java.util.HashMap;

public class VertxServer extends DefaultEmbeddableVerticle {
    private static SocketIOServer io = null;
    private static final Logger logger = LoggerFactory.getLogger(VertxServer.class);
    private static HashMap<String, Integer> Rooms = new HashMap<String, Integer>(); //ProjectIdx / Wrjter_Id
    private static HashMap<String, ServerUser> Clients = new HashMap<String, ServerUser>(); // socketId,User
    @Autowired
    ProjectService pjs;
    @Autowired
    MinuteService ms;
    @Autowired
    UserService us;

    @Override
    public void start(Vertx vertx) {
        int port = 9999;
        HttpServer server = vertx.createHttpServer(); //HTTP Server 생성

        io = new DefaultSocketIOServer(vertx, server);
        io.sockets().onConnection(new Handler<SocketIOSocket>() {//Connection Event

            public void handle(final SocketIOSocket socket) {
                socket.on("join", new Handler<JsonObject>() { //room
                    public void handle(JsonObject event) {
                        String projectIdx = event.getString("projectIdx");
                        ServerUser su = new ServerUser(projectIdx, event.getInteger("userIdx"), event.getString("userId"), event.getString("userName"), event.getString("userImg"), socket.getId());
                        Clients.put(socket.getId(), su); // Socket에 해당하는 Room저장
                        if (!projectIdx.equals("")) {
                            logger.info("방 아이디 : " + projectIdx + " 접속 " + socket.getId());
                            if (Rooms.get(projectIdx) != null)//방이 존재할경우
                                logger.info("방 접속 ");
                            else {
                                //방이 존재하지 않을경우
                                Rooms.put(projectIdx, -1);
                                logger.info("방 생성");
                            }
                            socket.join(projectIdx);
                            for (ServerUser temp : Clients.values())
                                io.sockets().in(temp.getProjectIdx()).emit("adduser", temp.getId());
                        }
                    }
                }); //Join End


                socket.onDisconnect(new Handler<JsonObject>() {
                    public void handle(JsonObject event) {
                        boolean flag = true;
                        ServerUser su = Clients.get(socket.getId());
                        if (!su.getProjectIdx().equals("")) {
                            if (su != null) {
                                String projectIdx = su.getProjectIdx();
                                logger.info("방나감 :: " + projectIdx + socket.getId());
                                socket.leave(projectIdx);

                                for (ServerUser temp : Clients.values()) {   // 남아있는 Client들
                                    //새로고침시 방 삭제 방지
                                    logger.info(temp.getName() + " vs "+su.getName());
                                    logger.info(temp.getProjectIdx()+" vs "+su.getProjectIdx());
                                    if ((temp.getName().equals(su.getName())) && (temp.getProjectIdx().equals(su.getProjectIdx()))) {
                                        flag = false;
                                        break;
                                    }
                                }
                                if (flag) {
                                    logger.info("나감 : " + su.getName());
                                    io.sockets().in(projectIdx).emit("deleteuser", su.getName());
                                }
                                if (Rooms.get(projectIdx) == (su.getUserIdx())) {
                                    logger.info("writer 초기화");
                                    Rooms.replace(projectIdx, -1);
                                }
                                if (io.sockets().clients(projectIdx) == null) {
                                    logger.info("방삭제");
                                    Rooms.remove(projectIdx);
                                }
                            }
                        }
                        Clients.remove(socket.getId());
                    }
                }); //Disconnect End


                socket.on("msg", new Handler<JsonObject>() { // 메세지 전송
                    public void handle(JsonObject event) {
                        ServerUser su = Clients.get(socket.getId());
                        String projectIdx = su.getProjectIdx();
                        logger.info("메세지 : " + event.getString("msg"));
                        event.putString("msg", CommonUtils.encodeContent(event.getString("msg")));
                        event.putString("img", su.getImg());
                        event.putString("user", su.getName());
                        io.sockets().in(projectIdx).emit("response", event);
                    }
                }); //Msg End

                socket.on("img", new Handler<JsonObject>() {
                    public void handle(JsonObject event) {
                        ServerUser su = Clients.get(socket.getId());
                        String projectIdx = su.getProjectIdx();
                        event.putString("img", su.getImg());
                        event.putString("user", su.getName());
                        event.putString("msg", "<img src=../loadImg?name=" + event.getString("msg") + " style=\'width:200px;height:150px\'>");
                        io.sockets().in(projectIdx).emit("response", event);
                    }
                });//img end

                socket.on("writer", new Handler<JsonObject>() {
                    public void handle(JsonObject event) {
                        ServerUser su = Clients.get(socket.getId());
                        String projectIdx = su.getProjectIdx();
                        Integer writer_id = su.getUserIdx();
                        if (Rooms.get(projectIdx) != -1) {
                            socket.emit("write", "no");
                        } else {
                            Rooms.replace(projectIdx, writer_id); //쓰는 사람의 id로 변경
                            socket.emit("write", "yes");
                            io.sockets().in(projectIdx).except(socket.getId()).emit("write", "no");
                        }
                    }
                }); //writer end

                socket.on("refreshToAll", new Handler<JsonObject>() {
                    public void handle(JsonObject event) {
                        ServerUser su = Clients.get(socket.getId());
                        String memo = event.getString("memo");
                        String projectIdx = su.getProjectIdx();
                        io.sockets().in(projectIdx).emit("refresh", memo);
                    }
                });
                socket.on("save", new Handler<JsonObject>() {
                    public void handle(JsonObject event) {
                        ServerUser su = Clients.get(socket.getId());
                        String projectIdx = su.getProjectIdx();
                        String memo = event.getString("memo");
                        Project pj = pjs.get(Integer.parseInt(projectIdx));
                        pj.setMinute(memo);
                        Minute minute = ms.getByDate(new Date());
                        if (minute == null) {
                            minute = new Minute(memo, new Date());
                            logger.info("새로 생성했다!");
                        }
                            minute.setContent(memo);
                            minute.setDate(new Date());
                            minute.setProject(pj);
                        logger.info("메모장 : " + memo);
                        ms.save(minute);
                        pjs.save(pj);
                        Rooms.replace(projectIdx, -1);
                        try {
                            FileUtil.MakeMinute(Integer.parseInt(projectIdx), memo);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        io.sockets().in(projectIdx).emit("refresh", memo);
                    }
                }); //save end

                socket.on("invite", new Handler<JsonObject>() {
                    public void handle(JsonObject event) {
                        Integer userIdx = Integer.parseInt(event.getString("userIdx"));
                        for (ServerUser temp : Clients.values())
                            if (temp.getUserIdx() == userIdx)
                                io.sockets().socket(temp.getSocketId(), false).emit("alarm");
                    }
                });
            }
        });// onConnection end
        server.listen(port);
    }


}
