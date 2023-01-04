package fr.unice.interfaces;

import fr.unice.Exceptions.OrderDeliveredErrorException;
import fr.unice.Exceptions.OrderIdErrorException;
import fr.unice.model.Order;

import java.time.LocalDateTime;

/**
 * Contains all the functionality to send a sms.
 */
public interface SmsService {
    void manageTime(int idOrder, LocalDateTime time) throws OrderIdErrorException, OrderDeliveredErrorException;
}
