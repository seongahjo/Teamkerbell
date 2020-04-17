package com.sajo.teamkerbell;

import com.google.common.collect.Lists;
import com.sajo.teamkerbell.configuration.JpaConfig;
import com.sajo.teamkerbell.entity.*;
import com.sajo.teamkerbell.repository.*;
import com.sajo.teamkerbell.service.*;
import com.sajo.teamkerbell.vo.AlarmVO;
import com.sajo.teamkerbell.vo.ScheduleVO;
import com.sajo.teamkerbell.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
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

    @Mock
    private AlarmRepository alarmRepository;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private FileDBService fileDBService;

    @InjectMocks
    private ScheduleService scheduleService;

    @InjectMocks
    private ProjectService projectService;

    @InjectMocks
    private AlarmService alarmService;

    @Test
    public void registerUser() {
        // given
        given(mockUserRepository.save(any(User.class))).willAnswer(i -> i.getArguments()[0]);
        given(mockFileRepository.save(any(FileDB.class))).willAnswer(i -> i.getArguments()[0]);
        RegisterServiceFacade registerService = new RegisterServiceFacade(userService, fileDBService);
        UserVO testUserVO = new UserVO("seongahjo", "password", "seongah");
        MockMultipartFile testFile = new MockMultipartFile("data", "mock_data.txt", "text/plain", "test".getBytes());
        // when
        User returnUser = registerService.registerUser(testFile, testUserVO);
        // then
        assertThat(returnUser.getImg(), is(notNullValue()));
    }

    @Test
    public void registerUserWithoutImage() {
        // given
        given(mockUserRepository.save(any(User.class))).willAnswer(i -> i.getArguments()[0]);
        RegisterServiceFacade registerService = new RegisterServiceFacade(userService, fileDBService);
        UserVO testUserVO = new UserVO("seongahjo", "password", "seongah");
        MockMultipartFile emptyFile = new MockMultipartFile("file", new byte[0]);
        // when
        User returnUser = registerService.registerUser(emptyFile, testUserVO);
        // then
        assertThat(emptyFile.isEmpty(), is(true));
        assertThat(returnUser.getImg(), is(nullValue()));
    }

    @Test
    public void projectDeleteOrFinish() {
        // given
        Project mockProject = new Project("testRoom", -1, "testRoomMinute");
        given(mockProjectRepository.findById(anyInt())).willReturn(Optional.of(mockProject));


        // when
        boolean deleted = projectService.delete(1).isDeleted();
        boolean finished = projectService.finish(1).isFinished();

        // then
        assertThat(deleted, is(true));
        assertThat(finished, is(true));
    }

    @Test
    public void makeSchedule() {
        // given
        ScheduleVO scheduleVO = new ScheduleVO("content", "place", new Time(100), LocalDate.now(), LocalDate.now());
        given(mockUserRepository.save(any(User.class))).willAnswer(i -> i.getArguments()[0]);
        given(mockScheduleRepository.findById(anyInt())).willReturn(
                Optional.of(Schedule.from(1, scheduleVO)
                ));

        User user = User.from(new UserVO("testId", "testPw", "testName"));
        user = userService.save(user);
        Schedule schedule = scheduleService.assignAppointment(1, user.getUserId());

        // when
        Set<Appointment> appointments = schedule.getAppointments();

        // then
        assertThat(appointments, hasSize(1));
    }

    @Test
    public void inviteUser() {
        // given
        given(alarmRepository.save(any(Alarm.class))).willAnswer(i -> i.getArguments()[0]);
        AlarmVO alarmVO = new AlarmVO(1, 2, 3);
        given(alarmRepository.findByInviteeId(1, PageRequest.of(0, 5))).willReturn(Lists.newArrayList(Alarm.from(alarmVO)));


        // when
        Alarm alarm = alarmService.invite(alarmVO);
        List<Alarm> alarms = alarmService.getAlarmsByInviteeId(1, 0, 5);

        // then
        assertThat(alarms, hasItem(alarm));
    }
}

