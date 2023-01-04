package fr.unice.interfaces;

import fr.unice.model.recipe.Recipe;

public interface RecipeCalculator {
    double getPrice(Recipe recipe);
}
