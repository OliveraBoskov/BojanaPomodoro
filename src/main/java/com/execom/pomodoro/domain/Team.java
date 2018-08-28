package com.execom.pomodoro.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "team")
@Data
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", unique = true)
    @Size(max = 15)
    private String name;

    @Column(name = "description")
    private String description;

    private boolean active;

    public Team(@NotNull @Size(max = 15) String name, String description) {
        this.name = name;
        this.description = description;
    }
}
