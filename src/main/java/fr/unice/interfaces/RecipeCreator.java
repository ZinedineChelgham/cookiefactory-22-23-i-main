package fr.unice.interfaces;

import fr.unice.model.recipe.RecipeBuilder;

public interface RecipeCreator {
    RecipeBuilder createRecipe(String name);
}
