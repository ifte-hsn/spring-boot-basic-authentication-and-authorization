package com.helloiftekhar.authdemo.service;

import com.helloiftekhar.authdemo.model.PasswordResetToken;
import com.helloiftekhar.authdemo.model.User;
import com.helloiftekhar.authdemo.repository.PasswordResetTokenRepository;
import com.helloiftekhar.authdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    public void savePasswordResetToken(PasswordResetToken token) {
        passwordResetTokenRepository.save(token);
    }
}
