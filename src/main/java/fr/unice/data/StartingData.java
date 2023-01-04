package fr.unice.data;

import fr.unice.enums.*;
import fr.unice.model.cooks.Cook;
import fr.unice.model.Ingredient;
import fr.unice.model.recipe.Recipe;
import fr.unice.model.Store;
import fr.unice.model.recipe.RecipeBuilder;

import java.util.ArrayList;
import java.util.HashMap;

public class StartingData {

    private final HashMap<String, Store> stores;
    private final HashMap<String, Recipe> recipes;
    private final HashMap<IngredientTag, Ingredient> allIngredients;
    private final HashMap<String, Cook> cooks;
    private final int nextOrderId;

    public StartingData(){
        stores = new HashMap<>();
        recipes = new HashMap<>();
        allIngredients = new HashMap<>();
        cooks = new HashMap<>();
        nextOrderId = 0;
        initializeStores();
        initializeRecipes();
    }

    /**
     * Add all stores in the list using Store objects
     */
    void initializeStores(){
        addStore(new Store("12 downside street, Chicago", "CookieCooked"));
        addStore(new Store("7 kim street, Washington", "CC"));
        addStore(new Store("89 paypal blvd, New York", "CC1"));
        addStore(new Store("12 downside street, Chicago", "CC2"));
        addStore(new Store("12 downside street, Chicago", "CC3"));
    }

    void initializeCooks() {
        addCook(new Cook("mike"));
        addCook(new Cook("John"));
    }

    /**
     * create and
     * add all recipes
     */
    void initializeRecipes(){
        initializeIngredients();

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(allIngredients.get(IngredientTag.WHITE_CHOCOLATE));
        ingredients.add(allIngredients.get(IngredientTag.BLACK_CHOCOLATE));
        ingredients.add(allIngredients.get(IngredientTag.MILK_CHOCOLATE));


        RecipeBuilder r = new RecipeBuilder("3 Chocolates")
                .withCookingType(CookingTypes.CRUNCHY)
                .withDough(allIngredients.get(IngredientTag.CHOCOLATE_DOUGH))
                .withFlavor(allIngredients.get(IngredientTag.BLACK_CHOCOLATE))
                .withMixType(MixTypes.MIXED)
                .withToppings(ingredients);
        Recipe chocolateCookie = r.getResult();

        recipes.put(chocolateCookie.getName(), chocolateCookie);


    }

    /**
     * create and add all ingredients with their characteristics
     */
    void initializeIngredients(){
        addInIngredientList(IngredientTag.FLOOR, 0.2, IngredientType.BASIS);
        addInIngredientList(IngredientTag.EGG, 0.1, IngredientType.BASIS);
        addInIngredientList(IngredientTag.BLACK_CHOCOLATE, 0.3, IngredientType.TOPPING);
        addInIngredientList(IngredientTag.WHITE_CHOCOLATE, 0.3, IngredientType.TOPPING);
        addInIngredientList(IngredientTag.MILK_CHOCOLATE, 0.3, IngredientType.TOPPING);
        addInIngredientList(IngredientTag.CHOCOLATE_DOUGH, 0.5, IngredientType.DOUGH);
    }

    private void addInIngredientList(IngredientTag ingredientTag, double price, IngredientType ingredientType) {
        allIngredients.put(ingredientTag, new Ingredient(ingredientTag.getName(),price,ingredientType));
    }
    public void addStore(Store store) {
        stores.put(store.getName(), store);
    }

    public void addCook(Cook cook) {
        cooks.put(cook.getName(), cook);
    }

    public Store getStoreByName(String storeName) {
        return stores.get(storeName);
    }

    public void addRecipe(Recipe recipe) {
        recipes.put(recipe.getName(), recipe);
    }

    public HashMap<String, Store> getStores() {
        return stores;
    }

    public HashMap<String, Recipe> getRecipes() {
        return recipes;
    }

    public HashMap<String, Cook> getCooks() {
        return cooks;
    }

    public int getNextOrderId() {
        return nextOrderId;
    }

    public HashMap<IngredientTag, Ingredient> getAllIngredients() {
        return allIngredients;
    }
}
