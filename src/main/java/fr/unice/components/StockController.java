package fr.unice.components;

import fr.unice.enums.IngredientTag;
import fr.unice.interfaces.IngredientsUpdater;
import fr.unice.interfaces.StockLockerConsumer;
import fr.unice.interfaces.StockModifier;
import fr.unice.interfaces.StoreIngredientsLocker;
import fr.unice.model.*;
import fr.unice.repositories.IngredientRepository;
import fr.unice.services.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.ServiceUnavailableException;
import java.util.List;

@Component
public class StockController implements StockModifier, IngredientsUpdater, StockLockerConsumer, StoreIngredientsLocker {

    private IngredientRepository ingredientRepository;
    private CatalogService catalogService;

    @Autowired
    public StockController(IngredientRepository ingredientRepository, CatalogService catalogService) {
        this.ingredientRepository = ingredientRepository;
        this.catalogService = catalogService;
    }

    @Override
    public void updateIngredientListViaCatalog() throws ServiceUnavailableException, IllegalStateException {
        if (catalogService == null)
            throw new IllegalStateException("A CatalogService must be provided before loading.");

        List<Ingredient> ingredientLoaded = catalogService.loadIngredients();
        for (Ingredient il: ingredientLoaded) {
            IngredientTag tag = IngredientTag.valueOf(il.name().toUpperCase().replace(' ', '_'));
            boolean exists = ingredientRepository.existsById(tag);
            if (exists) {
                Ingredient ingredient = ingredientRepository.findById(tag).get();
                ingredient.setPrice(il.price());
                ingredient.setType(il.type());
                ingredient.setAvailability(true);
            } else {
                ingredientRepository.save(il, tag);
            }
        }

        for (Ingredient ingredient: ingredientRepository.findAll()) {
            boolean noLongerInStock = !ingredientLoaded.contains(ingredient);
            if (noLongerInStock)
                ingredient.setAvailability(false);
        }
    }

    @Override
    public void addIngredientsToStore(Store store, String ingredientName, int amount) throws IllegalArgumentException {
        Ingredient ingredient = ingredientRepository.findById(IngredientTag.valueOf(ingredientName.toUpperCase()))
                .orElseThrow(() -> new IllegalArgumentException("No ingredient tag with the name " + ingredientName + " exists."));

        fillStore(store, ingredient, amount);
    }

    @Override
    public int getStockOfStore(Store store, String ingredientName) throws IllegalArgumentException {
        Ingredient ingredient = ingredientRepository.findById(IngredientTag.valueOf(ingredientName.toUpperCase()))
                .orElseThrow(() -> new IllegalArgumentException("No ingredient tag with the name " + ingredientName + " exists."));

        return getAmountInStock(store, ingredient);
    }

    /**
     * Adds new units of the given ingredient to the stock
     *
     * @param ingredient of which to add
     * @param amount the amount of units
     */
    @Override
    public void fillStore(Store store, Ingredient ingredient, int amount) {
        Stock itemStock = store.getStock().get(ingredient.name());

        if (itemStock == null)
            store.getStock().put(ingredient.name(), new Stock(ingredient, amount));
        else
            itemStock.addItem(amount);
    }

    /**
     * Gets the amount in stock of the given ingredient
     *
     * @param ingredient ingredient to check the amount for.
     * @return number of units in stock
     */
    @Override
    public int getAmountInStock(Store store, Ingredient ingredient) {
        Stock itemStock = store.getStock().get(ingredient.name());

        if (itemStock == null)
            return 0;

        return itemStock.getStockQuantity();
    }

    /**
     * Try to lock the given quantity of ingredients
     *
     * @param quantity the quantity to lock
     * @return true if locked, false otherwise
     */
    @Override
    public boolean lock(Stock stock, int quantity) {
        if (stock.getStockQuantity() < quantity)
            return false;

        stock.setStockQuantity(stock.getStockQuantity() - quantity);
        stock.setLockQuantity(stock.getLockedQuantity() + quantity);
        return true;
    }

    /**
     * Try to unlock the given quantity of ingredients
     *
     * @param quantity the quantity to unlock
     * @return true if unlocked, false otherwise
     */
    @Override
    public boolean unlock(Stock stock, int quantity) {
        if (stock.getLockedQuantity() < quantity)
            return false;

        stock.setStockQuantity(stock.getStockQuantity() + quantity);
        stock.setLockQuantity(stock.getLockedQuantity() - quantity);
        return true;
    }

    /**
     * Try to remove the given quantity of ingredients from the locked amount
     *
     * @param quantity the quantity to remove
     * @return a value indicating whether the ingredients where consumed
     */
    @Override
    public boolean consume(Stock stock, int quantity) {
        if (stock.getLockedQuantity() < quantity)
            return false;


        stock.setLockQuantity(stock.getLockedQuantity() - quantity);
        return true;
    }

    /**
     * True if enough stock to lock the given quantity
     * False if not enough stock
     *
     * @param quantity to lock
     * @return boolean
     */
    @Override
    public boolean ingredientCanBeLocked(Stock stock, int quantity) {
        return stock.getStockQuantity() >= quantity;
    }

    /**
     * Try to lock the given amount of an ingredient in the stock
     *
     * @param ingredientName the name of the ingredient to lock
     * @param amount the amount to lock
     * @return true if locked, false otherwise
     */
    @Override
    public boolean lockIngredient(Store store, String ingredientName, int amount) {
        if (!store.getStock().containsKey(ingredientName))
            return false;

        return lock(store.getStock().get(ingredientName), amount);
    }

    /**
     * Try to unlock the given amount of an ingredient in the stock
     *
     * @param ingredientName the name of the ingredient to unlock
     * @param amount the amount to unlock
     * @return true if unlocked, false otherwise
     */
    @Override
    public boolean unlockIngredient(Store store, String ingredientName, int amount) {
        if (!store.getStock().containsKey(ingredientName))
            return false;

        return unlock(store.getStock().get(ingredientName), amount);
    }

    /**
     * Try to remove the given amount of an ingredient in the stock's locked amount
     *
     * @param ingredientName the name of the ingredient to consume
     * @param amount the amount to remove
     * @return true if removed, false otherwise
     */
    @Override
    public boolean consumeIngredients(Store store, String ingredientName, int amount) {
        if (!store.getStock().containsKey(ingredientName))
            return false;

        return consume(store.getStock().get(ingredientName), amount);
    }

    /**
     * Try to lock the given amount of an ingredient in the stock
     *
     * @param ingredientName the name of the ingredient to lock
     * @param amount the amount to lock
     * @return true if locked, false otherwise
     */
    @Override
    public boolean ingredientCanBeLocked(Store store, String ingredientName, int amount) {
        if (!store.getStock().containsKey(ingredientName))
            return false;

        return ingredientCanBeLocked(store.getStock().get(ingredientName), amount);
    }

    /**
     * True if the ingredients used in the order line can be locked
     * False if not
     *
     * @param orderLine order line to lock
     * @return boolean
     */
    @Override
    public boolean orderLineIngredientsCanBeLocked(Store store, OrderLine orderLine) {
        for (Ingredient ingredient : orderLine.getRecipe().getToppings()) {
            if (!ingredientCanBeLocked(store, ingredient.name(), orderLine.getQuantity()))
                return false;
        }
        return ingredientCanBeLocked(store, orderLine.getRecipe().getDough().name(), orderLine.getQuantity()) &&
                ingredientCanBeLocked(store, orderLine.getRecipe().getFlavor().name(), orderLine.getQuantity());
    }

    /**
     * Try to lock the given amount of an ingredient in the stock
     *
     * @param orderLine the order line to lock
     * @return true if locked, false otherwise
     */
    @Override
    public void lockOrderLineIngredients(Store store, OrderLine orderLine) {
        for (Ingredient ingredient : orderLine.getRecipe().getToppings()) {
            lockIngredient(store, ingredient.name(), orderLine.getQuantity());
        }
        lockIngredient(store, orderLine.getRecipe().getDough().name(), orderLine.getQuantity());
        lockIngredient(store, orderLine.getRecipe().getFlavor().name(), orderLine.getQuantity());
    }

    /**
     * Try to lock the given amount of an ingredient in the stock
     *
     * @param orderLine the order line to lock
     * @return true if locked, false otherwise
     */
    @Override
    public void unlockOrderLineIngredients(Store store, OrderLine orderLine) {
        for (Ingredient ingredient : orderLine.getRecipe().getToppings()) {
            unlockIngredient(store, ingredient.name(), orderLine.getQuantity());
        }
        unlockIngredient(store, orderLine.getRecipe().getDough().name(), orderLine.getQuantity());
        unlockIngredient(store, orderLine.getRecipe().getFlavor().name(), orderLine.getQuantity());
    }

    /**
     * Try to lock the given amount of an ingredient in the stock
     *
     * @param order
     * @return true if locked, false otherwise
     */
    @Override
    public void unlockOrderIngredients(Store store, Order order) {
        for (OrderLine orderLine : order.getOrderLines().values()) {
            unlockOrderLineIngredients(store, orderLine);
        }
    }

    /**
     * Removes all the ingredients form the locked stock in the order line
     *
     * @param orderLine the order line, what's ingredients are to consume
     */
    @Override
    public void consumeOrderLineIngredients(Store store, OrderLine orderLine) {
        for (Ingredient ingredient : orderLine.getRecipe().getToppings()) {
            consumeIngredients(store, ingredient.name(), orderLine.getQuantity());
        }
        consumeIngredients(store, orderLine.getRecipe().getDough().name(), orderLine.getQuantity());
        consumeIngredients(store, orderLine.getRecipe().getFlavor().name(), orderLine.getQuantity());
    }

    @Override
    public void consumeIngredientsFromStock(Store store, Order order) {
        for (OrderLine orderLine: order.getOrderLines().values()) {
            consumeOrderLineIngredients(store, orderLine);
        }
    }
}
