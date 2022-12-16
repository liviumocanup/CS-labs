package com.utm.cslabs.hash;

import com.utm.cslabs.asymmetric.implementation.RSA;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class PersistUser {
    private final UsersRepository usersRepository;

    @PostConstruct
    public String addUser() {
        String password = "VerySafePassword";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        UserHash savedUser = usersRepository.save(
                new UserHash().setPassword(hashedPassword)
        );

        System.out.println(("Hashed password stored: "+savedUser.getPassword()));
        return savedUser.getPassword();
    }
}
