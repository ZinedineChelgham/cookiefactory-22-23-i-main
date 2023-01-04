package fr.unice.spring;

import fr.unice.enums.CookingTypes;
import fr.unice.enums.IngredientTag;
import fr.unice.enums.MixTypes;
import fr.unice.repositories.IngredientRepository;
import fr.unice.repositories.RecipeRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import fr.unice.model.*;
import fr.unice.model.recipe.Recipe;
import fr.unice.model.recipe.RecipeBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ManageRecipesDefinition {


    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    RecipeRepository recipeRepository;

    int size;
    String recipeName;

    @Given("a list of recipes")
    public void aListOfRecipes() {
        recipeRepository.deleteAll();
        recipeRepository.initialise();
        ingredientRepository.initialise();

        size = recipeRepository.count();
    }

    @When("someone add a new {string}")
    public void someoneAddARecipe(String name) {
        recipeName = name;
        assertFalse(recipeRepository.existsById(name));
        recipeRepository.save(getNewRecipe(name), name);
        size++;
    }

    @Then("the recipe list size is increment and the recipe is in the list")
    public void theRecipeIsInTheList() {
        assertEquals(size, recipeRepository.count());
        assertTrue(recipeRepository.existsById(recipeName));
    }

    @When("someone remove a existing {string}")
    public void someoneRemoveAExistingRecipe(String name) {
        recipeRepository.save(getNewRecipe(name), name);
        size++;

        if(recipeRepository.existsById(name)) {
            recipeRepository.deleteById(name);
            size--;
        }
        recipeName = name;
    }

    @Then("the recipe list size is decrement and the recipe is not in the list")
    public void theRecipeListSizeIsDecrementAndTheRecipeIsInTheList() {
        assertEquals(size, recipeRepository.count());
        assertFalse(recipeRepository.existsById(recipeName));
    }

    Recipe getNewRecipe(String name){
        ArrayList<Ingredient> i = new ArrayList<>();
        i.add(ingredientRepository.findById(IngredientTag.WHITE_CHOCOLATE).get());
        i.add(ingredientRepository.findById(IngredientTag.BLACK_CHOCOLATE).get());
        i.add(ingredientRepository.findById(IngredientTag.MILK_CHOCOLATE).get());

        Ingredient flavor = ingredientRepository.findById(IngredientTag.BLACK_CHOCOLATE).get();
        Ingredient dough = ingredientRepository.findById(IngredientTag.CHOCOLATE_DOUGH).get();

        RecipeBuilder r = new RecipeBuilder(name)
                .withCookingType(CookingTypes.CHEWY)
                .withDough(dough)
                .withFlavor(flavor)
                .withMixType(MixTypes.TOPPED)
                .withToppings(i);

        return r.getResult();
    }
}
