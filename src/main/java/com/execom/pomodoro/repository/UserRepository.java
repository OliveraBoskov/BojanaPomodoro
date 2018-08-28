package com.execom.pomodoro.repository;

import com.execom.pomodoro.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByActive(boolean active);

    User findUserByUsername(String username);
}
