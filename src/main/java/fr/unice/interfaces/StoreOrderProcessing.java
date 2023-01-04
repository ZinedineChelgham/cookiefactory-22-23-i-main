package fr.unice.interfaces;

import fr.unice.model.Order;
import fr.unice.model.Store;
import fr.unice.model.cooks.Cook;

import java.util.Optional;

public interface StoreOrderProcessing {
    boolean tryAssociateOrderWithScheduler(Store store, Order order);
    Optional<Cook> getCookByOrder(Order order);
    boolean deleteTimeSlot(Cook cook, Order order);
}
