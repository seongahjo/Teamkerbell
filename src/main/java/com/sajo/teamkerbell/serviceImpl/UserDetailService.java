package com.sajo.teamkerbell.serviceImpl;

import com.sajo.teamkerbell.entity.User;
import com.sajo.teamkerbell.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 27..
 */
@Service
@Slf4j
public class UserDetailService implements UserDetailsService {

    private UserRepository userRepository;


    @Autowired
    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
     Spring Security에서 권한 설정을 위한 객체 Role 반환
      */
    private List<String> getRoles(Integer role) {

        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
       /* if (role.intValue() == 1) {
            roles.add("ROLE_USER");
            roles.add("ROLE_ADMIN");
        } else if (role.intValue() == 2) {
            roles.add("ROLE_MODERATOR");
        }*/
        return roles;
    }

    private static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        roles.forEach(s -> authorities.add(new SimpleGrantedAuthority(s)));
        return authorities;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
        return getGrantedAuthorities(getRoles(role));
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
            User u = userRepository.findById(id);
            return new org.springframework.security.core.userdetails.User(u.getId(),
                    u.getPw(),
                    enabled,
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    getAuthorities(0));
        } catch (NullPointerException e) {
            log.error("Null ", e);
        } catch (UsernameNotFoundException e) {
            log.error("User Name {} ", id, e);
        }
        return new org.springframework.security.core.userdetails.User("null", "null", false, false, false, false, getAuthorities(0));

    }
}
