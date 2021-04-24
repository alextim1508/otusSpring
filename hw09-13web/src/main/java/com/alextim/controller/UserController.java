package com.alextim.controller;

import com.alextim.controller.dto.*;
import com.alextim.domain.User;
import com.alextim.security.GrantedAuthorityImpl;
import com.alextim.service.working.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.alextim.controller.dto.UserRoleActionDto.Action.ADD;
import static com.alextim.controller.dto.UserRoleActionDto.Action.SUB;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@RestController @RequestMapping("/user")
@RequiredArgsConstructor @Slf4j
public class UserController {

    private final UserService userService;


    @PostMapping()
    public MessageDto saveUser(@Valid @RequestBody UserDto userDto, BindingResult result,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        if(result.hasErrors()) {
            response.setStatus(SC_BAD_REQUEST);
            return new MessageDto("Error input param");
        }

        User user = userService.add(
                userDto.getUsername(),
                userDto.getName(),
                userDto.getSurname(),
                userDto.getEmail(),
                userDto.getPhone(),
                userDto.getRawPassword());

        response.setStatus(SC_OK);
        log.info("{} saved", user);
        return new MessageDto(String.format("%s saved", user));
    }

    @GetMapping("/size")
    public Long getUserCount(HttpServletRequest request,
                             HttpServletResponse response) {
        long count = userService.getCount();
        response.setStatus(SC_OK);
        log.info("User count: {}", count);
        return count;
    }

    @GetMapping()
    public List<UserDto> getUsers(@RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "amountByOnePage", defaultValue = "100") int amountByOnePage,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        List<User> users = userService.getAll(page, amountByOnePage);
        response.setStatus(SC_OK);
        log.info("Users: {}", users);
        return users.stream().map(UserDto::toTransferObject).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") int id,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        User userById = userService.findById(id);
        response.setStatus(SC_OK);
        log.info("Course: {}", userById);
        return UserDto.toTransferObject(userById);
    }

    @GetMapping("/find")
    public List<UserDto> findUsers(@RequestParam(name = "username", defaultValue = "") String username,
                                   @RequestParam(name = "email", defaultValue = "") String email,
                                   @RequestParam(name = "name", defaultValue = "") String name,
                                   @RequestParam(name = "surname", defaultValue = "") String surname,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        List<User> founded = new ArrayList<>();
        if(!username.isEmpty()) {
            founded.addAll(userService.findByUsername(username));
        }
        if(!email.isEmpty()) {
            founded.addAll(userService.findByEmail(email));
        }

        response.setStatus(SC_OK);
        log.info("Founded courses: {}", founded);
        return founded.stream().map(UserDto::toTransferObject).collect(Collectors.toList());
    }

    @PostMapping("/role")
    public MessageDto changeRole(@Valid @RequestBody UserRoleActionDto userRoleAction, BindingResult result,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        if(result.hasErrors()) {
            response.setStatus(SC_BAD_REQUEST);
            return new MessageDto("Error input param");
        }

        User userById = userService.findById(userRoleAction.getId());

        GrantedAuthorityImpl.Role[] roles = userRoleAction.getRoles();
        StringBuilder builder = new StringBuilder();
        String splitter = "";
        for(GrantedAuthorityImpl.Role role:  roles) {
            builder.append(splitter).append(role);
            splitter = ", ";
        }

        if(userRoleAction.getAction() == ADD) {
            userService.addRoles(userRoleAction.getId(), roles);
            log.info("{} add to : {}", builder.toString(), userById);
            return new MessageDto(String.format("%s add to %s " ,builder.toString(), userById));
        }
        else if(userRoleAction.getAction() == SUB) {
            userService.removeRoles(userRoleAction.getId(), roles);
            log.info("{} sub to : {}", builder.toString(), userById);
            return new MessageDto(String.format("%s sub to %s", builder.toString(), userById));
        }
        return new MessageDto(String.format("Unknown action: %s" , userRoleAction.getAction()));
    }

    @PutMapping("/{id}")
    public MessageDto updateUser(@PathVariable("id") int id,
                                 @Valid @RequestBody UserDto userDto,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        User updated = userService.update(id, userDto.getUsername(), userDto.getName(), userDto.getSurname(), userDto.getEmail(), userDto.getRawPassword());
        response.setStatus(SC_OK);
        log.info("User update: {}", updated);
        return new MessageDto(String.format("User update: %s", updated));
    }

    @DeleteMapping("/{id}")
    public MessageDto deleteUserById(@PathVariable("id") int id,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        userService.delete(id);
        response.setStatus(SC_OK);
        log.info("Delete user with id {}", id);
        return new MessageDto(String.format("Delete user with id %d", id));
    }

    @PostMapping("/user/block")
    public MessageDto setLock(@Valid @RequestBody LockUserDto lockUserDto, BindingResult result,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        if(result.hasErrors()) {
            response.setStatus(SC_BAD_REQUEST);
            return new MessageDto("Error input param.");
        }

        userService.setLock(lockUserDto.getUserId(), lockUserDto.isLock());
        response.setStatus(SC_OK);
        log.info("User ");
        return new MessageDto("Delete all users");
    }
}
