package com.project.jwt.repository;

import com.project.jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUserName(String userName);
    boolean existsByUserName(String userName);
}

