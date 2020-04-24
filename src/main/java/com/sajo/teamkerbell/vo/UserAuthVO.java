package com.sajo.teamkerbell.vo;

import com.sajo.teamkerbell.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserAuthVO implements UserDetails {
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;


    public UserAuthVO(User user) {
        this.username = user.getName();
        this.password = "{noop}" + user.getPw();
        this.authorities = user.grantAuthority();
        this.accountNonExpired = !user.isExpired(LocalDate.now());
        this.accountNonLocked = !user.isLocked(LocalDate.now());
        this.credentialsNonExpired = !user.isCredentialExpired(LocalDate.now());
        this.enabled = user.isEnabled();
    }
}
