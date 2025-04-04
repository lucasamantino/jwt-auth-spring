package com.project.jwt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tb_roles")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> user;

    public  enum Values {
        COMMON,
        ADMIN
    }
}
