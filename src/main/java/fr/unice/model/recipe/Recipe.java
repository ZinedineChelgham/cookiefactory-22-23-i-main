package fr.unice.model.recipe;

import fr.unice.enums.*;
import fr.unice.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all the information needed to cook a cookie
 */
public class Recipe {
    String name;
    List<Ingredient> toppings;
    Ingredient flavor;
    Ingredient dough;
    MixTypes mix;
    CookingTypes cooking;
    int preparationTime;
    CookieSize size;

    List<Ingredient> removeIngredient = new ArrayList<>();
    List<Ingredient> addIngredient = new ArrayList<>();


    public Recipe(RecipeBuilder recipeBuilder) {
        this.name = recipeBuilder.name;
        this.toppings = recipeBuilder.toppings;
        this.flavor = recipeBuilder.flavor;
        this.dough = recipeBuilder.dough;
        this.mix = recipeBuilder.mix;
        this.cooking = recipeBuilder.cooking;
        this.preparationTime = recipeBuilder.preparationTime;
        this.size = recipeBuilder.size;
    }

    public void setToppings(List<Ingredient> toppings) {
        if (toppings.size() < 1 ||
                toppings.size() > 3 ||
                toppings.stream().allMatch(ingredient -> ingredient.type() != IngredientType.TOPPING)            )
            throw new IllegalArgumentException();
        this.toppings = toppings;
    }

    public List<Ingredient> getToppings() {
        return toppings;
    }

    public Ingredient getFlavor() {
        return flavor;
    }

    public Ingredient getDough() {
        return dough;
    }

    public String getName() {
        return name;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public MixTypes getMix() {
        return mix;
    }

    public CookingTypes getCooking() {
        return cooking;
    }

    public CookieSize getSize(){return size;}

    public List<Ingredient> getRemoveIngredient() {
        return removeIngredient;
    }

    public List<Ingredient> getAddIngredient() {
        return addIngredient;
    }
}
