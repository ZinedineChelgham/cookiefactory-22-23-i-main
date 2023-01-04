package fr.unice.spring;

import fr.unice.Exceptions.OrderDeliveredErrorException;
import fr.unice.Exceptions.OrderIdErrorException;
import fr.unice.Exceptions.OrderNotFoundException;
import fr.unice.enums.OrderStatus;
import fr.unice.components.OrderRetrieverController;
import fr.unice.repositories.OrderRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import fr.unice.model.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * same fonction are used here for the passed time feature and the pass time one hour feature
 * as they are the same with different time
 */
public class PassedTimeDefinition {
    @Autowired
    OrderRetrieverController control;
    @Autowired
    OrderRepository orderRepository;

    Order order;
    LocalDateTime time;
    LocalDateTime timer;

    @Before
    public void setup(){
        orderRepository.deleteAll();
    }

    @Given("an order id : 12800; and a pickup time for 21 june 2022 at 15:30 pm for a client")
    public void setupOrderTimePickup(){
        time = LocalDateTime.of(2022, 6,21,15,30);
        order = new Order(12800,time);
        orderRepository.save(order,12800);
    }

    @Given("this order finished and not delivered yet")
    public void orderFinishedNotDelivered(){
        orderRepository.findById(12800).get().setStatus(OrderStatus.READY);
    }

    @Given("this order finished and HAS been delivered")
    public void orderDelivered(){
        orderRepository.findById(12800).get().setStatus(OrderStatus.DELIVERED);
    }


    @When("its pass {int} minutes after the pickup time")
    public void timerMinutes(int timeee){
        timer = LocalDateTime.of(2022, 6,21,15,30+timeee);
    }

    @When("its pass {int} hours after the pickup time")
    public void timerHours(int timeee){
        timer = LocalDateTime.of(2022, 6,21,15+timeee,30);
    }

    @When("its pass {int} hours after the pickup time on an non existing order")
    public void timerHoursWrongOrder(int timeee){
        timer = LocalDateTime.of(2022, 6,21,15+timeee,30);
    }

    @Then("client recieve message")
    public void recievedMessage(){
        try {
            control.manageTime(12800,timer);
            assertTrue(orderRepository.findById(12800).get().isSendMessage());
        } catch (OrderIdErrorException | OrderDeliveredErrorException e) {
            e.printStackTrace();
        }
    }

    @Then("client dont recieve message")
    public void noRecievedMessage(){
        try {
            control.manageTime(12800,timer);
            assertFalse(orderRepository.findById(12800).get().isSendMessage());
        } catch (OrderIdErrorException | OrderDeliveredErrorException e) {
            e.printStackTrace();
        }
    }
    @Then("No Message : Order is already delivered")
    public void MessageErrorOrderDelivered() throws OrderIdErrorException {
        boolean valeur = false;
        try{
            control.manageTime(12800,timer);
        }catch (OrderDeliveredErrorException e){
            valeur = true;
        }finally {
            assertTrue(valeur);
        }
    }
    @Then("No Message : Wrong Order Id")
    public void MessageErrorWrongId() throws OrderDeliveredErrorException{
        boolean valeur = false;
        try{
            control.manageTime(12801,timer);
        }catch (OrderIdErrorException e){
            valeur = true;
        }finally {
            assertTrue(valeur);
        }
    }
}
