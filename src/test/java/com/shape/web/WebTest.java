package com.shape.web;

import com.shape.web.configuration.JpaConfig;
import com.shape.web.configuration.SecurityConfig;
import com.shape.web.configuration.WebConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfig.class,WebConfig.class})
@WebAppConfiguration
@Slf4j
public class WebTest {
    @Autowired
    private WebApplicationContext context;

    @Test
    public void responseBodyHandler() throws Exception{
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        mockMvc.perform(get("/")).andDo((result)-> {
            String content=result.getModelAndView().getViewName();
            log.info(content);
        })
                .andExpect(status().isOk());
    }

}
