package com.sajo.teamkerbell.controller;

import com.sajo.teamkerbell.VO.MemberGraph;
import com.sajo.teamkerbell.entity.*;
import com.sajo.teamkerbell.repository.ProjectRepository;
import com.sajo.teamkerbell.service.*;
import com.sajo.teamkerbell.util.Attribute;
import com.sajo.teamkerbell.util.CommonUtils;
import com.sajo.teamkerbell.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles requests for the application home page.
 */


@Slf4j
@Controller
public class HomeController implements BaseController {
    //메뉴 컨트롤러


    private UserService userService;

    private ProjectService projectService;

    private AlarmService alarmService;

    private TodolistService todolistService;

    private ScheduleService scheduleService;

    private MinuteService minuteService;

    private FileDBService fileDBService;

    private ProjectRepository projectRepository;

    private int userIdx = 1;

    @Autowired
    public HomeController(UserService userService, ProjectService projectService, AlarmService alarmService, TodolistService todolistService, ScheduleService scheduleService, MinuteService minuteService, FileDBService fileDBService, ProjectRepository projectRepository) {
        this.userService = userService;
        this.projectService = projectService;
        this.alarmService = alarmService;
        this.todolistService = todolistService;
        this.scheduleService = scheduleService;
        this.minuteService = minuteService;
        this.fileDBService = fileDBService;
        this.projectRepository = projectRepository;
    }

    /**
     * Simply selects the home view to render by returning its name.
     */

    @GetMapping(value = "/")    //시작부
    public ModelAndView home(Authentication authentication) {
        ModelAndView mv = new ModelAndView("redirect:/dashboard");
        if (authentication == null) {
            mv = new ModelAndView("/login");
            mv.addObject("tempUser", new User());
        }
        return mv;
    }

    @GetMapping(value = "/dashboard")
    public String goDashboard(Authentication authentication) {
        return "redirect:/dashboard/" + authentication.getName();

    }

    @GetMapping(value = "/userInfo/{userId}")
    public ModelAndView userInfo(@PathVariable("userId") String userId, HttpSession session) {
        User user = userService.getUser(userIdx);
        List<Project> lpj = projectService.getProjects(user); // 프로젝트 리스트를 반환

        ModelAndView mv = new ModelAndView("/userInfo");    //ModelAndView : 컨트롤러의 처리 결과를 보여줄 뷰와 뷰에 전달할 값을 저장
        mv.addObject("user", user);
        mv.addObject(Attribute.PROJECTS, lpj);
        return mv;
    }


    @GetMapping(value = "/dashboard/{userId}")
    public ModelAndView dashboard(@PathVariable("userId") String userId, HttpSession session) {
        User user = userService.getUser(userId);
        List<Project> lpj = projectService.getProjects(user); // 프로젝트 리스트를 반환
        List<Alarm> tlla = alarmService.getTimelines(user, 0, 15); // 타임라인 리스트를 반환
        List<Todolist> lt = todolistService.getTodolists(user); // 투두리스트 리스트를 반환
        lt = lt.stream().map(t -> {
            if (new Date().after(t.getEnddate())) {
                t.setOverdue(true);
                t = todolistService.save(t);
            }
            return t;
        }).collect(Collectors.toList());

        List<Schedule> ls = scheduleService.getSchedules(user); // 스케쥴 리스트를 반환
        List<Alarm> la = alarmService.getAlarms(user); // 알람 리스트를 반환
        ModelAndView mv = new ModelAndView("/dashboard");
        mv.addObject("user", user);
        mv.addObject("timeline", tlla);
        mv.addObject(Attribute.ALARM, la);
        mv.addObject(Attribute.PROJECTS, lpj);
        mv.addObject(Attribute.TODOLIST, lt);
        mv.addObject("schedules", ls);
        return mv;
    }

    @GetMapping(value = "/chat/{projectIdx}")
    public ModelAndView chat(@PathVariable("projectIdx") Integer projectIdx,
                             HttpSession session) {
        ModelAndView mv = new ModelAndView("redirect:/");

        String time = CommonUtils.dateFormat(new Date());
        User user = userService.getUser(userIdx);

        Project project = projectService.getProject(projectIdx); // 프로젝트 객체 반환
        List<Project> lpj = projectService.getProjects(user); // 프로젝트 리스트 반환


        if (lpj.stream().noneMatch(p -> p.equals(project))) { // 자기 자신의 프로젝트가 아닐경우
            return mv;
        }
        List<Todolist> lt = todolistService.getTodolists(project); // 투두리스트 리스트를 반환
        List<Alarm> la = alarmService.getAlarms(user); // 알람 리스트를 반환
        List<User> lu = userService.getUsersByProject(project); // 유저 리스트 반환
        //List<FileDB> lfd = fileDBRepository.groupbytest(project); // 파일 받아오기

        if (project.isProcessed()) {
            List<Minute> lm = minuteService.getMinutes(project); // 회의록 객체 반환
            List<FileDB> img = fileDBService.getImgs(project); // 파일디비 리스트중 이미지 리스트 반환
            project.setMinute(" "); //회의록 초기화
            lm = lm.stream().filter(m -> {
                        if (time.equals(m.getDate().toString())) {
                            project.setMinute(m.getContent());
                            return false;
                        }
                        return true;
                    }
            )
                    .sorted()
                    .collect(Collectors.toList());


            String foldername = FileUtil.getFoldername(projectIdx, null);
            File file = new File(foldername);
            if (!file.exists() && file.mkdirs())
                log.info("folder created " + file);
            mv = new ModelAndView("/project");
            mv.addObject(Attribute.PROJECTS, lpj);
            mv.addObject(Attribute.USERS, lu);
            mv.addObject("user", user);
            mv.addObject(Attribute.ALARM, la);
            mv.addObject("minutes", lm);
            mv.addObject("project", project);
            mv.addObject("img", img);
            mv.addObject(Attribute.TODOLIST, lt);
        } else { // 위 ProjectRoom, 아래 Documentation
            List<Object> lo = projectRepository.todolistPercentage(project.getProjectidx()); // 멤버 참석율 반환
            List<MemberGraph> lg = new ArrayList<>();
            lo.forEach(object -> {
                Object[] objects = (Object[]) object;
                MemberGraph temp = new MemberGraph((Integer) objects[0], (String) objects[1], (BigDecimal) objects[2]);
                lg.add(temp);
            });
            List<Integer> percentage = new ArrayList<>();
            List<String> username = new ArrayList<>();
            for (MemberGraph temp : lg) {
                username.add("\"" + temp.getName() + "\"");
                if (temp.getPercentage() != null)
                    percentage.add(temp.getPercentage().intValue()); //달성율
                else
                    percentage.add(0);
            }

            mv = new ModelAndView("/document");
            mv.addObject("user", user);
            mv.addObject(Attribute.PROJECTS, lpj);
            mv.addObject("project", project);
            mv.addObject(Attribute.USERS, lu);
            mv.addObject(Attribute.ALARM, la);
            mv.addObject(Attribute.TODOLIST, lt);
            mv.addObject("date", new Date());
            mv.addObject("usersname", username);
            mv.addObject("percentage", percentage);
        } // Documentation 끝
        return mv;

    }


    @RequestMapping(value = "/projectmanager")
    public ModelAndView manager(HttpSession session) {
        User user = userService.getUser(userIdx);
        List<Project> lpj = projectService.getProjects(user, 0, 5); // 프로젝트 리스트 객체 10개 반환
        List<Project> lpjs = projectService.getProjects(user); // 프로젝트 리스트를 반환

        ModelAndView mv = new ModelAndView("/EditPJ");
        mv.addObject("user", user);
        mv.addObject(Attribute.PROJECTS, lpj);
        mv.addObject("projectss", lpjs);
        return mv;
    }

    @GetMapping(value = "/admin")
    public ModelAndView admin(HttpSession session) {
        return new ModelAndView("/admin");
    }

    @PostMapping(value = "/room")    //프로젝트 개설
    public String makeRoom(@RequestParam(value = "name") String name, HttpSession session) {
        User user = userService.getUser(userIdx);
        Project project = new Project(name, userIdx, "");
        user.addProject(project);
        projectService.save(user, project);
        return "redirect:/projectmanager";
    }

    @Override
    public void setSessionId(Integer userIdx) {
        this.userIdx = 1;
//        this.userIdx = userIdx;
    }
}
