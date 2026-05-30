package com.meows.meows.config;

import com.meows.meows.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthConfig implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthConfig(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("BUSCANDO USUARIO: " + username);
        return userRepository.findByNome(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
