package fr.unice.interfaces;

import fr.unice.model.Ingredient;
import fr.unice.model.Order;
import fr.unice.model.OrderLine;
import fr.unice.model.Store;

public interface StoreIngredientsLocker {
    boolean lockIngredient(Store store, String ingredientName, int amount);
    boolean unlockIngredient(Store store, String ingredientName, int amount);
    boolean consumeIngredients(Store store, String ingredientName, int amount);
    boolean ingredientCanBeLocked(Store store, String ingredientName, int amount);
    boolean orderLineIngredientsCanBeLocked(Store store, OrderLine orderLine);
    void lockOrderLineIngredients(Store store, OrderLine orderLine);
    void unlockOrderLineIngredients(Store store, OrderLine orderLine);
    void unlockOrderIngredients(Store store, Order order);
    void consumeOrderLineIngredients(Store store, OrderLine orderLine);
    void consumeIngredientsFromStock(Store store, Order order);
}
