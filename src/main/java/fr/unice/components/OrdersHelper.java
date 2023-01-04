package fr.unice.components;

import fr.unice.Exceptions.UserNotFindException;
import fr.unice.interfaces.OrderFinder;
import fr.unice.interfaces.OrderUtilities;
import fr.unice.interfaces.UserFinder;
import fr.unice.model.Order;
import fr.unice.model.User;
import fr.unice.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrdersHelper implements OrderFinder {
    OrderRepository orderRepository;
    UserFinder userFinder;

    @Autowired
    public OrdersHelper(OrderRepository orderRepository, UserFinder userFinder) {
        this.orderRepository = orderRepository;
        this.userFinder = userFinder;
    }


    @Override
    public List<Order> getUserHistory(String email) throws UserNotFindException {
        List<Order> orders = new ArrayList<>();
        User user = userFinder.findUserByMail(email);
        if (user == null)
            throw new UserNotFindException();

        for (Order order : orderRepository.findAll()) {
            if (order.getBuyer() == user)
                orders.add(order);
        }

        return orders;
    }
}
