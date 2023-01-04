package fr.unice.components;

import fr.unice.enums.CookieSize;
import fr.unice.interfaces.RecipeCalculator;
import fr.unice.interfaces.RecipeCreator;
import fr.unice.interfaces.RecipeModifier;
import fr.unice.model.Ingredient;
import fr.unice.model.recipe.Recipe;
import fr.unice.model.recipe.RecipeBuilder;
import fr.unice.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecipeController implements RecipeCreator, RecipeModifier, RecipeCalculator {

    RecipeRepository recipeRepository;

    @Autowired
    RecipeController(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    @Override
    public RecipeBuilder createRecipe(String name) {
        return new RecipeBuilder(name);
    }

    @Override
    public void removeIngredient(Recipe r, Ingredient ingredient) {
        if(ingredient==null) return;
        if(!r.getRemoveIngredient().remove(ingredient)) r.getAddIngredient().add(ingredient);
    }

    @Override
    public void addIngredient(Recipe r, Ingredient ingredient) {
        if(ingredient==null) return;
        if(!r.getAddIngredient().remove(ingredient)) r.getRemoveIngredient().add(ingredient);
    }

    /**
     * get the price of the recipe based on the ingredients
     */
    @Override
    public double getPrice(Recipe recipe) {
        double price = 0;
        for(Ingredient i: recipe.getToppings()) price += i.price();

        if(recipe.getDough()!=null) price += recipe.getDough().price();
        if(recipe.getFlavor()!=null) price += recipe.getFlavor().price();

        for (Ingredient i: recipe.getAddIngredient()) price += i.price();

        price*= recipe.getSize().getMultiplier();

        if(recipe.getSize()!= CookieSize.Normal) price*=1.25;
        return price;
    }
}
