package com.example.the_bar_app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
    }
)
@Getter @Setter @AllArgsConstructor @Builder
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String username;

    @Column(nullable = false, length = 180)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    private boolean enabled = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 30)
    private RoleName role;

    public User() {

    }
}