package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.User;
import com.sajo.teamkerbell.repository.UserRepository;
import com.sajo.teamkerbell.vo.UserAuthVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userRepository.findById(id);
        if (user == null) throw new UsernameNotFoundException("user not found");
        return new UserAuthVO(user);
    }
}
