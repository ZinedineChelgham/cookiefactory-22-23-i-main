package fr.unice.spring;

import fr.unice.enums.IngredientTag;
import fr.unice.enums.IngredientType;
import fr.unice.interfaces.StockModifier;
import fr.unice.model.Ingredient;
import fr.unice.model.Store;
import fr.unice.repositories.IngredientRepository;
import fr.unice.services.CatalogService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockDefinitions {
    Store store;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private StockModifier stockModifier;

    @Before
    public void setup() { ingredientRepository.deleteAll(); }

    @Given("an empty store named {string}")
    public void anEmptyStore(String storeName) {
        store = new Store("home street 12", storeName);
    }

    @Given("an ingredient named {string} exists")
    public void anIngredientNamedExists(String ingredientName) {
        Ingredient ingredient = new Ingredient(ingredientName, 0.2, IngredientType.BASIS);
        ingredientRepository.save(ingredient, IngredientTag.valueOf(ingredientName.toUpperCase()));
    }

    @When("the manager adds {int} ingredient of the name {string} to store")
    public void theManagerAddsIngredientOfTheNameToStore(int amount_add, String ingredientName) {
        stockModifier.addIngredientsToStore(store, ingredientName, amount_add);
    }

    @Then("the store has {int} ingredient of the name {string} in stock")
    public void theStoreHasIngredientOfTheNameInStock(int amount_final, String ingredientName) {
        assertEquals(amount_final, stockModifier.getStockOfStore(store, ingredientName));
    }

}
