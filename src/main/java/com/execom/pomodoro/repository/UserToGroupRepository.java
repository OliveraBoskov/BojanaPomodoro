package com.execom.pomodoro.repository;

import com.execom.pomodoro.domain.Team;
import com.execom.pomodoro.domain.User;
import com.execom.pomodoro.domain.UserToGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserToGroupRepository extends JpaRepository<UserToGroup, Long> {

    List<UserToGroup> findAllByTeam(Team team);

    UserToGroup findOneByTeamAndUser(Team team, User user);
}
