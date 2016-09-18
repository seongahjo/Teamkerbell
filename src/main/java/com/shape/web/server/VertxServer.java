package com.shape.web.server;

import com.nhncorp.mods.socket.io.SocketIOServer;
import com.nhncorp.mods.socket.io.impl.DefaultSocketIOServer;
import com.nhncorp.mods.socket.io.spring.DefaultEmbeddableVerticle;
import com.shape.web.VO.ServerUser;
import com.shape.web.entity.Minute;
import com.shape.web.entity.Project;
import com.shape.web.entity.User;
import com.shape.web.service.AlarmService;
import com.shape.web.service.MinuteService;
import com.shape.web.service.ProjectService;
import com.shape.web.service.UserService;
import com.shape.web.util.CommonUtils;
import com.shape.web.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServer;

import java.util.Date;
import java.util.HashMap;

@Slf4j
public class VertxServer extends DefaultEmbeddableVerticle {
    private static SocketIOServer io = null;
    private static HashMap<String, Integer> Rooms = new HashMap<String, Integer>(); //ProjectIdx / Wrjter_Id
    private static HashMap<String, ServerUser> Clients = new HashMap<String, ServerUser>(); // socketId,User
    @Autowired
    UserService userService;
    @Autowired
    ProjectService projectService;
    @Autowired
    AlarmService alarmService;
    @Autowired
    MinuteService minuteService;

    @Override
    public void start(Vertx vertx) {
        int port = 9999;
        HttpServer server = vertx.createHttpServer(); //HTTP Server 생성

        io = new DefaultSocketIOServer(vertx, server);
       /* io.setAuthHandler((data,accept) ->{

        });*/
        io.sockets().onConnection(socket -> {
            socket.on("join", event -> {
                String projectIdx = event.getString("projectIdx");
                ServerUser su = new ServerUser(projectIdx, event.getInteger("userIdx"), event.getString("userId"), event.getString("userName"), event.getString("userImg"), socket.getId());
                Clients.put(socket.getId(), su); // Socket에 해당하는 Room저장
                if (!projectIdx.equals("")) {
                    log.info("[ROOM "+projectIdx+"] Trying to Enter the room [USER " + su.getId()+"]");
                    if (Rooms.get(projectIdx) != null)//방이 존재할경우
                        log.info("[ROOM "+projectIdx+"] Room exist");
                    else {
                        //방이 존재하지 않을경우
                        Rooms.put(projectIdx, -1);
                        log.info("[ROOM "+projectIdx+"] Room is created");
                    }
                    socket.join(projectIdx);
                    for (ServerUser temp : Clients.values())
                        io.sockets().in(temp.getProjectIdx()).emit("adduser", temp.getId());
                    log.info("[ROOM "+projectIdx+"] Entering room succeed [USER "+su.getId()+"]");
                }
            }); //Join End


            socket.onDisconnect(event -> {
                boolean flag = true;
                ServerUser su = Clients.get(socket.getId());
                if (su != null) {
                    if (!su.getProjectIdx().equals("")) {
                        String projectIdx = su.getProjectIdx();
                        log.info("[ROOM "+projectIdx+"] Trying to Exit the room [USER " + su.getId()+"]");
                        socket.leave(projectIdx);
                        Clients.remove(socket.getId());
                        //su ==> 나가는 사람
                        for (ServerUser temp : Clients.values()) {   // 남아있는 Client들
                            //새로고침시 방 삭제 방지
                            flag = true;
                           /* log.info("Remain : ["+temp.getId()+"]" + " vs Exit new name : [" + su.getId()+"]");
                            log.info("Remain : ["+temp.getProjectIdx()+"]" + " vs Exit new project : [" + su.getProjectIdx()+"]");*/

                            if ((temp.getName().equals(su.getName())) && (temp.getProjectIdx().equals(su.getProjectIdx()))) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            log.info("[ROOM "+projectIdx+"] Succeeding to Exit Room [USER "+su.getId()+"]");
                            io.sockets().in(projectIdx).emit("deleteuser", su.getName());
                        }
                        if (Rooms.get(projectIdx) == (su.getUserIdx())) {
                            Rooms.replace(projectIdx, -1);
                            log.info("[ROOM "+projectIdx+"] Writer Initialize");
                        }
                        if (io.sockets().clients(projectIdx) == null) {
                            Rooms.remove(projectIdx);
                            log.info("[ROOM "+projectIdx+"] Room is deleted");
                        }
                    }
                }
                //Clients.remove(socket.getId());

            }); //Disconnect End


            socket.on("msg", event -> {
                ServerUser su = Clients.get(socket.getId());
                String projectIdx = su.getProjectIdx();
                event.putString("msg", CommonUtils.encodeContent(event.getString("msg")));
                event.putString("img", su.getImg());
                event.putString("user", su.getName());
                io.sockets().in(projectIdx).emit("response", event);
                log.info("[ROOM "+projectIdx+"] Sending Message : [" + event.getString("msg")+"] [USER "+su.getId()+"]");
            }); //Msg End

            socket.on("file", event -> {
                // msg = 파일이름
                ServerUser su = Clients.get(socket.getId());
                String projectIdx = su.getProjectIdx();
                event.putString("img", su.getImg());
                event.putString("user", su.getName());

                if (event.getString("type").equals("img"))
                    event.putString("msg", "<img src=../loadImg?name=" + event.getElement("msg").asObject().getString("stored") + " style=\'width:200px;height:150px\'>");
                else
                    event.putString("msg", "<i class='fa fa-file-text-o fa-2x'></i>" + "<a href='../file?name=" + event.getElement("msg").asObject().getString("stored") + "'><span class='file_name_tag' style='color:#ffffff;'> " + event.getElement("msg").asObject().getString("original") + "</span></a>");
                io.sockets().in(projectIdx).emit("response", event);
                log.info("[ROOM "+projectIdx+"] Sending File [USER "+su.getId()+"]");
            });//img end

            socket.on("writer", event -> {

                ServerUser su = Clients.get(socket.getId());
                String projectIdx = su.getProjectIdx();
                Integer writer_id = su.getUserIdx();
                log.info("["+projectIdx + "] Trying to be writer ["+su.getId()+"]");
                if (Rooms.get(projectIdx) != -1) {
                    socket.emit("write", "{\"flag\" : \"no\", \"writer\" : \""+su.getId()+"\"}");
                    log.info("["+su.getId() + "] Failing to be writer");
                } else {
                    Rooms.replace(projectIdx, writer_id); //쓰는 사람의 id로 변경
                    socket.emit("write", "{\"flag\" : \"yes\", \"writer\" : \""+su.getId()+"\"}");
                    io.sockets().in(projectIdx).except(socket.getId()).emit("write", "{\"flag\" : \"no\", \"writer\" : \""+su.getId()+"\"}");
                    log.info("["+projectIdx + "] Succeeding to be writer [USER "+ su.getId()+"]");
                }
            }); //writer end

            socket.on("refreshToAll", event -> {
                ServerUser su = Clients.get(socket.getId());
                String memo = event.getString("memo");
                String projectIdx = su.getProjectIdx();
                io.sockets().in(projectIdx).emit("refresh", memo);
            });
            socket.on("save", event -> {
                ServerUser su = Clients.get(socket.getId());
                User u=userService.getUser(su.getId());
                String projectIdx = su.getProjectIdx();
                String memo = event.getString("memo");
                Project pj = projectService.getProject(Integer.parseInt(projectIdx));
                pj.setMinute(memo);
                log.info("[ROOM "+projectIdx+"] Trying to save memo [" + memo + "] [USER "+su.getId()+"]");

                /*
                Create Minute
                 */
                Minute minute = minuteService.getMinute(pj,new Date());
                if (minute == null) {
                    minute = new Minute(memo, new Date());
                    log.info("[ROOM "+projectIdx+"] Minute is Created");
                }
                minute.setContent(memo);
                minute.setDate(new Date());
                minute.setProject(pj);
                minuteService.save(minute);

                projectService.save(u,pj);
                Rooms.replace(projectIdx, -1);
                try {
                    FileUtil.MakeMinute(Integer.parseInt(projectIdx), memo);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                io.sockets().in(projectIdx).emit("refresh", memo);
                io.sockets().in(projectIdx).emit("finish");
                log.info("[ROOM "+projectIdx+"] Succeeding to save memo [" + memo + "] [USER "+su.getId()+"]");
            }); //save end


            socket.on("invite", event -> {
                Integer userIdx = Integer.parseInt(event.getString("userIdx"));
                for (ServerUser temp : Clients.values())
                    if (temp.getUserIdx() == userIdx)
                        io.sockets().socket(temp.getSocketId(), false).emit("alarm");
            }); //invite end
        });// onConnection end
        server.listen(port);
    }


}
