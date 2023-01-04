package fr.unice.interfaces;

import fr.unice.model.Order;
import fr.unice.model.Store;
import fr.unice.model.cooks.Cook;

import java.util.Optional;

public interface CookOrderProcessing {
    void startPreparingOrder(Cook cook, Order order);
    boolean tryAddingOrderInSchedule(Cook cook, Order order);
    void finishPreparingOrder(Cook cook);
    Optional<Cook> getCookByOrder(Order order);
}
