package com.execom.pomodoro.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvitationStatusDto {

    private String accepted; //accept-deny

    public InvitationStatusDto(String accepted) {
        this.accepted = accepted;
    }

}
