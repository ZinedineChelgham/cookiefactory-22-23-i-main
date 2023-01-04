package fr.unice.interfaces;

import fr.unice.model.Ingredient;
import fr.unice.model.recipe.Recipe;

public interface RecipeModifier {
    void removeIngredient(Recipe r, Ingredient ingredient);
    void addIngredient(Recipe r, Ingredient ingredient);
}
