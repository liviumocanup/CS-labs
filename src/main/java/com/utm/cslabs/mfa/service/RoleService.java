package com.utm.cslabs.mfa.service;

import com.utm.cslabs.mfa.models.ERole;
import com.utm.cslabs.mfa.models.Role;
import com.utm.cslabs.mfa.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findByName(ERole role){
        return roleRepository.findByName(role)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    }
}
