package fr.unice.spring;

import fr.unice.Exceptions.OrderNotFoundException;
import fr.unice.Exceptions.OrderNotPreparedException;
import fr.unice.components.OrderRetrieverController;
import fr.unice.enums.OrderStatus;
import fr.unice.repositories.OrderRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import fr.unice.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveredOrderDefinition {
    @Autowired
    OrderRetrieverController control;
    @Autowired
    OrderRepository orderRepository;

    Order anOrder;

    @Before
    public void setup(){
        orderRepository.deleteAll();
    }

    @Given("order id : 12780")
    public void AnOrder(){
        anOrder = new Order(12780);
    }

    @When("the order is ready and client come with his known id")
    public void OrderReadyAndClientHere(){
        anOrder.setStatus(OrderStatus.READY);
        orderRepository.save(anOrder,12780);
    }
    @When("the order is not ready and client come with his known id")
    public void OrderNotReadyAndClientHere(){
        anOrder.setStatus(OrderStatus.PREPARATION);
        orderRepository.save(anOrder,12780);
    }

    @Then("Order {int} is delivered")
    public void OrderDelivered(int id){
        try{
            control.deliverOrderById(id);
            assertEquals(orderRepository.findById(12780).get().getStatus(),OrderStatus.DELIVERED);
        }catch (OrderNotFoundException | OrderNotPreparedException e) {
            e.printStackTrace();
        }
    }

    @Then("No Delivery for Order {int} : the order is not found")
    public void OrderNotFound(int id) throws OrderNotPreparedException {
        boolean valeur = false;
        try{
            control.deliverOrderById(id);
        }catch (OrderNotFoundException e){
            valeur = true;
        }finally {
            assertTrue(valeur);
        }
    }

    @Then("No Delivery for Order {int} : the order is not ready")
    public void OrderNotReady(int id) throws OrderNotFoundException {
        boolean valeur = false;
        try{
            control.deliverOrderById(id);
        }catch (OrderNotPreparedException e){
            valeur = true;
        }finally {
            assertTrue(valeur);
        }
    }
}
