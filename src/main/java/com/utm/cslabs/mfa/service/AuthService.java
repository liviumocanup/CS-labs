package com.utm.cslabs.mfa.service;

import com.utm.cslabs.mfa.exception.AuthException;
import com.utm.cslabs.mfa.models.ERole;
import com.utm.cslabs.mfa.models.Role;
import com.utm.cslabs.mfa.models.User;
import com.utm.cslabs.mfa.models.request.LoginRequest;
import com.utm.cslabs.mfa.models.request.RegisterRequest;
import com.utm.cslabs.mfa.security.JwtUtils;
import com.utm.cslabs.mfa.security.UserDetailsImpl;
import com.utm.cslabs.mfa.models.response.JwtResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final RoleService roleService;
    private final MfaService mfaService;

    private final SecurityContext sc = SecurityContextHolder.getContext();

    public byte[] login(LoginRequest loginRequest) {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

        sc.setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String secret = mfaService.generateSecret();
        userService.save(
                userService.findByUsername(userDetails.getUsername()).setSecret(secret)
        );

        return mfaService.generateQrPng(secret, userDetails.getEmail());
    }

    public ResponseEntity<?> confirm(String code) {
        Object principal;
        if(sc.getAuthentication() != null)
            principal = sc.getAuthentication().getPrincipal();
        else throw new AuthException("You have to log in first.");

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        User currentUser = userService.findByUsername(username);

        if (mfaService.verify(currentUser.getSecret(), code)) {
            String jwt = jwtUtils.generateJwtToken(sc.getAuthentication());

            return ResponseEntity.ok(new JwtResponse(jwt));
        }

        throw new AuthException("The Code is incorrect.");
    }

    public void register(RegisterRequest registerRequest) {
        if (userService.existsByUsername(registerRequest.username())) {
            throw new AuthException("Error: Username is already taken!");
        }

        if (userService.existsByEmail(registerRequest.email())) {
            throw new AuthException("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(registerRequest.username(),
                registerRequest.email(),
                encoder.encode(registerRequest.password()));

        Set<String> strRoles = registerRequest.role();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleService.findByName(ERole.CLASSIC);
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (role.equals("classic")) {
                    Role adminRole = roleService.findByName(ERole.CLASSIC);
                    roles.add(adminRole);
                } else {
                    Role userRole = roleService.findByName(ERole.ASYMMETRIC);
                    roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userService.save(user);
    }
}
