package com.shape.web.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by seongahjo on 2016. 2. 29..
 */

@Data
@Entity
@Table(name="roles")
@EqualsAndHashCode(exclude={"userRoles"})
public class Role implements Serializable{

    @Id
    @GeneratedValue
    private Integer id;

    private String role;

    public Role() {
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "useridx")}
    )
    protected Set<User> userRoles;

    public Role(String role) {
        this.role = role;
    }
}