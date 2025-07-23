package com.pragma.plazoleta.infrastructure.security;

import com.pragma.plazoleta.infrastructure.output.entity.UserEntity;
import com.pragma.plazoleta.infrastructure.output.repository.IUserRepository;
import com.pragma.plazoleta.infrastructure.output.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        String roleName = roleRepository.findById(user.getRoleId())
                .map(r -> r.getName())
                .orElse("USER");
        GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(Collections.singleton(authority))
                .build();
    }
} 