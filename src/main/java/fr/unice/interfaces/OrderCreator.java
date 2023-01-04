package fr.unice.interfaces;

import fr.unice.model.Order;
import fr.unice.model.Store;

public interface OrderCreator {
    Order createOrder(Store store, int id);
}
