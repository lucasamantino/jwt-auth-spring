package com.project.jwt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
public class User {
    @Column(name = "user_id")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String userName;
    private String password;
    private String email;
    private int age;
    private float height;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public boolean credentialsTest (String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.password);
    }
}
