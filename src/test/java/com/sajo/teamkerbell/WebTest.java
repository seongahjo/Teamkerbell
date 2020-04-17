package com.sajo.teamkerbell;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sajo.teamkerbell.configuration.JpaConfig;
import com.sajo.teamkerbell.configuration.WebConfig;
import com.sajo.teamkerbell.vo.TodoListVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JpaConfig.class, WebConfig.class})
@WebAppConfiguration
@Slf4j
public class WebTest {
    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void responseBodyHandler() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        TodoListVO mockTodoListVO = new TodoListVO("test", new Date(), new Date(), 1, 1);
        String value = objectMapper.writeValueAsString(mockTodoListVO);
        log.info(value);
        mockMvc.perform(post("/todoList")
                .contentType(MediaType.APPLICATION_JSON)
                .content(value)
        )
                .andDo((result) -> {
                    String content = result.getResponse().getContentAsString();
                    log.info(content);
                })
                .andExpect(status().isOk());
    }

}
