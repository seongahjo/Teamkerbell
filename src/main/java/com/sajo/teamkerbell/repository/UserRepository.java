package com.sajo.teamkerbell.repository;

import com.sajo.teamkerbell.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 2..
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(String id);

    @Query("select u from User u left join u.projects p where p.projectId = ?1")
    List<User> findByProjectId(Integer projectId, Pageable pageable);
}
