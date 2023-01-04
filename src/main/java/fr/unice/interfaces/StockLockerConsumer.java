package fr.unice.interfaces;

import fr.unice.model.Ingredient;
import fr.unice.model.OrderLine;
import fr.unice.model.Stock;

public interface StockLockerConsumer {
    boolean lock(Stock stock, int quantity);
    boolean unlock(Stock stock, int quantity);
    boolean consume(Stock stock, int quantity);
    boolean ingredientCanBeLocked(Stock stock, int quantity);
}
