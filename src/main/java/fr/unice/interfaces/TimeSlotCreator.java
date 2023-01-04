package fr.unice.interfaces;

import fr.unice.model.Order;
import fr.unice.model.cooks.TimeSlot;

public interface TimeSlotCreator {
    TimeSlot createTimeSlotBasedOnOrder(Order order);
}
