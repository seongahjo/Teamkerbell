package com.shape.web.service;

import com.shape.web.entity.*;
import com.shape.web.repository.UserRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class UserService implements UserDetailsService {

@Autowired
    UserRepository userRepository;
    /*
    User가 가지고 있는 Alarm 객체들을 반환
     */



    /*
    Spring Security에서 권한 설정을 위한 객체 Role 반환
     */
    public List<String> getRoles(Integer role) {

        List<String> roles = new ArrayList<String>();
        roles.add("ROLE_USER");
       /* if (role.intValue() == 1) {
            roles.add("ROLE_USER");
            roles.add("ROLE_ADMIN");
        } else if (role.intValue() == 2) {
            roles.add("ROLE_MODERATOR");
        }*/
        return roles;
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
        return authList;
    }

    /*
    Spring Security에서 로그인 인증을 위한 함수
     */
    @Override
    public UserDetails loadUserByUsername(String id) {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        try {
            User u = (User)userRepository.findById(id);
            return new org.springframework.security.core.userdetails.User(u.getId(),
                    u.getPw(),
                    enabled,
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    getAuthorities(0));
        } catch (NullPointerException e) {
        } catch (UsernameNotFoundException e) {

        }
        return new org.springframework.security.core.userdetails.User("null", "null", false, false, false, false, getAuthorities(0));

    }
}
