package com.sajo.teamkerbell;

import com.sajo.teamkerbell.configuration.JpaConfig;
import com.sajo.teamkerbell.entity.*;
import com.sajo.teamkerbell.repository.FileDBRepository;
import com.sajo.teamkerbell.repository.ProjectRepository;
import com.sajo.teamkerbell.repository.ScheduleRepository;
import com.sajo.teamkerbell.repository.UserRepository;
import com.sajo.teamkerbell.service.*;
import com.sajo.teamkerbell.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {JpaConfig.class})
@Slf4j
public class ServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private ProjectRepository mockProjectRepository;

    @Mock
    private ScheduleRepository mockScheduleRepository;

    @Mock
    private FileDBRepository mockFileRepository;

    @Test
    public void registerUser() {
        given(mockUserRepository.save(any(User.class))).willAnswer(i -> i.getArguments()[0]);
        given(mockFileRepository.save(any(FileDB.class))).willAnswer(i -> i.getArguments()[0]);
        UserService userService = new UserService(mockUserRepository);
        FileDBService fileDBService = new FileDBService(mockFileRepository);
        RegisterServiceFacade registerService = new RegisterServiceFacade(userService, fileDBService);
        // given
        UserVO testUserVO = new UserVO("seongahjo", "password", "seongah");
        MockMultipartFile testFile = new MockMultipartFile("data", "mock_data.txt", "text/plain", "test".getBytes());
        // when
        User returnUser = registerService.registerUser(testFile, testUserVO);
        // then
        assertThat(returnUser.getImg(), is(notNullValue()));
    }

    @Test
    public void projectDeleteOrFinish() {
        Project mockProject = new Project("testRoom", -1, "testRoomMinute");
        given(mockProjectRepository.findById(anyInt())).willReturn(Optional.of(mockProject));
        ProjectService projectService = new ProjectService(mockProjectRepository);
        assertThat(projectService.delete(1).isDeleted(), is(true));
        assertThat(projectService.finish(1).isFinished(), is(true));
    }

    @Test
    public void makeSchedule() {
        given(mockUserRepository.save(any(User.class))).willAnswer(i -> i.getArguments()[0]);
        given(mockScheduleRepository.findById(anyInt())).willReturn(
                Optional.of(new Schedule(1, "content", "place", new Time(100), Schedule.ScheduleState.REGISTER, LocalDate.now(), LocalDate.now())
                ));
        // given
        UserService userService = new UserService(mockUserRepository);
        ScheduleService scheduleService = new ScheduleService(mockScheduleRepository);
        User user = new User("testId", "testPw", "testName");
        user = userService.save(user);
        Schedule schedule = scheduleService.assignAppointment(1, user.getUserId());

        // when
        Set<Appointment> appointments = schedule.getAppointments();
        // then
        assertThat(appointments, hasSize(1));
    }
}

