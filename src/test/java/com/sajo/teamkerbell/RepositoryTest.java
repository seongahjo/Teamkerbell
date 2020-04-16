package com.sajo.teamkerbell;

import com.sajo.teamkerbell.configuration.JpaConfig;
import com.sajo.teamkerbell.entity.Project;
import com.sajo.teamkerbell.entity.User;
import com.sajo.teamkerbell.repository.ProjectRepository;
import com.sajo.teamkerbell.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {JpaConfig.class})
public class RepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;


    private User u;
    private User u2;

    private Project p;
    private Project p2;
    private Project p3;

    @Before
    public void setUp() throws Exception {
        u = new User("user1", "user1Pw", "user1Name");
        u2 = new User("user2", "user2pw", "user2Name");

        p = new Project("testRoom", -1, "test");
        p2 = new Project("testRoom2", -1, "test2");
        p3 = new Project("testRoom3", -1, "test3");

        u.participateProject(p);
        u.participateProject(p2);

        u2.participateProject(p2);
        u2.participateProject(p3);

        projectRepository.save(p);
        projectRepository.save(p2);
        projectRepository.save(p3);

        userRepository.save(u);
        userRepository.save(u2);
    }

    @Test
    public void insertOK() {
        assertThat(userRepository.findById(u.getId()), is(u));
        assertThat(userRepository.findById(u2.getId()), is(u2));
        assertThat(projectRepository.findAll(), hasSize(3));
        assertThat(userRepository.findById(u2.getId()), is(u2));
        assertThat(userRepository.findById(u2.getId()), is(u2));
    }

    @Test
    public void findUsersByProjectId() {
        List<User> pListU = userRepository.findByProjectId(p.getProjectId(), new PageRequest(0, 5));
        List<User> p2ListU = userRepository.findByProjectId(p2.getProjectId(), new PageRequest(0, 5));
        List<User> p3ListU = userRepository.findByProjectId(p3.getProjectId(), new PageRequest(0, 5));
        assertThat(p2ListU, hasSize(2));
        assertThat(p2ListU, hasItems(u, u2));

        assertThat(pListU, hasSize(1));
        assertThat(pListU, hasItems(u));

        assertThat(p3ListU, hasSize(1));
        assertThat(p3ListU, hasItems(u2));
    }

    @Test
    public void findProjectsByUserId() {
        List<Project> uListP = projectRepository.findByUserId(u.getUserId(), new PageRequest(0, 5));
        List<Project> u2ListP = projectRepository.findByUserId(u2.getUserId(), new PageRequest(0, 5));
        assertThat(uListP, hasSize(2));
        assertThat(uListP, hasItems(p2, p));

        assertThat(u2ListP, hasSize(2));
        assertThat(u2ListP, hasItems(p2, p3));
    }
}
