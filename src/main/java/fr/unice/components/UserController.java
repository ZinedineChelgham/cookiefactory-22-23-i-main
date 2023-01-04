package fr.unice.components;

import fr.unice.interfaces.UserFinder;
import fr.unice.interfaces.UserRegisterer;
import fr.unice.model.LoyalMember;
import fr.unice.model.User;
import fr.unice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserController implements UserRegisterer, UserFinder {
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User registerUser(String email, String phoneNumber) throws IllegalArgumentException {
        String emailRegex = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+[.][a-zA-Z]{2,3}$";
        boolean validEmail = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE)
                .matcher(email)
                .find();

        if (validEmail) {
            User user = new User(email, phoneNumber);
            userRepository.save(user, email);
            return user;
        } else {
            throw new IllegalArgumentException("Email is no legal email");
        }
    }

    @Override
    public LoyalMember registerLoyalMember(String email, String phoneNumber, String password) {
        User user = registerUser(email, phoneNumber);
        user.setPassword(password);
        return activateLoyaltyProgram(user);
    }

    @Override
    public LoyalMember activateLoyaltyProgram(User user) {
        LoyalMember member = new LoyalMember(user);
        userRepository.deleteById(user.getMail());
        userRepository.save(member, user.getMail());
        return member;
    }

    @Override
    public User findUserByMail(String email) {
        return userRepository.findById(email).orElse(null);
    }
}
