package fr.unice.components;

import fr.unice.enums.OrderStatus;
import fr.unice.interfaces.*;
import fr.unice.model.Order;
import fr.unice.model.cooks.Cook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Optional;

@Component
public class CooksController implements CookOrderProcessing, CookModifier {
    TimeSlotManager timeSlotManager;
    StoreIngredientsLocker storeIngredientsLocker;

    @Autowired
    public CooksController(TimeSlotManager timeSlotManager, StoreIngredientsLocker storeIngredientsLocker) {
        this.timeSlotManager = timeSlotManager;
        this.storeIngredientsLocker = storeIngredientsLocker;
    }

    /**
     * Start the preparation of an order
     */
    @Override
    public void startPreparingOrder(Cook cook, Order order) {
        cook.setOrderToPrepare(order);
        order.setStatus(OrderStatus.PREPARATION);
    }

    /**
     * Add a time slot in the list matching the given order if the order have a time to pickup respecting the working
     * hours and don't interfere with other time slots
     *
     * @param order Order
     * @return boolean if the order has been added in the schedule
     */
    @Override
    public boolean tryAddingOrderInSchedule(Cook cook, Order order) {
        return timeSlotManager.tryAddTimeSlot(cook, order);
    }

    /**
     * After preparing the order, update the status of the order to ready and remove the order from the cook
     */
    @Override
    public void finishPreparingOrder(Cook cook) {
        Order order = cook.getOrderToPrepare();
        if (order == null)
            return;

        order.setStatus(OrderStatus.READY);
        storeIngredientsLocker.consumeIngredientsFromStock(order.getStore(), order);
        timeSlotManager.deleteTimeSlot(cook, order);
        cook.setOrderToPrepare(null);
    }

    @Override
    public Optional<Cook> getCookByOrder(Order order) {
        for (Cook cook : order.getStore().getCooks()){
            if (timeSlotManager.getTimeSlot(cook, order) != null)
                return Optional.of(cook);
        }
        return Optional.empty();
    }

    /**
     * Define the schedule hours of a cook
     *
     * @param startWorkHour LocalTime
     * @param endWorkHour LocalTime
     */
    @Override
    public void defineSchedule(Cook cook, LocalTime startWorkHour, LocalTime endWorkHour) {
        cook.setStartWorkingHour(startWorkHour);
        cook.setEndWorkingHour(endWorkHour);
    }
}
