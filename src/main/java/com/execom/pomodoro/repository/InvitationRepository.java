package com.execom.pomodoro.repository;

import com.execom.pomodoro.domain.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Invitation findOneByActivationLink(String activationLink);

}
