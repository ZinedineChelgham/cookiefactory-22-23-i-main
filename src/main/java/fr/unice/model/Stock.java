package fr.unice.model;

/**
 * Contains all the information about the stock of an ingredient
 */
public class Stock {
    private final Ingredient ingredient;
    private int stockQuantity;
    private int lockQuantity;


    /**
     * Initializes a new instance of the Stock class.
     *
     * @param ingredient the ingredient in stock
     * @param quantity the quantity of the ingredient
     */
    public Stock(Ingredient ingredient, int quantity) {
        this.ingredient = ingredient;
        this.stockQuantity = quantity;
        this.lockQuantity = 0;
    }

    public void addItem(int amount_add) {
        if(amount_add>=0 || (-1)*amount_add <=stockQuantity)stockQuantity+=amount_add;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setLockQuantity(int lockQuantity) {
        this.lockQuantity = lockQuantity;
    }

    public int getLockedQuantity() {
        return lockQuantity;
    }

}
