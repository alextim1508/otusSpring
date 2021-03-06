package com.alextim.controller;

import com.alextim.controller.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    @GetMapping("/public_test")
    public MessageDto publicData(HttpServletRequest request,
                                 HttpServletResponse response) {
        response.setStatus(SC_OK);
        return new MessageDto("public");
    }

    @GetMapping("/authenticated_test")
    public MessageDto sucurityData(HttpServletRequest request,
                                    HttpServletResponse response) {
        response.setStatus(SC_OK);
        return new MessageDto("authenticated only");
    }

    @GetMapping("/login")
    public MessageDto loginPage(HttpServletRequest request,
                                HttpServletResponse response) {
        response.setStatus(SC_FORBIDDEN);
        return new MessageDto("Unauthorized. Have come post request witch username, password to '/login'.");
    }

    @PostMapping("/login_success1")
    public MessageDto successPrimaryAuthenticationPage(HttpServletRequest request,
                                                       HttpServletResponse response) {
        response.setStatus(SC_OK);
        return new MessageDto("PrimaryAuthenticationToken Ok. Have come post request witch username, sms to '/login'.");
    }

    @PostMapping("/login_success2")
    public MessageDto successSecondaryAuthenticationPage(HttpServletRequest request,
                                                         HttpServletResponse response) {
        response.setStatus(SC_OK);
        return new MessageDto("SecondaryAuthenticationToken Ok. Authorized.");
    }

    @PostMapping("/login_success")
    public MessageDto successUsernamePasswordAuthenticationPage(HttpServletRequest request,
                                                       HttpServletResponse response) {
        response.setStatus(SC_OK);
        return new MessageDto("UsernamePasswordAuthenticationToken Ok.");
    }

    @GetMapping("/logout_success")
    public MessageDto successLogoutPage(HttpServletRequest request,
                                        HttpServletResponse response) {
        response.setStatus(SC_OK);
        return new MessageDto("Logout Ok.");
    }

    @PostMapping("/login_fail")
    public MessageDto failLoginPage(HttpServletRequest request,
                                        HttpServletResponse response) {
        response.setStatus(SC_OK);
        return new MessageDto("Login fail.");
    }
}
