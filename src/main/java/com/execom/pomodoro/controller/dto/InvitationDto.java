package com.execom.pomodoro.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class InvitationDto {

    private List<String> userEmails;

    public InvitationDto(List<String> userEmails, Long id) {
        this.userEmails = userEmails;
    }

}
