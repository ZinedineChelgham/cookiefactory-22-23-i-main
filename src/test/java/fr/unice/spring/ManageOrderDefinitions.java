package fr.unice.spring;

import fr.unice.CoD;
import fr.unice.interfaces.*;
import fr.unice.model.Store;
import fr.unice.enums.IngredientTag;
import fr.unice.enums.OrderStatus;
import fr.unice.repositories.IngredientRepository;
import fr.unice.repositories.RecipeRepository;
import fr.unice.repositories.Repository;
import fr.unice.repositories.StoreRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import fr.unice.model.*;
import fr.unice.model.cooks.Cook;
import fr.unice.model.recipe.Recipe;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ManageOrderDefinitions {
    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    OrderCreator orderCreator;

    @Autowired
    OrderProcessing orderProcessing;

    @Autowired
    StockModifier stockModifier;

    @Autowired
    CookOrderProcessing cookOrderProcessing;

    @Autowired
    OrderUtilities orderUtilities;

    @Autowired
    TimeSlotManager timeSlotManager;

    Cook cook;
    Order order;


    @Given("a cook {string} with an order")
    public void aCook(String cookName) {
        storeRepository.initialise();
        recipeRepository.initialise();
        ingredientRepository.initialise();

        cook = new Cook(cookName);
        Store store = storeRepository.findById("CookieCooked").get();
        order = orderCreator.createOrder(store,0);
        order.setPickupTime(LocalDateTime.of(2023,1,1,10,00));

        stockModifier.fillStore(store, ingredientRepository.findById(IngredientTag.WHITE_CHOCOLATE).get(), 1);
        stockModifier.fillStore(store, ingredientRepository.findById(IngredientTag.BLACK_CHOCOLATE).get(), 1);
        stockModifier.fillStore(store, ingredientRepository.findById(IngredientTag.MILK_CHOCOLATE).get(), 1);
        stockModifier.fillStore(store, ingredientRepository.findById(IngredientTag.CHOCOLATE_DOUGH).get(), 1);

        Recipe recipe = recipeRepository.findById("3 Chocolates").get();
        assertTrue(orderProcessing.tryAddOrderLineWithStockManagement(order,new OrderLine(recipe, 1)));
        timeSlotManager.tryAddTimeSlot(cook, order);
    }

    @Given("A order is payed")
    public void aOrderIsPayed() {
        orderUtilities.updateStatus(order, OrderStatus.PAYED);
    }

    @When("A cook starts the preparation")
    public void aCookStartsThePreparation() {
        cookOrderProcessing.startPreparingOrder(cook, order);
    }

    @Given("A order is in preparation")
    public void aOrderIsInPreparation() {
        orderUtilities.updateStatus(order, OrderStatus.PAYED);
        cookOrderProcessing.startPreparingOrder(cook, order);
    }

    @When("A cook marks the order as ready")
    public void aCookMarksTheOrderAsReady() {
        cookOrderProcessing.finishPreparingOrder(cook);
    }

    @Then("The order has the Status {string}")
    public void theOrderHasTheStatus(String orderStatus) {
        assertEquals(orderStatus, order.getStatus().name());
    }

    @Then("The cook is available for a new order")
    public void the_cook_is_available_for_a_new_order() {
        assertNull(cook.getOrderToPrepare());
    }

    @And("The ingredients are removed from stock")
    public void theIngredientsAreRemovedFromStock() {
        Store store = storeRepository.findById("CookieCooked").get();

        assertEquals(0, stockModifier.getAmountInStock(store, ingredientRepository.findById(IngredientTag.WHITE_CHOCOLATE).get()));
        assertEquals(0, stockModifier.getAmountInStock(store, ingredientRepository.findById(IngredientTag.BLACK_CHOCOLATE).get()));
        assertEquals(0, stockModifier.getAmountInStock(store, ingredientRepository.findById(IngredientTag.MILK_CHOCOLATE).get()));
        assertEquals(0, stockModifier.getAmountInStock(store, ingredientRepository.findById(IngredientTag.CHOCOLATE_DOUGH).get()));
    }
}
