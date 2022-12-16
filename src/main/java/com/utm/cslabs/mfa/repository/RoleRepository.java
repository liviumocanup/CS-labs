package com.utm.cslabs.mfa.repository;

import com.utm.cslabs.mfa.models.ERole;
import com.utm.cslabs.mfa.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
