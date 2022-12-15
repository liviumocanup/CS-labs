package com.utm.cslabs;

import com.utm.cslabs.hash.PersistUser;
import com.utm.cslabs.hash.UserHash;
import com.utm.cslabs.hash.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class HashTest {
    private final UsersRepository usersRepository = mock(UsersRepository.class);

    private PersistUser persistUser;

    private UserHash user, user1;

    @BeforeEach
    void setUp() {
        persistUser = new PersistUser(usersRepository);
        String password = "VerySafePassword";

        String hashPass = BCrypt.hashpw(password, BCrypt.gensalt());
        user = new UserHash().setPassword(hashPass);

        String hashPass1 = BCrypt.hashpw(password, BCrypt.gensalt());
        user1 = new UserHash().setPassword(hashPass1);
    }

    @Test
    void addUser() {
        doReturn(user).when(usersRepository).save(any());

        assertEquals(user.getPassword(), persistUser.addUser());
    }

    @Test
    void differentPassword() {
        doReturn(user).when(usersRepository).save(any());

        assertNotEquals(user1.getPassword(), persistUser.addUser());
        assertNotEquals(user, user1);
    }
}
