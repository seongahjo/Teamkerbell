package com.sajo.teamkerbell.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by seongahjo on 2016. 2. 29..
 */

@Data
@Entity
@Table
@EqualsAndHashCode(exclude = {"userRoles"})
public class Role implements Serializable, GrantedAuthority {
    private static final long serialVersionUID = -24282248985360532L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated
    private UserRole userRole;

    public Role() {
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "ROLEID", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "USERID")}
    )
    private Set<User> userRoles;

    public Role(UserRole role) {
        this.userRole = role;
    }

    @Override
    public String getAuthority() {
        return userRole.name();
    }

    public enum UserRole {
        ROLE_USER, ROLE_ADMIN
    }
}