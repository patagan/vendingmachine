package com.mvp.vending.service.implementation;

import com.mvp.vending.data.entity.Users;
import com.mvp.vending.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users foundUsers = userRepository.findByUsername(username).orElseThrow();
        return new User(foundUsers.getUsername(), foundUsers.getPassword(),
                new ArrayList<>(Collections.singleton(new SimpleGrantedAuthority(foundUsers.getRole()))));

    }
}
