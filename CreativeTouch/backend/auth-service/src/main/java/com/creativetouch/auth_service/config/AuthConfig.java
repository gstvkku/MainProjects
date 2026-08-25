package com.creativetouch.auth_service.config;

import com.creativetouch.auth_service.repository.UserRespository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthConfig implements UserDetailsService {

    private final UserRespository userRespository;

    @Override
    public UserDetails loadUserByUsername(String userMail) throws UsernameNotFoundException {
        return userRespository.findUserByEmail(userMail).orElseThrow(() -> new UsernameNotFoundException(userMail));
    }
}
