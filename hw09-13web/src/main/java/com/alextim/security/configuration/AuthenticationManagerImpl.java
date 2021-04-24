package com.alextim.security.configuration;

import com.alextim.domain.User;
import com.alextim.security.GrantedAuthorityImpl;
import com.alextim.security.token.PrimaryAuthenticationToken;
import com.alextim.security.token.SecondaryAuthenticationToken;
import com.alextim.service.gateway.SmsService;
import com.alextim.service.working.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor @Slf4j
public class AuthenticationManagerImpl implements AuthenticationManager {

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final SmsService smsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("Old authentication " + authentication);

        Authentication newAuthentication = authentication;

        if(authentication.getClass().equals(UsernamePasswordAuthenticationToken.class) && !authentication.isAuthenticated()) {
            log.info("Первичная авторизация");
            User user = getUserByAuthentication(authentication);

            if(passwordEncoder.matches((CharSequence)authentication.getCredentials(), user.getPassword())) {

                Set<GrantedAuthorityImpl> authorities = user.getAuthorities();
                if(authorities.contains(new GrantedAuthorityImpl(GrantedAuthorityImpl.Role.ADMIN))) {
                    log.info("Первичная авторизация OK");
                    newAuthentication = new PrimaryAuthenticationToken(user, "[PROTECTED]");
                    String sms = smsService.generationSms();
                    smsService.sendSms(user.getPhone(), sms);
                    userService.setSms(user.getId(), sms);
                }
                else{
                    log.info("Простая (не админская) авторизация OK");
                    newAuthentication = new UsernamePasswordAuthenticationToken(user, "[PROTECTED]", user.getAuthorities());
                }
            }
            else {
                log.warn("PrimaryAuthenticationException");
                throw new BadCredentialsException("PrimaryAuthenticationException");
            }
        }
        if(authentication.getClass().equals(SecondaryAuthenticationToken.class) && !authentication.isAuthenticated()) {
            log.info("Вторичная авторизация");
            User user = getUserByAuthentication(authentication);

            if (authentication.getCredentials().equals(user.getSms())) {
                log.info("Вторичная авторизация OK");
                newAuthentication = new SecondaryAuthenticationToken(user, "[PROTECTED]", user.getAuthorities());
            }
            else {
                log.warn("SecondaryAuthenticationException");
                throw new BadCredentialsException("SecondaryAuthenticationException");
            }
        }

        log.info("New authentication: " + newAuthentication);
        return newAuthentication;
    }

    private User getUserByAuthentication(Authentication authentication) {
        List<User> byUsername = userService.findByUsername((String) authentication.getPrincipal());
        if(byUsername.isEmpty())
            throw new UsernameNotFoundException("User " + authentication.getPrincipal() + " not found!");
        return byUsername.get(0);
    }
}