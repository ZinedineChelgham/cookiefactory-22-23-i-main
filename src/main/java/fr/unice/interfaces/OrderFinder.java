package fr.unice.interfaces;

import fr.unice.Exceptions.UserNotFindException;
import fr.unice.model.Order;

import java.util.List;

public interface OrderFinder {
    List<Order> getUserHistory(String email) throws UserNotFindException;
}
