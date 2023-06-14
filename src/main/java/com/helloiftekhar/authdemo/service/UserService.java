package com.helloiftekhar.authdemo.service;

import com.helloiftekhar.authdemo.model.Role;
import com.helloiftekhar.authdemo.model.User;
import com.helloiftekhar.authdemo.repository.RoleRepository;
import com.helloiftekhar.authdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    public boolean save(User user) {
        if (loadUserByUsername(user.getEmail()) != null) {
            return false;
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
            user = userRepository.save(user);

            return user.getId() != null || user.getId() != 0;
        }
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

}
