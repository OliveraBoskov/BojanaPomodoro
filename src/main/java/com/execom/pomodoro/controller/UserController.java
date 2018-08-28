package com.execom.pomodoro.controller;

import com.execom.pomodoro.controller.dto.InvitationDto;
import com.execom.pomodoro.controller.dto.UserDto;
import com.execom.pomodoro.domain.Invitation;
import com.execom.pomodoro.domain.User;
import com.execom.pomodoro.repository.UserRepository;
import com.execom.pomodoro.service.MailService;
import com.execom.pomodoro.service.Mapper;
import com.execom.pomodoro.service.UserService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    private Mapper mapper;

    private UserRepository userRepository;

    private MailService mailService;

    private static Logger log = Logger.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, Mapper mapper, UserRepository userRepository,
            MailService mailService) {
        this.userService = userService;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> tempList = userService.getAllUsers();
        List<UserDto> temp2List = mapper.userListToUserDtoList(tempList);
        return new ResponseEntity<>(temp2List, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Long id) {
        User user = userService.getSingleUser(id);
        return new ResponseEntity<>(mapper.userToUserDto(user), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User user = userService.createUser(userDto);
        return new ResponseEntity<>(mapper.userToUserDto(user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> editUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User user = userService.editUser(id, userDto);
        return new ResponseEntity<>(mapper.userToUserDto(user), HttpStatus.OK);
    }

    @GetMapping("/login")
    public Principal login(Principal principal) {
        return principal;
    }

    @PostMapping("/{id}/sendInvitation")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendInvitation(@RequestBody InvitationDto invitationDto, @PathVariable Long id) throws MessagingException {
        log.info("-------------------------------------------------------------------------------");
        log.info("usao je u kontroler");
        for (String username : invitationDto.getUserEmails()) {
            log.info("-------------------------------------------------------------------------------");
            log.info("usao " + username);
            Invitation invitation = userService.createInvitation(username, id);
            mailService.sendInvitationMail("Olivera", invitation);
        }
    }
}