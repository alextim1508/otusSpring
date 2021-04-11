package com.alextim.service.working;

import com.alextim.domain.User;
import com.alextim.repository.UserRepository;
import com.alextim.security.GrantedAuthorityImpl;
import com.alextim.security.GrantedAuthorityImpl.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.alextim.service.working.Helper.*;

@Service
@RequiredArgsConstructor @Slf4j
public class UserDetailsServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    @Override
    public User add(String username, String name, String surname, String email, String phone, String rawPassword) {
        User user = User.builder()
                .username(username)
                .name(name)
                .surname(surname)
                .email(email)
                .phone(phone)
                .password(encode(rawPassword))
                .authority(new GrantedAuthorityImpl(Role.GUEST))
                .accountNonExpired(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();

        try {
            user = userRepository.save(user);
        }
        catch (DataIntegrityViolationException exception) {
            String causeMsg= exception.getCause().getCause().getMessage();
            if(causeMsg.contains("Нарушение уникального индекса или первичного ключ"))
                throw new RuntimeException(String.format(DUPLICATE_ERROR_STRING, user));
            else
                throw new RuntimeException(String.format(ERROR_STRING, user));

        }
        return user;
    }

    @Transactional
    @Override
    public void setLock(long id, boolean lock) {
        findById(id).setAccountNonLocked(!lock);
    }

    @Transactional
    @Override
    public void setSms(long id, String sms) {
        findById(id).setSms(sms);
    }

    @Override
    public long getCount() {
        return userRepository.count();
    }

    @Override
    public List<User> getAll(int page, int amountByOnePage) {
        return userRepository.findAll(PageRequest.of(page,amountByOnePage)).getContent();
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, User.class.getSimpleName(), id)));
    }

    @Transactional
    @Override
    public List<User> findByRole(Role role) {
        return userRepository.findByRole(role);
    }

    @Override
    public List<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findByNameOrSurname(String name, String surname) {
        return userRepository.findByNameOrSurname(name, surname);
    }

    @Transactional
    @Override
    public User update(long id, String username, String name, String surname, String email, String rawPassword) {
        User user = findById(id);

        if(username != null)
            user.setUsername(username);
        if(name != null)
            user.setName(name);
        if(surname != null)
            user.setSurname(surname);
        if(email!=null)
            user.setEmail(email);
        if(rawPassword!=null)
            user.setEmail(rawPassword);

        return user;
    }

    @Override
    public void delete(long id) {
        try {
            userRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, User.class.getSimpleName()));
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userRepository.findByUsername(username);
        if(users.isEmpty()) {
            log.info("{} not found", username);
            throw new UsernameNotFoundException(username + " not found");
        }
        log.info("Loaded user from storage: " + users.get(0));
        return users.get(0);
    }

    @Transactional
    @Override
    public void addRoles(long id, Role... role) {
        User user = findById(id);
        Collection<GrantedAuthorityImpl> authorities = user.getAuthorities();
        authorities.addAll(Arrays.stream(role).map(GrantedAuthorityImpl::new).collect(Collectors.toList()));
    }

    @Transactional
    @Override
    public void removeRoles(long id, Role... role) {
        User user = findById(id);
        Collection<GrantedAuthorityImpl> authorities = user.getAuthorities();
        authorities.removeAll(Arrays.stream(role).map(GrantedAuthorityImpl::new).collect(Collectors.toList()));
    }

    public static String encode(String password) {
        return password;
    }
}
