package com.sajo.teamkerbell;

import com.sajo.teamkerbell.configuration.JpaConfig;
import com.sajo.teamkerbell.entity.*;
import com.sajo.teamkerbell.repository.*;
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

    private User u;
    private User u2;

    private Project p;
    private Project p2;
    private Project p3;

    @BeforeEach
    public void setUp() throws Exception {
        u = new User("user1", "user1Pw", "user1Name");
        u2 = new User("user2", "user2pw", "user2Name");

        p = new Project("testRoom", -1, "test");
        p2 = new Project("testRoom2", -1, "test2");
        p3 = new Project("testRoom3", -1, "test3");


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
        assertThat(userRepository.findById(u2.getId()), is(u2));
        assertThat(userRepository.findById(u2.getId()), is(u2));
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
        TodoList t1 = new TodoList("FIRST", LocalDate.now(), LocalDate.now(), p.getProjectId(), u.getUserId());
        TodoList t2 = new TodoList("SECOND", LocalDate.now(), LocalDate.now(), p3.getProjectId(), u2.getUserId());
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
        Minute minute = new Minute(1, "TEST", LocalDate.now());
        minuteRepository.save(minute);

        // when
        Minute find = minuteRepository.findByProjectIdAndDate(1, LocalDate.now());

        // then
        assertThat(find, is(minute));
    }

    @Test
    public void findSchedule() {
        // given
        Schedule schedule = new Schedule(p.getProjectId(), "content", "place", new Time(1000), Schedule.ScheduleState.REGISTER, LocalDate.now(), LocalDate.now());

        // when
        scheduleRepository.save(schedule);
        List<Schedule> find = scheduleRepository.findByUserId(u.getUserId(), PageRequest.of(0, 5));

        // then
        assertThat(find, hasItem(schedule));
    }
}
