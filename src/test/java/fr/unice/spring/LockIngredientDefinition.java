package fr.unice.spring;

import fr.unice.CoD;
import fr.unice.enums.IngredientTag;
import fr.unice.enums.IngredientType;
import fr.unice.interfaces.*;
import fr.unice.model.Ingredient;
import fr.unice.model.Order;
import fr.unice.model.OrderLine;
import fr.unice.model.Store;
import fr.unice.model.recipe.Recipe;
import fr.unice.model.recipe.RecipeBuilder;
import fr.unice.repositories.IngredientRepository;
import fr.unice.repositories.RecipeRepository;
import fr.unice.services.CatalogService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.naming.ServiceUnavailableException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class LockIngredientDefinition {
    @Autowired
    OrderProcessing orderProcessing;

    @Autowired
    StockModifier stockModifier;

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    OrderCreator orderCreator;

    @Autowired
    private CatalogService catalogServiceMock;

    @Autowired
    IngredientsUpdater ingredientsUpdater;

    CoD cod;
    Store store;
    Order order;
    Recipe recipe;
    List<Ingredient> ingredients;
    Ingredient dough, flavour;

    @Given("a recipe {string} is made with ingredients {string} and {string}, with dough {string} and with flavour {string}")
    public void a_recipe_is_made_with_ingredients_and_with_dough_and_with_flavour(String recipeName, String ingredientName1, String ingredientName2, String doughName, String flavourName) {
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

    @Given("a store {string} with the {int} of these ingredients")
    public void a_store_with_the_of_each_ingredients(String storeName, Integer quantity) throws ServiceUnavailableException {
        store = new Store("adress", storeName);
        for (Ingredient ingredient : ingredients) {
            stockModifier.fillStore(store, ingredient, quantity);
        }
        stockModifier.fillStore(store, dough, quantity);
        stockModifier.fillStore(store, flavour, quantity);
    }

    @Given("an order A")
    public void an_order_a() {
        order = orderCreator.createOrder(store, 0);
    }

    @When("the client add {int} cookies of the recipe {string} in the order")
    public void the_client_add_cookies_of_the_recipe_in_the_order(Integer quantity, String recipeName) {
        orderProcessing.tryAddOrderLineWithStockManagement(order, new OrderLine(recipe, quantity));
    }

    @Then("the store have {int} of each ingredient of the recipe {string} in stock")
    public void the_store_have_of_each_ingredient_of_the_recipe_in_stock(Integer quantity, String recipeName) {
        assertEquals(quantity, stockModifier.getAmountInStock(store, dough));
        assertEquals(quantity, stockModifier.getAmountInStock(store, flavour));
        for (Ingredient ingredient : ingredients) {
            assertEquals(quantity, stockModifier.getAmountInStock(store, ingredient));
        }
    }

    @Then("the store have {int} of each ingredient of the recipe {string} locked in stock")
    public void the_store_have_of_each_ingredient_of_the_recipe_locked_in_stock(Integer quantity, String recipeName) {
        assertEquals(quantity, store.getStock().get(dough.name()).getLockedQuantity());
        assertEquals(quantity, store.getStock().get(flavour.name()).getLockedQuantity());
        for (Ingredient ingredient : ingredients) {
            assertEquals(quantity, store.getStock().get(ingredient.name()).getLockedQuantity());
        }
    }

    @Then("the order line isn't add in the order")
    public void the_order_line_isn_t_add_in_the_order() {
        assertEquals(0, order.getOrderLines().size());
    }
}
