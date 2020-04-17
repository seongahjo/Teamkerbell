package com.sajo.teamkerbell;

import com.sajo.teamkerbell.configuration.JpaConfig;
import com.sajo.teamkerbell.entity.*;
import com.sajo.teamkerbell.repository.*;
import com.sajo.teamkerbell.vo.MinuteVO;
import com.sajo.teamkerbell.vo.ScheduleVO;
import com.sajo.teamkerbell.vo.TodoListVO;
import com.sajo.teamkerbell.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JpaConfig.class})
@Transactional
@Slf4j
public class RepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TodoListRepository todoListRepository;
    @Autowired
    private MinuteRepository minuteRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private TimelineRepository timelineRepository;

    private User u;
    private User u2;

    private Project p;
    private Project p2;
    private Project p3;

    @BeforeEach
    public void setUp() throws Exception {
        UserVO uVO = new UserVO("user1", "user1Pw", "user1Name");
        UserVO u2VO = new UserVO("user2", "user2pw", "user2Name");

        p = new Project("testRoom", -1, "test");
        p2 = new Project("testRoom2", -1, "test2");
        p3 = new Project("testRoom3", -1, "test3");

        u = User.from(uVO);
        u2 = User.from(u2VO);

        u.participateProject(p);
        u.participateProject(p2);

        u2.participateProject(p2);
        u2.participateProject(p3);

        p = projectRepository.save(p);
        p2 = projectRepository.save(p2);
        p3 = projectRepository.save(p3);


        u = userRepository.save(u);
        u2 = userRepository.save(u2);
    }

    @Test
    public void insertOK() {
        assertThat(userRepository.findById(u.getId()), is(u));
        assertThat(userRepository.findById(u2.getId()), is(u2));
        assertThat(projectRepository.findAll(), hasSize(3));
        assertThat(projectRepository.findById(p.getProjectId()).orElseThrow(IllegalArgumentException::new), is(p));
        assertThat(projectRepository.findById(p2.getProjectId()).orElseThrow(IllegalArgumentException::new), is(p2));
        assertThat(projectRepository.findById(p3.getProjectId()).orElseThrow(IllegalArgumentException::new), is(p3));
    }

    @Test
    public void findUsersByProjectId() {
        // when
        List<User> pListU = userRepository.findByProjectId(p.getProjectId(), PageRequest.of(0, 5));
        List<User> p2ListU = userRepository.findByProjectId(p2.getProjectId(), PageRequest.of(0, 5));
        List<User> p3ListU = userRepository.findByProjectId(p3.getProjectId(), PageRequest.of(0, 5));

        //then
        assertThat(p2ListU, hasSize(2));
        assertThat(p2ListU, hasItems(u, u2));
        assertThat(pListU, hasSize(1));
        assertThat(pListU, hasItems(u));
        assertThat(p3ListU, hasSize(1));
        assertThat(p3ListU, hasItems(u2));
    }

    @Test
    public void findProjectsByUserId() {
        // when
        List<Project> uListP = projectRepository.findByUserId(u.getUserId(), PageRequest.of(0, 5));
        List<Project> u2ListP = projectRepository.findByUserId(u2.getUserId(), PageRequest.of(0, 5));
        assertThat(uListP, hasSize(2));
        assertThat(uListP, hasItems(p2, p));

        assertThat(u2ListP, hasSize(2));
        assertThat(u2ListP, hasItems(p2, p3));
    }

    @Test
    public void findTodoLists() {
        // given
        TodoListVO todoListVO1 = new TodoListVO("FIRST", LocalDate.now(), LocalDate.now(), u.getUserId());
        TodoListVO todoListVO2 = new TodoListVO("SECOND", LocalDate.now(), LocalDate.now(), u2.getUserId());

        TodoList t1 = TodoList.from(p.getProjectId(), todoListVO1);
        TodoList t2 = TodoList.from(p3.getProjectId(), todoListVO2);

        t1 = todoListRepository.save(t1);
        t2 = todoListRepository.save(t2);

        // when
        List<TodoList> byUserId = todoListRepository.findByUserId(u.getUserId(), PageRequest.of(0, 5));

        // then
        assertThat(p.isFinished(), is(false));
        assertThat(byUserId, hasItem(t1));

        // given
        p.finished();

        // when
        byUserId = todoListRepository.findByUserId(u.getUserId(), PageRequest.of(0, 5));
        List<TodoList> all = todoListRepository.findAll();
        List<TodoList> byProjectId = todoListRepository.findByProjectId(p3.getProjectId(), PageRequest.of(0, 5));

        // then
        assertThat(all, hasSize(2));
        assertThat(byProjectId, hasItem(t2));
        assertThat(p.isFinished(), is(true));
        assertThat(byUserId, empty());
    }

    @Test
    public void findMinute() {
        // given
        Minute minute = Minute.from(1, new MinuteVO("TEST", LocalDate.now()));
        minute = minuteRepository.save(minute);

        // when
        Minute find = minuteRepository.findByProjectIdAndDate(1, LocalDate.now());

        // then
        assertThat(find, is(minute));
    }

    @Test
    public void findSchedule() {
        // given
        ScheduleVO scheduleVO = new ScheduleVO("content", "place", new Time(1000), LocalDate.now(), LocalDate.now());
        Schedule schedule = Schedule.from(p.getProjectId(), scheduleVO);
        schedule = scheduleRepository.save(schedule);

        // when
        List<Schedule> find = scheduleRepository.findByUserId(u.getUserId(), PageRequest.of(0, 5));

        // then
        assertThat(find, hasItem(schedule));
    }

    @Test
    public void findTimelines() {
        // given
        ScheduleVO scheduleVO = new ScheduleVO("content", "place", new Time(1000), LocalDate.now(), LocalDate.now());
        Schedule schedule = Schedule.from(p.getProjectId(), scheduleVO);
        scheduleRepository.save(schedule);

        Minute minute = Minute.from(p.getProjectId(), new MinuteVO("TEST", LocalDate.now()));
        minuteRepository.save(minute);

        TodoListVO todoListVO = new TodoListVO("FIRST", LocalDate.now(), LocalDate.now(), u.getUserId());
        TodoList todoList = TodoList.from(p.getProjectId(), todoListVO);
        todoList = todoListRepository.save(todoList);

        Timeline t1 = timelineRepository.save(Timeline.from(schedule));
        Timeline t2 = timelineRepository.save(Timeline.from(minute));
        Timeline t3 = timelineRepository.save(Timeline.from(todoList));

        // when
        List<Timeline> timelines = timelineRepository.findByUserId(u.getUserId(), PageRequest.of(0, 5));

        // then
        assertThat(timelines, hasItems(t1, t2, t3));
    }
}
