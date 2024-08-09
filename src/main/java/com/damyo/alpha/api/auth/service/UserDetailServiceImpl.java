package com.damyo.alpha.api.auth.service;

import com.damyo.alpha.api.user.domain.User;
import com.damyo.alpha.api.user.domain.UserRepository;
import com.damyo.alpha.api.auth.domain.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userRepository.findUserById(UUID.fromString(id))
                .orElseThrow(() -> {
                    log.error("[Auth]: user name is not found by id | {}", id);
                    return new UsernameNotFoundException("not found id");
                });
        log.info("[Auth]: user load by name complete");
        return new UserDetailsImpl(user);
    }
}
