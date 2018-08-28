package com.execom.pomodoro.controller;

import com.execom.pomodoro.controller.dto.*;
import com.execom.pomodoro.domain.Invitation;
import com.execom.pomodoro.domain.Team;
import com.execom.pomodoro.domain.User;
import com.execom.pomodoro.service.Mapper;
import com.execom.pomodoro.service.TeamService;
import com.execom.pomodoro.service.UserService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/api/team") public class TeamController {

    private TeamService teamService;
    private Mapper mapper;
    private UserService userService;

    private static Logger log = Logger.getLogger(TeamController.class);

    @Autowired public TeamController(TeamService teamService, Mapper mapper, UserService userService) {
        this.teamService = teamService;
        this.mapper = mapper;
        this.userService = userService;
    }

    @PostMapping public ResponseEntity<TeamDto> createTeam(@RequestBody TeamDto teamDto) {
        Team team = teamService.createTeam(teamDto);
        return new ResponseEntity<>(mapper.teamToTeamDto(team), HttpStatus.CREATED);
    }

    @GetMapping public ResponseEntity<List<TeamDto>> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();
        List<TeamDto> tempList = mapper.teamListToTeamDtoList(teams);
        return new ResponseEntity<>(tempList, HttpStatus.OK);
    }

    @GetMapping("/{id}") public ResponseEntity<TeamDto> getSingleTeam(@PathVariable Long id) {
        Team team = teamService.getSingleTeam(id);
        return new ResponseEntity<>(mapper.teamToTeamDto(team), HttpStatus.OK);
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public ResponseEntity<MessageDto> deleteTeam(
            @PathVariable Long id) {
        MessageDto messageDto = new MessageDto();
        String response;
        teamService.deleteTeam(id);
        response = "User has been deleted";
        messageDto.setMessage(response);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @PutMapping() public ResponseEntity<TeamDto> editTeam(@PathVariable Long id, @RequestBody TeamDto teamDto) {
        Team team = teamService.editTeam(id, teamDto.getName(), teamDto.getDescription());
        return new ResponseEntity<>(mapper.teamToTeamDto(team), HttpStatus.OK);
    }

    @GetMapping("/activate") public ResponseEntity<MessageDto> activateAccount(
            @RequestParam(value = "key") String key) {
        Invitation invitation = teamService.getInvitationByActivationKey(key);
        MessageDto messageDto = new MessageDto();
        String response;
        if (invitation == null) {
            response = "invitation expired";
        } else {
            response = "Decide: accept/deny invitation";
        }
        messageDto.setMessage(response);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    // add invited user to team, check if exists somewhere else
    @PostMapping("/activate")
    public ResponseEntity<MessageDto> activateAccount(@RequestParam(value = "key") String key,
            @RequestBody InvitationStatusDto invitationStatusDto) {
        log.info("-------------------------------------------------------------------------------");
        log.info("key" + key);
        RawInvitationDto rawInvitationDto = teamService.extractDataFromInvitation(
                key); //delete invitation + who is the user
        MessageDto messageDto = new MessageDto();
        String response = "";
        if (invitationStatusDto.getAccepted().equals("false")) {
            response = "invitation rejected";
        } else {
            if (!teamService.checkIfUserExistsInThatTeamByEmail(rawInvitationDto.getEmail(),
                    rawInvitationDto.getTeamId())) {

                InWhichTeamUserFoundDto inWhichTeamUserFoundDto = teamService.checkIfUserExistsInAnyTeam(
                        rawInvitationDto.getEmail());
                log.info("-------------------------------------------------------------------------------");
                log.info(inWhichTeamUserFoundDto.isFound());
                if (inWhichTeamUserFoundDto.isFound()) {
                    teamService.removeUserFromTeam(rawInvitationDto.getEmail(), inWhichTeamUserFoundDto.getTeamId());
                } //izvrsice if, i propasti dole i svakako izvrsiti ovo ispod
                log.info("-------------------------------------------------------------------------------");
                log.info(rawInvitationDto.getEmail() + " " + rawInvitationDto.getTeamId());
                User user = userService.getUserByUsername(rawInvitationDto.getEmail());
                Team team = teamService.getSingleTeam(rawInvitationDto.getTeamId());
                teamService.addUserToTeam(user, team);
                response = "user has been added";
            }
        }
        messageDto.setMessage(response);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @PutMapping("/{id}/leaveTeam") public ResponseEntity<MessageDto> leaveTeam(@PathVariable("id") Long teamId,
            @RequestBody LeaveTeamDto leaveTeamDto) {
        MessageDto messageDto = new MessageDto();
        String response;

        teamService.removeUserFromTeam(leaveTeamDto.getEmail(), teamId);

        response = "User uklonjen iz tima";
        messageDto.setMessage(response);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

}
