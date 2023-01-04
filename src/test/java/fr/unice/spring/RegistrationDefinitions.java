package fr.unice.spring;

import fr.unice.components.UserController;
import fr.unice.model.LoyalMember;
import fr.unice.model.User;
import fr.unice.repositories.UserRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.fail;

public class RegistrationDefinitions {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @Before
    public void setup() { userRepository.deleteAll(); }

    @Given("no user with the {string} exists")
    public void noUserWithTheExists(String email) {
        userRepository.deleteById(email);
    }

    @When("a user registers with {string} and {string}")
    public void aUserRegistersWithAnd(String email, String password) {
        try {
            userController.registerUser(email, password);
        } catch (IllegalArgumentException ignored) { }
    }

    @Given("a user with the {string} is registered")
    public void aUserWithTheIsRegistered(String email) {
        userController.registerUser(email, "");
    }

    @When("the user with the {string} activates the loyalty program")
    public void theUserWithTheActivatesTheLoyaltyProgram(String email) {
        User user = userRepository.findById(email).orElse(null);
        if (user == null)
            fail();
        userController.activateLoyaltyProgram(user);
    }

    @Then("a user with the {string} exists in the system")
    public void aUserWithTheExistsInTheSystem(String email) {
        User user = userController.findUserByMail(email);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(email, user.getMail());
    }

    @Then("the user with the {string} is in the loyalty program")
    public void theUserWithTheEmailIsInTheLoyaltyProgram(String email) {
        User user = userController.findUserByMail(email);
        Assertions.assertTrue(user instanceof LoyalMember);
    }

    @Then("only one user with the {string} exists")
    public void onlyOneUserWithTheExists(String email) {
        List<User> users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .filter(u -> u.getMail().equals(email))
                .toList();
        Assertions.assertEquals(1, users.size());
    }

    @Then("no user with the {string} exists in the system")
    public void noUserWithTheExistsInTheSystem(String email) {
        User user = userController.findUserByMail(email);
        Assertions.assertNull(user);
    }


}
