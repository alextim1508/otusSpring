package com.alextim.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final MutableAclService aclService;

    @Override
    public void addSecurity(Authentication authentication, long id, Class<?> type) {

        ObjectIdentityGenerator generator = new ObjectIdentityRetrievalStrategyImpl();
        ObjectIdentity identity = generator.createObjectIdentity(id, type.getName());

        AuditableAcl acl = (AuditableAcl)aclService.createAcl(identity);

        Sid sidOwner = new PrincipalSid(authentication);

        acl.setOwner(sidOwner);
        acl.setParent(null);
        acl.setEntriesInheriting(false);

        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, sidOwner, true);
        acl.updateAuditing(acl.getEntries().size()-1, true, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, new GrantedAuthoritySid("ROLE_ADMIN"), true);
        acl.updateAuditing(acl.getEntries().size()-1, true, true);

        aclService.updateAcl(acl);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void addPermission(long id, Class<?> type, String principal, Permission permission) {
        ObjectIdentity identity = new ObjectIdentityImpl(type.getName(), id);
        MutableAcl acl = (MutableAcl)aclService.readAclById(identity);
        acl.insertAce(acl.getEntries().size(), permission, new PrincipalSid(principal), true);
    }

    @Override
    public boolean isGranted(long id, Class<?> type, String principal, Permission... permission) {
        ObjectIdentity oid = new ObjectIdentityImpl(type.getName(), id);
        Acl acl = aclService.readAclById(oid);
        List<Permission> permissions = Arrays.asList(permission);
        List<Sid> sids = Collections.singletonList(new PrincipalSid(principal));

        if (!acl.isGranted(permissions, sids, true)) {
            throw new RuntimeException("Access denied.");
        }
        return true;
    }
}