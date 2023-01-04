package fr.unice.spring;

import fr.unice.interfaces.OrderCreator;
import fr.unice.interfaces.OrderProcessing;
import fr.unice.repositories.StoreRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import fr.unice.model.Order;
import fr.unice.model.OrderLine;
import fr.unice.model.recipe.Recipe;
import fr.unice.model.Store;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlaceAnOrderAdvancedDefinitions {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    OrderCreator orderCreator;

    @Autowired
    OrderProcessing orderProcessing;

    Store store;
    Recipe recipe1, recipe2;
    Order order;

    @Given("an advanced store named {string} exists")
    public void an_advanced_store_named_exists(String storeName) {
        storeRepository.initialise();
        store = new Store("random adress", storeName);
        storeRepository.save(store,storeName);
    }

    @Given("a chocolate cookie recipe named {string} exists")
    public void a_chocolate_cookie_recipe_named_exists(String recipeName) {
        recipe1 = mock(Recipe.class);
        when(recipe1.getName()).thenReturn(recipeName);
    }

    @Given("a vanilla cookie recipe named {string} exists")
    public void a_vanilla_cookie_recipe_named_exists(String recipeName) {
        recipe2 = mock(Recipe.class);
        when(recipe2.getName()).thenReturn(recipeName);
    }

    @Given("an empty order without cookies")
    public void an_empty_order_without_cookies() {
        order = orderCreator.createOrder(store,0);
    }

    @When("the client add {int} cookies {string} in the order")
    public void the_client_add_cookies_in_the_order(Integer quantity, String recipeName) {
        Recipe recipe = null;
        if (recipeName.equals(recipe1.getName()))
            recipe = recipe1;
        else if (recipeName.equals(recipe2.getName()))
            recipe = recipe2;

        OrderLine orderLine = new OrderLine(recipe, quantity);
        orderProcessing.addOrderLine(order, orderLine);
    }

    @Then("the order contains {int} of the recipe {string}")
    public void the_order_contains_of_the_recipe(Integer quantity, String recipeName) {
        assertTrue(order.getOrderLines().containsKey(recipeName));
        assertEquals(quantity, order.getOrderLine(recipeName).getQuantity());
    }

    @Given("an empty order without order lines")
    public void an_empty_order_without_order_lines() {
        order = orderCreator.createOrder(store,1);
    }

    @Then("the order has an order line that contain {int} of the recipe {string}")
    public void the_order_has_an_order_line_that_contain_of_the_recipe(Integer quantity, String recipeName) {
        assertTrue(order.getOrderLines().containsKey(recipeName));
        assertEquals(quantity, order.getOrderLine(recipeName).getQuantity());
    }

}
