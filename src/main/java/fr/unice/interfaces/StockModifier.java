package fr.unice.interfaces;

import fr.unice.model.Ingredient;
import fr.unice.model.Stock;
import fr.unice.model.Store;

public interface StockModifier {

    /**
     * Adds an amount to the store's stock of the given ingredient
     *
     * @param store store to restock
     * @param ingredientName name of the restocked ingredient
     * @param amount amount of units added to the stock
     *
     * @throws IllegalStateException is thrown if the ingredient name does not exist.
     */
    void addIngredientsToStore(Store store, String ingredientName, int amount) throws IllegalArgumentException;

    /**
     * Gets the amount of units in stock at a given store.
     *
     * @param store store to check
     * @param ingredientName name of the ingredient to check for
     * @return amount of units is stock
     *
     * @throws IllegalStateException is thrown if the ingredient name does not exist.
     */
    int getStockOfStore(Store store, String ingredientName) throws IllegalArgumentException;

    void fillStore(Store store, Ingredient ingredient, int amount);
    int getAmountInStock(Store store, Ingredient ingredient);
}
