package com.execom.pomodoro.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "username", unique = true)
    private String username;

    @NotNull
    private boolean active;

    @OneToMany(mappedBy = "user")
    private Set<Pomodoro> pomodoro;

    public User(@NotNull String username) {
        this.username = username;
    }
}
