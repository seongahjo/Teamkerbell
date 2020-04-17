package com.sajo.teamkerbell.entity;

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
@Table(name = "roles")
@EqualsAndHashCode(exclude = {"userRoles"})
public class Role implements Serializable {
    private static final long serialVersionUID = -24282248985360532L;
    @Id
    @GeneratedValue
    private Integer id;

    private String userRole;

    public Role() {
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "ROLEID", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "USERID")}
    )
    private Set<User> userRoles;

    public Role(String role) {
        this.userRole = role;
    }
}