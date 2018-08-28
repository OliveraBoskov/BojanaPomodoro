package com.execom.pomodoro.repository;

import com.execom.pomodoro.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findAllByActive(boolean active);

    Team findOneById(Long id);
}