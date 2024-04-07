package com.damyo.alpha.security;

import com.damyo.alpha.domain.User;
import com.damyo.alpha.exception.errorCode.AuthErrorCode;
import com.damyo.alpha.exception.exception.AuthException;
import com.damyo.alpha.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.damyo.alpha.exception.errorCode.AuthErrorCode.EMAIL_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new AuthException(EMAIL_NOT_FOUND));
        return new UserDetailsImpl(user);
    }
}
