package com.sajo.teamkerbell;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sajo.teamkerbell.configuration.JpaConfig;
import com.sajo.teamkerbell.configuration.WebConfig;
import com.sajo.teamkerbell.vo.ScheduleVO;
import com.sajo.teamkerbell.vo.TodoListVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
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

import java.sql.Time;
import java.time.LocalDate;

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

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void createTodoList() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        TodoListVO mockTodoListVO = new TodoListVO("test", LocalDate.now(), LocalDate.now(), 1);
        String value = objectMapper.writeValueAsString(mockTodoListVO);
        log.info(value);
        mockMvc.perform(post("/project/1/todoList")
                .contentType(MediaType.APPLICATION_JSON)
                .content(value)
        )
                .andDo((result) -> {
                    String content = result.getResponse().getContentAsString();
                    log.info(content);
                })
                .andExpect(status().isOk());
    }

    @Test
    public void createSchedule() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        ScheduleVO mockTodoListVO = new ScheduleVO("content", "place", new Time(1000), LocalDate.now(), LocalDate.now());

        String value = objectMapper.writeValueAsString(mockTodoListVO);
        log.info(value);
        mockMvc.perform(post("/project/1/schedule")
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
