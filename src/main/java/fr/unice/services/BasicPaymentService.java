package fr.unice.services;

import fr.unice.model.User;
import org.springframework.stereotype.Component;

@Component
public class BasicPaymentService implements PaymentService {

    @Override
    public boolean PayOrder(User user, double price) {
        return price >= 0;
    }
}
