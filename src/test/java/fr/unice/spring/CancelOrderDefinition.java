package fr.unice.spring;

import fr.unice.enums.IngredientType;
import fr.unice.enums.OrderStatus;
import fr.unice.interfaces.OrderProcessing;
import fr.unice.interfaces.StockModifier;
import fr.unice.interfaces.StoreIngredientsLocker;
import fr.unice.interfaces.TimeSlotManager;
import fr.unice.model.*;
import fr.unice.model.cooks.Cook;
import fr.unice.model.cooks.TimeSlot;
import fr.unice.model.recipe.Recipe;
import fr.unice.model.recipe.RecipeBuilder;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class CancelOrderDefinition {
    @Autowired
    StockModifier stockModifier;

    @Autowired
    OrderProcessing orderProcessing;

    @Autowired
    StoreIngredientsLocker storeIngredientsLocker;

    @Autowired
    TimeSlotManager timeSlotManager;

    Store store;
    Order order;
    Recipe recipe;
    Cook cook;
    List<Ingredient> ingredients;
    Ingredient dough, flavour;

    @Given("a recipe {string} is made with ingredients {string} and {string}, with flavour {string} and with dough {string}")
    public void a_recipe_is_made_with_ingredients_and_with_flavour_and_with_dough(String recipeName, String ingredientName1, String ingredientName2, String flavourName, String doughName) {
        ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(ingredientName1, 1, IngredientType.TOPPING));
        ingredients.add(new Ingredient(ingredientName2, 1, IngredientType.TOPPING));

        dough = new Ingredient(doughName, 1, IngredientType.DOUGH);
        flavour = new Ingredient(flavourName, 1, IngredientType.BASIS);

        RecipeBuilder r = new RecipeBuilder(recipeName)
                .withToppings(ingredients)
                .withDough(dough)
                .withFlavor(flavour);
        recipe = r.getResult();
    }
    @Given("a store {string} with the {int} of these ingredients locked and {int} available")
    public void a_store_with_the_of_these_ingredients_locked(String storeName, Integer quantityLocked, Integer quantityAvailable) {
        store = new Store("adress", storeName);
        for (Ingredient ingredient : ingredients) {
            stockModifier.fillStore(store, ingredient, quantityLocked + quantityAvailable);
            storeIngredientsLocker.lockIngredient(store, ingredient.name(), quantityLocked);
        }
        stockModifier.fillStore(store, dough, quantityLocked + quantityAvailable);
        stockModifier.fillStore(store, flavour, quantityLocked + quantityAvailable);

        storeIngredientsLocker.lockIngredient(store, dough.name(), quantityLocked);
        storeIngredientsLocker.lockIngredient(store, flavour.name(), quantityLocked);
    }
    @Given("a cook with an empty agenda")
    public void a_cook_with_an_empty_agenda() {
        cook = new Cook("Ayman");
        store.getCooks().add(cook);
    }
    @Given("A order with {string} status")
    public void a_order_with_status(String orderStatus) {
        order = new Order(0);
        order.setStore(store);
        order.setStatus(OrderStatus.valueOf(orderStatus));
    }
    @Given("{int} cookie of the recipe {string}")
    public void cookie_of_the_recipe(Integer quantity, String recipeName) {
        OrderLine orderLine = new OrderLine(recipe, quantity);
        order.getOrderLines().put(recipeName, orderLine);
    }
    @Given("in the cook agenda")
    public void in_the_cook_agenda() {
        LocalDateTime start = LocalDateTime.of(2022, 12, 10, 10, 00);
        LocalDateTime end = LocalDateTime.of(2022, 12, 10, 11, 00);
        cook.getTimeSlots().add(new TimeSlot(start, end, order));
    }
    @When("The client cancel the order")
    public void theClientCancelTheOrder() {
        orderProcessing.cancelOrder(order);
    }
    @Then("the order status change to {string}")
    public void the_order_status_change_to(String canceledOrder) {
        assertEquals(OrderStatus.valueOf(canceledOrder).getLabel(), order.getStatus().getLabel());
    }
    @Then("the cook agenda is empty")
    public void the_cook_agenda_is_empty() {
        assertFalse(timeSlotManager.containsOrderInScheduler(cook, order));
    }
    @Then("there is {int} ingredient locked and {int} available of the recipe {string} in the stock")
    public void there_is_ingredient_locked_of_the_recipe_in_the_stock(Integer quantityLocked, Integer quantityAvailable, String recipeName) {
        for (Stock stock : store.getStock().values()) {
            assertEquals(quantityLocked, stock.getLockedQuantity());
            assertEquals(quantityAvailable, stock.getStockQuantity());
        }
    }
    @Then("the cook agenda still contains the order")
    public void the_cook_agenda_still_contains_the_order() {
        assertTrue(timeSlotManager.containsOrderInScheduler(cook, order));
    }
}

