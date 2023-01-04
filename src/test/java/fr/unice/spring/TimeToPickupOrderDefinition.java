package fr.unice.spring;

import fr.unice.Exceptions.StoreNotFoundException;
import fr.unice.interfaces.*;
import fr.unice.model.Order;
import fr.unice.model.OrderLine;
import fr.unice.model.Store;
import fr.unice.model.cooks.Cook;
import fr.unice.model.cooks.TimeSlot;
import fr.unice.model.recipe.Recipe;
import fr.unice.repositories.StoreRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TimeToPickupOrderDefinition {
    @Autowired
    StoreRepository storeRepository;

    @Autowired
    OrderCreator orderCreator;

    @Autowired
    OrderProcessing orderProcessing;

    @Autowired
    StoreModifier storeModifier;

    @Autowired
    CookModifier cookModifier;

    @Autowired
    TimeSlotManager timeSlotManager;

    Recipe recipe;
    Order order;
    Cook cook;
    Store store;

    @Before
    public void settingUpContext() {
        storeRepository.deleteAll();
    }

    @Given("a store {string} open from {int} to {int}")
    public void a_store_open_from_to(String storeName, Integer openHour, Integer closeHour) {
        store = new Store("adress", storeName);
        storeModifier.updateOpeningHours(store, LocalTime.of(openHour, 0));
        storeModifier.updateClosingHours(store, LocalTime.of(closeHour, 0));

        storeRepository.save(store, storeName);
    }

    @Given("a recipe {string} which take {int} minutes to be prepared")
    public void a_recipe_which_take_minutes_to_be_prepared(String recipeName, Integer time) {
        recipe = mock(Recipe.class);
        when(recipe.getName()).thenReturn(recipeName);
        when(recipe.getPreparationTime()).thenReturn(time);
    }

    @Given("an order with {int} cookies of the recipe {string}")
    public void an_empty_order_with_cookies_of_the_recipe(Integer quantity, String recipeName) {
        order = orderCreator.createOrder(store, 0);
        OrderLine orderLine = new OrderLine(recipe, quantity);
        orderProcessing.addOrderLine(order,orderLine);
    }

    @Given("a cook who work from {int} to {int}")
    public void a_cook_who_work_from_to(Integer startHour, Integer endHour) {
        cook = new Cook("MyCook");

        LocalTime start = LocalTime.of(startHour, 0);
        LocalTime end = LocalTime.of(endHour, 0);
        cookModifier.defineSchedule(cook, start, end);

        store.getCooks().add(cook);
    }

    @Given("with an empty CookScheduler")
    public void with_an_empty_cook_scheduler() {
        cook.getTimeSlots().clear();
    }

    @When("a client choose the time to pickup the {int}\\/{int}\\/{int} at {int}:{int} for his order")
    public void a_client_choose_the_time_to_pickup_the_at_for_his_order(Integer day, Integer month, Integer year, Integer hour, Integer minute) throws StoreNotFoundException {
        orderProcessing.trySelectPickupTime(order, year, month, day, hour, minute);
    }

    @Then("the order have the time to pickup corresponding to the {int}\\/{int}\\/{int} at {int}:{int}")
    public void the_order_have_the_time_to_pickup_corresponding_to_the_at(Integer day, Integer month, Integer year, Integer hour, Integer minute) {
        LocalDateTime date = LocalDateTime.of(year, month, day, hour, minute);
        assertEquals(date, order.getPickupTime());
    }

    @Then("the cook scheduler of a the cook will have a new timeslot corresponding to the given order")
    public void the_cook_scheduler_of_a_the_cook_will_have_a_new_timeslot_corresponding_to_the_given_order() {
        assertTrue(timeSlotManager.containsOrderInScheduler(cook, order));
    }

    @Then("the timeslot start the {int}\\/{int}\\/{int} at {int}:{int}")
    public void the_timeslot_start_the_at(Integer day, Integer month, Integer year, Integer hour, Integer minute) {
        TimeSlot timeSlot = timeSlotManager.getTimeSlot(cook, order);
        LocalDateTime date = LocalDateTime.of(year, month, day, hour, minute);

        assertNotNull(timeSlot);
        assertEquals(timeSlot.getStartDateTime(), date);
    }

    @Then("the timeslot end the {int}\\/{int}\\/{int} at {int}:{int}")
    public void the_timeslot_end_the_at(Integer day, Integer month, Integer year, Integer hour, Integer minute) {
        TimeSlot timeSlot = timeSlotManager.getTimeSlot(cook, order);
        LocalDateTime date = LocalDateTime.of(year, month, day, hour, minute);

        assertNotNull(timeSlot);
        assertEquals(timeSlot.getEndDateTime(), date);
    }

    @Given("with an CookScheduler with an order with {int} cookies of the recipe {string} the {int}\\/{int}\\/{int} at {int}:{int}")
    public void with_an_cook_scheduler_with_an_order_with_cookies_of_the_recipe_the_at(Integer quantity, String recipeName, Integer day, Integer month, Integer year, Integer hour, Integer minute) throws StoreNotFoundException {
        Order otherOrder = orderCreator.createOrder(store, 1);
        orderProcessing.addOrderLine(otherOrder, new OrderLine(recipe, quantity));
        orderProcessing.trySelectPickupTime(otherOrder, year, month, day, hour, minute);
    }

    @Then("the order have the time set to null")
    public void the_order_have_the_time_set_to_null() {
        assertNull(order.getPickupTime());
    }

    @Then("the cook scheduler of a the cook will not have a new timeslot corresponding to the given order")
    public void the_cook_scheduler_of_a_the_cook_will_not_have_a_new_timeslot_corresponding_to_the_given_order() {
        assertFalse(timeSlotManager.containsOrderInScheduler(cook, order));
    }

}
