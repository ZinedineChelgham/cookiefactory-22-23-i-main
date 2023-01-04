package fr.unice.interfaces;

import fr.unice.model.Order;
import fr.unice.model.cooks.Cook;
import fr.unice.model.cooks.TimeSlot;

public interface TimeSlotManager {
    boolean tryAddTimeSlot(Cook cook, Order order);
    boolean deleteTimeSlot(Cook cook, Order order);
    boolean stackedTimeSlots(TimeSlot t1, TimeSlot t2);
    boolean containsOrderInScheduler(Cook cook, Order order);
    TimeSlot getTimeSlot(Cook cook, Order order);
}
