package com.shape.test;

import com.shape.web.configuration.SpringConfig;
import com.shape.web.repository.FileDBRepository;
import com.shape.web.repository.ProjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by seongahjo on 2016. 7. 16..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
@ComponentScan("com.shape.web.repository")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    FileDBRepository fileDBRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Test
    public void test() throws IOException {




    }
}
