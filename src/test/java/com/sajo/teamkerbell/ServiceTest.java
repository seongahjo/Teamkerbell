package com.sajo.teamkerbell;

import com.sajo.teamkerbell.configuration.JpaConfig;
import com.sajo.teamkerbell.entity.FileDB;
import com.sajo.teamkerbell.entity.Project;
import com.sajo.teamkerbell.entity.User;
import com.sajo.teamkerbell.repository.FileDBRepository;
import com.sajo.teamkerbell.repository.ProjectRepository;
import com.sajo.teamkerbell.repository.UserRepository;
import com.sajo.teamkerbell.service.FileDBService;
import com.sajo.teamkerbell.service.ProjectService;
import com.sajo.teamkerbell.service.RegisterServiceFacade;
import com.sajo.teamkerbell.service.UserService;
import com.sajo.teamkerbell.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {JpaConfig.class})
@Slf4j
public class ServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private ProjectRepository mockProjectRepository;

    @Mock
    private FileDBRepository mockFileRepository;

    private RegisterServiceFacade registerService;
    private ProjectService projectService;

    @Before
    public void setUp() {
        UserService userService = new UserService(mockUserRepository);
        Project mockProject = new Project("testRoom", -1, "testRoomMinute");
        FileDBService fileDBService = new FileDBService(mockFileRepository);
        given(mockUserRepository.save(any(User.class))).willAnswer(i -> i.getArguments()[0]);
        given(mockFileRepository.save(any(FileDB.class))).willAnswer(i -> i.getArguments()[0]);
        given(mockProjectRepository.findOne(1)).willReturn(mockProject);
        projectService = new ProjectService(mockProjectRepository);
        registerService = new RegisterServiceFacade(userService, fileDBService);
    }

    @Test
    public void registerUser() {
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
        assertThat(projectService.delete(1).isDeleted(), is(true));
        assertThat(projectService.finish(1).isFinished(), is(false));
    }
}

