package com.helloiftekhar.authdemo.repository;

import com.helloiftekhar.authdemo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
