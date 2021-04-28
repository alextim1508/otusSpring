package com.alextim.security;

import org.springframework.security.acls.model.Permission;
import org.springframework.security.core.Authentication;

public interface SecurityService {

    void addSecurity(Authentication authentication, long id, Class<?> type);

    void addPermission(long id, Class<?> type, String principal, Permission permission);

    boolean isGranted(long id, Class<?> type, String principal, Permission... permission);
}