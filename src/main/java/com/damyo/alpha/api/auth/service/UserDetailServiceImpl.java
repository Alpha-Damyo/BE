package com.damyo.alpha.api.auth.service;

import com.damyo.alpha.api.user.domain.User;
import com.damyo.alpha.api.user.domain.UserRepository;
import com.damyo.alpha.api.auth.domain.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userRepository.findUserById(UUID.fromString(id))
                .orElseThrow(() -> new UsernameNotFoundException("not found id"));
        return new UserDetailsImpl(user);
    }
}
