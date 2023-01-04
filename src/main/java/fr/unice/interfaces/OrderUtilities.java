package fr.unice.interfaces;

import fr.unice.enums.OrderStatus;
import fr.unice.model.Order;
import fr.unice.model.OrderLine;

public interface OrderUtilities {
    /**
     * Calculate the preparation duration in minute to prepare all the cookies in the order
     * @return Preparation duration
     */
    void updateStatus(Order order, OrderStatus status);
}
