package fr.unice.spring;

import fr.unice.Exceptions.BadUserInformationsException;
import fr.unice.interfaces.*;
import fr.unice.model.Order;
import fr.unice.model.Store;
import fr.unice.model.User;
import fr.unice.repositories.OrderRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class GetClientHistoryDefinitions {
    @Autowired
    UserRegisterer userRegisterer;

    @Autowired
    StoreManager storeManager;

    @Autowired
    OrderCreator orderCreator;

    @Autowired
    OrderProcessing orderProcessing;

    @Autowired
    OrderFinder orderFinder;

    @Autowired
    OrderRepository orderRepository;

    Store store;
    User user;
    boolean historyException;
    List<Order> orderList;

    @Before
    public void initialisation() {
        orderRepository.deleteAll();
    }

    @Given("a existing client with email {string}")
    public void a_existing_client_with_email(String mail) {
        user = userRegisterer.registerUser(mail, "");
    }

    @Given("a store {string}")
    public void a_store(String storeName) {
        store = new Store("MyAdress", storeName);
        storeManager.addStore(store);
    }

    @Given("previous {int} orders ordered by user with email {string}")
    public void previous_orders_ordered_by_user_with_email(Integer nb, String userMail) throws BadUserInformationsException {
        for (int i = 0; i < nb; i++) {
            Order order = orderCreator.createOrder(store, i);
            orderProcessing.associateUserInfoInOrder(order, userMail, "");
        }
    }

    @When("the client {string} get is history")
    public void the_client_get_is_history(String mail) {
        historyException = false;
        try {
            orderList = orderFinder.getUserHistory(mail);
        } catch (Exception e) {
            historyException = true;
        }
    }

    @Then("the client get his {int} previous orders")
    public void the_client_get_his_previous_orders(Integer nb) {
        assertFalse(historyException);
        assertEquals(nb, orderList.size());
        for (Order order : orderList) {
            assertEquals(user.getMail(), order.getBuyer().getMail());
        }
    }

    @Then("the client have an error")
    public void the_client_have_an_error() {
        assertTrue(historyException);
    }

    @Given("no order ordered by user with email {string}")
    public void no_order_ordered_by_user_with_email(String nb) {
    }
}
