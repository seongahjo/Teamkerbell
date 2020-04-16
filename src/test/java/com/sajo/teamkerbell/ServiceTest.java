package com.sajo.teamkerbell;

import com.sajo.teamkerbell.configuration.JpaConfig;
import com.sajo.teamkerbell.entity.FileDB;
import com.sajo.teamkerbell.entity.User;
import com.sajo.teamkerbell.repository.FileDBRepository;
import com.sajo.teamkerbell.repository.UserRepository;
import com.sajo.teamkerbell.service.FileDBService;
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
    private FileDBRepository mockFileRepository;

    private RegisterServiceFacade registerService;


    @Before
    public void setUp() {
        UserService userService = new UserService(mockUserRepository);
        FileDBService fileDBService = new FileDBService(mockFileRepository);
        given(mockUserRepository.save(any(User.class))).willAnswer(i -> i.getArguments()[0]);
        given(mockFileRepository.save(any(FileDB.class))).willAnswer(i -> i.getArguments()[0]);
        registerService = new RegisterServiceFacade(userService, fileDBService);

    }

    @Test
    public void registerUser() {
        UserVO testUserVO = new UserVO("seongahjo", "password", "seongah");
        MockMultipartFile testFile = new MockMultipartFile("data", "mock_data.txt", "text/plain", "test".getBytes());
        User returnUser = registerService.registerUser(testFile, testUserVO);
        log.info(returnUser.toString());
        assertThat(returnUser.getImg(), is(notNullValue()));
    }

}

