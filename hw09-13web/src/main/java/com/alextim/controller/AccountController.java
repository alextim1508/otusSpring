package com.alextim.controller;

import com.alextim.controller.dto.MessageDto;
import com.alextim.controller.dto.UserDto;
import com.alextim.service.gateway.EmailService;
import com.alextim.service.gateway.JwtService;
import com.alextim.service.working.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@RestController @RequestMapping("/account")
@RequiredArgsConstructor @Slf4j
public class AccountController {

    @Value("${application.url}")
    public String url;

    @Value("${application.emailName}")
    public String emailName;

    private final UserService userService;

    private final JwtService jwtService;

    private final EmailService serviceEmail;

    @PostMapping()
    public MessageDto accountCreationRequest(@Valid @RequestBody UserDto userDto, BindingResult result,
                                             HttpServletRequest request,
                                             HttpServletResponse response) {

        if(result.hasErrors()) {
            response.setStatus(SC_BAD_REQUEST);
            return new MessageDto("Error input param");
        }

        if(!userService.findByEmail(userDto.getEmail()).isEmpty() || !userService.findByUsername(userDto.getUsername()).isEmpty()) {
            response.setStatus(SC_BAD_REQUEST);
            return new MessageDto("Email or username is not unique");
        }

        serviceEmail.send(userDto.getEmail(), emailName,url + "/account/parser?jwt=" + jwtService.createJwt(userDto));

        response.setStatus(SC_OK);

        return new MessageDto("Check email: " + userDto.getEmail());
    }

    @GetMapping("/parser")
    public MessageDto parser(@RequestParam String jwt,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        UserDto userDto = jwtService.parser(jwt);

        userService.add(
                userDto.getUsername(),
                userDto.getName(),
                userDto.getSurname(),
                userDto.getEmail(),
                userDto.getPhone(),
                userDto.getRawPassword());

        response.setStatus(SC_OK);
        log.info("{} saved", userDto.getUsername());
        return new MessageDto(userDto.getUsername() + " saved");
    }
}
