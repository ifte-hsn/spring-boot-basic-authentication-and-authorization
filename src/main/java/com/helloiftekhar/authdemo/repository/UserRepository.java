package com.helloiftekhar.authdemo.repository;

import com.helloiftekhar.authdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByEmail(String email);

    public User findByResetPasswordToken(String token);
}
