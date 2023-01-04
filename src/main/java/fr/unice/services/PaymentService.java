package fr.unice.services;

import fr.unice.model.User;

/**
 * Contains all the functionality to pay an order via an external payment service
 */
public interface PaymentService {
    boolean PayOrder(User user, double price);
}
