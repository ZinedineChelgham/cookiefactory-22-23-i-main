package fr.unice.model;

import fr.unice.model.recipe.Recipe;

/**
 * Contains all information and functionality of a line in an order
 */
public class OrderLine {
    private final Recipe recipe;
    private int quantity;

    /**
     * Initializes a new instance of the OrderLine class.
     *
     * @param recipe the ingredient in the line
     * @param quantity the quantity of the ingredient
     */
    public OrderLine(Recipe recipe, int quantity) {
        this.recipe = recipe;
        this.quantity = quantity;
    }


    public Recipe getRecipe() {
        return recipe;
    }

    public int getQuantity() {
        return quantity;
    }
}
