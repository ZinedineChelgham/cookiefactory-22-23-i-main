package fr.unice.spring;

import fr.unice.components.StoreController;
import fr.unice.interfaces.OrderProcessing;
import fr.unice.interfaces.StoreOrderProcessing;
import fr.unice.model.Store;
import fr.unice.enums.OrderStatus;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import fr.unice.model.*;
import fr.unice.model.cooks.Cook;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class ManageStoreScheduleDefinition {
    @Autowired
    StoreController storeController;

    @Autowired
    StoreOrderProcessing storeOrderProcessing;

    Store store;
    int nbOrder = 0;
    @Given("a store that want to change his schedule")
    public void aStoreWithMultipleOrders() {
        store = new Store("addresse", "name");
        store.addCook(new Cook("bernard0"));
        store.addCook(new Cook("bernard1"));
        store.addCook(new Cook("bernard2"));
    }

    @Given("a store with a list of {int} orders {int},{int},{int},{int},{int},{int},{int}")
    public void aStoreWithAListOfNbOrder(int nbOrder, int openingHour, int openingMinute, int closingHour, int closingMinute, int order1Hour, int order2Hour, int order3Hour) {
        this.nbOrder = nbOrder;
        storeController.updateOpeningHours(store, LocalTime.of(openingHour, openingMinute));
        storeController.updateClosingHours(store, LocalTime.of(closingHour,closingMinute));

        Order order1 = new Order(15);
        order1.setPickupTime(LocalDateTime.of(2014, 7, 10, order1Hour, 0));
        Order order2 = new Order(16);
        order2.setPickupTime(LocalDateTime.of(2014, 7, 10, order2Hour, 0));
        Order order3 = new Order(17);
        order3.setPickupTime(LocalDateTime.of(2014, 7, 10, order3Hour, 0));

        store.addOrder(order1);
        store.addOrder(order2);
        store.addOrder(order3);
        assertTrue(storeOrderProcessing.tryAssociateOrderWithScheduler(store, order1));
        assertTrue(storeOrderProcessing.tryAssociateOrderWithScheduler(store, order2));
        assertTrue(storeOrderProcessing.tryAssociateOrderWithScheduler(store, order3));
    }


    @When("someone change the opening hour of the store {int}, {int}")
    public void someoneChangeTheOpeningHourOfTheStoreNewOpeningHourNewOpeningMinute(int newOpeningHour, int newOpeningMinute) {
        storeController.updateOpeningHours(store, LocalTime.of(newOpeningHour,newOpeningMinute));
    }

    @Then("the orders outside the schedule are delete {int}")
    public void theOrdersOutsideTheScheduleAreDeleteNbOrderLeft(int nbOrderLeft) {
        for (Order order: store.getOrders()){
            if(order.getStatus()==OrderStatus.CANCELLED) nbOrder--;
        }
        assertEquals(nbOrderLeft, nbOrder);
    }

    @When("someone change the closing hour of the store {int}, {int}")
    public void someoneChangeTheClosingHourOfTheStoreNewClosingHourNewClosingMinute(int newClosingHour, int newClosingMinute) {
        storeController.updateClosingHours(store, LocalTime.of(newClosingHour,newClosingMinute));
    }
}