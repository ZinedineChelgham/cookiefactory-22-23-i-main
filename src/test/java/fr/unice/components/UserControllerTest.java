package fr.unice.components;

import fr.unice.interfaces.UserFinder;
import fr.unice.interfaces.UserRegisterer;
import fr.unice.model.LoyalMember;
import fr.unice.model.User;
import fr.unice.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserControllerTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRegisterer userRegisterer;

    @Autowired
    private UserFinder userFinder;

    @BeforeEach
    public void setupContext() {
        userRepository.deleteAll();
        userRepository.save(new User("user", "1234"), "user");
        userRepository.save(new LoyalMember("member", "1234", 1), "member");
    }

    @Test
    public void findUser() {
        User user = userFinder.findUserByMail("user");
        assertNotNull(user);
        assertEquals("user", user.getMail());
    }

    @Test
    public void findMember() {
        User user = userFinder.findUserByMail("member");
        assertNotNull(user);
        assertTrue(user instanceof LoyalMember);
        LoyalMember member = (LoyalMember) user;
        assertEquals("member", member.getMail());
        assertEquals(1, member.getCookiesSinceLastLoyalty());
    }

    @Test
    public void findNotExistingUser() {
        User user = userFinder.findUserByMail("does not exist");
        assertNull(user);
    }

    @Test
    public void registerUser() {
        String mail = "newUser@poly.fr";
        User user = userRegisterer.registerUser(mail, "12345");
        assertNotNull(user);
        assertEquals(user, userFinder.findUserByMail(mail));
        assertEquals(mail, user.getMail());
    }

    @Test
    public void registerMember() {
        String mail = "newMember@poly.fr";
        User user = userRegisterer.registerUser(mail, "12345");
        LoyalMember member = userRegisterer.activateLoyaltyProgram(user);
        assertEquals(user.getMail(), member.getMail());
        assertEquals(0, member.getCookiesSinceLastLoyalty());
    }

    @Test
    public void registerWithBadMail() {
        List<String> mails = List.of("newMember", "newMember@test", "newMember.fr", "@newMember.fr", "newMember@test.12");
        for (String mail: mails) {
            assertThrows(IllegalArgumentException.class, () -> {
                userRegisterer.registerUser(mail, "12345");
            });
        }
    }
}
