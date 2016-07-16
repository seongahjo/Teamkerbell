package com.shape.test;

import com.shape.web.configuration.SpringConfig;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by seongahjo on 2016. 7. 16..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    @Test
    public void test() throws IOException, GitAPIException {



    }
}
