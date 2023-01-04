package fr.unice.interfaces;

import fr.unice.model.LoyalMember;
import fr.unice.model.User;

public interface UserRegisterer {

    /**
     * Register a new user
     * @param email the email of the user used as a unique id
     * @param phoneNumber number to call the user
     * @return the created user
     */
    User registerUser(String email, String phoneNumber);

    /**
     * Register a new user
     * @param email the email of the user used as a unique id
     * @param phoneNumber number to call the user
     * @param password the password of the account
     * @return the created user
     */
    LoyalMember registerLoyalMember(String email, String phoneNumber, String password);

    /**
     * Activates the loyalty program for a user.
     *
     * @param user the user of which to activate the loyalty program
     * @return the user in the form of a loyalty member with activated loyalty program
     */
    LoyalMember activateLoyaltyProgram(User user);
}
