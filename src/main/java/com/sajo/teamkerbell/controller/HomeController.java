package com.sajo.teamkerbell.controller;

import com.sajo.teamkerbell.entity.*;
import com.sajo.teamkerbell.service.*;
import com.sajo.teamkerbell.util.Attribute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Handles requests for the application home page.
 */


@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    //메뉴 컨트롤러
    private final UserService userService;
    private final ProjectService projectService;
    private final TodoListService todoListService;
    private final ScheduleService scheduleService;
    private final MinuteService minuteService;
    private final FileDBService fileDBService;
    private final TimelineService timelineService;
    private final AlarmService alarmService;

    /**
     * Simply selects the home view to render by returning its name.
     */

    @GetMapping(value = "/")    //시작부
    public ModelAndView home(Authentication authentication) {
        ModelAndView mv = new ModelAndView("redirect:/dashboard");
        if (authentication == null) {
            mv = new ModelAndView("/login");
        }
        return mv;
    }

    @GetMapping(value = "/userInfo/{userId}")
    public ModelAndView userInfo(@PathVariable("userId") Integer userId) {
        User user = userService.getUserByUser(userId);
        List<Project> lpj = projectService.getProjectByUserId(userId, 0, 5); // 프로젝트 리스트를 반환
        ModelAndView mv = new ModelAndView("/userInfo");    //ModelAndView : 컨트롤러의 처리 결과를 보여줄 뷰와 뷰에 전달할 값을 저장
        mv.addObject("user", user);
        mv.addObject(Attribute.PROJECTS, lpj);
        return mv;
    }


    @GetMapping(value = "/dashboard/{userId}")
    public ModelAndView dashboard(@PathVariable("userId") Integer userId) {
        User user = userService.getUserByUser(userId);
        List<Project> lpj = projectService.getProjectByUserId(userId, 0, 5); // 프로젝트 리스트를 반환
        List<TodoList> lt = todoListService.getYetTodoListsByUserId(userId, 0, 5); // 투두리스트 리스트를 반환
        List<Schedule> ls = scheduleService.getScheduleByUserId(userId, 0, 5); // 스케쥴 리스트를 반환
        List<Alarm> la = alarmService.getAlarmsByInviteeId(userId, 0, 5); // 알람 리스트를 반환
        List<Timeline> timelines = timelineService.getTimelinesByUserId(userId, 0, 5);
        ModelAndView mv = new ModelAndView("/dashboard");
        mv.addObject("user", user);
        mv.addObject("timeline", timelines);
        mv.addObject(Attribute.ALARM, la);
        mv.addObject(Attribute.PROJECTS, lpj);
        mv.addObject(Attribute.TODOLIST, lt);
        mv.addObject("schedules", ls);
        return mv;
    }

    @GetMapping(value = "/chat/{projectId}")
    public ModelAndView chat(@PathVariable("projectId") Integer projectId) {
        int userId = 1;
        User user = userService.getUserByUser(userId);


        List<Project> lpj = projectService.getProjectByUserId(userId, 0, 5); // 프로젝트 리스트 반환
        List<TodoList> lt = todoListService.getYetTodoListsByUserId(userId, 0, 5); // 투두리스트 리스트를 반환
        List<Alarm> la = alarmService.getAlarmsByInviteeId(userId, 0, 5); // 알람 리스트를 반환
        List<User> lu = userService.getUsersFromProjectId(projectId, 0, 5); // 유저 리스트 반환
        List<FileDB> lfd = fileDBService.getFilesFromProjectId(projectId, 0, 5); // 파일 받아오기
        List<Minute> lm = minuteService.findMinutesByProjectId(projectId, 0, 5); // 회의록 객체 반환
        List<FileDB> img = fileDBService.findImgsByProjectId(projectId, 0, 5); // 파일디비 리스트중 이미지 리스트 반환
        Minute todayMinute = minuteService.findTodayMinute(projectId);
        Project project = projectService.getCurrentProjectId(projectId, todayMinute); // 프로젝트 객체 반환

        ModelAndView mv = new ModelAndView("/project");
        mv.addObject(Attribute.PROJECTS, lpj);
        mv.addObject(Attribute.USERS, lu);
        mv.addObject("user", user);
        mv.addObject(Attribute.ALARM, la);
        mv.addObject("files", lfd);
        mv.addObject("minutes", lm);
        mv.addObject("project", project);
        mv.addObject("img", img);
        mv.addObject(Attribute.TODOLIST, lt);
        return mv;
    }
}
