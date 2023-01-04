package fr.unice.components;

import fr.unice.interfaces.OrderCreator;
import fr.unice.model.Order;
import fr.unice.model.Store;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderControllerTest {
    @Autowired
    OrderCreator orderCreator;

    @Test
    public void test() {
        Order order = orderCreator.createOrder(new Store("123", "abc"), 0);
        assertNotNull(order);
    }
}
