package com.sajo.teamkerbell;

import com.sajo.teamkerbell.configuration.JpaConfig;
import com.sajo.teamkerbell.repository.AlarmRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfig.class})
@Slf4j
public class JpaTest{

    @Autowired
    AlarmRepository alarmRepository;


    @Test
    public void GetRepositoryTest() {
            assertEquals(alarmRepository.findAll(),Collections.emptyList());

    }

}

