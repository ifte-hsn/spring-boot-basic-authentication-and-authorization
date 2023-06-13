package com.helloiftekhar.authdemo.repository;

import com.helloiftekhar.authdemo.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
}
