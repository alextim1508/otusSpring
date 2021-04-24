package com.alextim.service.working;

import com.alextim.domain.User;
import com.alextim.security.GrantedAuthorityImpl;

import java.util.List;

public interface UserService {

    User add(String username, String name, String surname, String email, String phone, String rawPassword);
    void setLock(long id, boolean lock);
    void setSms(long id, String sms);

    long getCount();
    List<User> getAll(int page, int amountByOnePage);

    User findById(long id);
    List<User> findByRole(GrantedAuthorityImpl.Role role);
    List<User> findByEmail(String email);
    List<User> findByUsername(String username);
    List<User> findByNameOrSurname(String name, String surname);

    void addRoles(long id, GrantedAuthorityImpl.Role... role);
    void removeRoles(long id, GrantedAuthorityImpl.Role... role);

    User update(long id, String username, String name, String surname, String email, String rawPassword);

    void delete(long id);
}
