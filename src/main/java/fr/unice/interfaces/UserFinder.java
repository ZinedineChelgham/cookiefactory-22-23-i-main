package fr.unice.interfaces;

import fr.unice.model.User;

public interface UserFinder {

    /**
     * Finds a user in the system by its mail
     * @param email the unique id of the user
     * @return the found user or null of no user with the given mail exists
     */
    User findUserByMail(String email);
}
