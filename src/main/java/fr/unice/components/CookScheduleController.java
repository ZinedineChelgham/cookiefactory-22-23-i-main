package fr.unice.components;

import fr.unice.interfaces.OrderUtilities;
import fr.unice.interfaces.TimeSlotCreator;
import fr.unice.interfaces.TimeSlotManager;
import fr.unice.model.Order;
import fr.unice.model.OrderLine;
import fr.unice.model.cooks.Cook;
import fr.unice.model.cooks.TimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CookScheduleController implements TimeSlotManager, TimeSlotCreator {
    public static final int FRAGMENT_TIME_SLOT_DURATION = 15;

    @Autowired
    public CookScheduleController() {
    }

    /**
     * Add a time slot in the list matching the given order if the order have a time to pickup respecting the working
     * hours and don't interfere with other time slots
     *
     * @param order Order
     * @return boolean if the time slot has been added
     */
    @Override
    public boolean tryAddTimeSlot(Cook cook, Order order) {
        TimeSlot orderTimeSlot = createTimeSlotBasedOnOrder(order);
        if (cook.getStartWorkingHour().isAfter(orderTimeSlot.getStartDateTime().toLocalTime())
                || cook.getEndWorkingHour().isBefore(orderTimeSlot.getEndDateTime().toLocalTime()))
            return false;

        for (TimeSlot timeSlot : cook.getTimeSlots()) {
            if (stackedTimeSlots(timeSlot, orderTimeSlot))
                return false;
        }

        cook.getTimeSlots().add(orderTimeSlot);
        return true;
    }

    /**
     * @param cook
     * @param order
     * @return true if the order is deleted or false if the order hasn't been found
     */
    @Override
    public boolean deleteTimeSlot(Cook cook, Order order) {
        TimeSlot timeSlot = getTimeSlot(cook, order);
        if (timeSlot != null) {
            cook.getTimeSlots().remove(timeSlot);
            return true;
        }
        return false;
    }

    /**
     * @param t1 TimeSlot
     * @param t2 TimeSlot
     * @return Return true if the given time slots have at least a part stack together
     */
    @Override
    public boolean stackedTimeSlots(TimeSlot t1, TimeSlot t2) {
        return (t1.getStartDateTime().isAfter(t2.getStartDateTime()) && t1.getStartDateTime().isBefore(t2.getEndDateTime()))
                || (t1.getEndDateTime().isAfter(t2.getStartDateTime()) && t1.getEndDateTime().isBefore(t2.getEndDateTime()))
                || (t1.getEndDateTime().isEqual(t2.getEndDateTime()));

    }

    /**
     * @param cook
     * @param order
     * @return true if the given cook has the order in his TimeSlot list
     */
    @Override
    public boolean containsOrderInScheduler(Cook cook, Order order) {
        for (TimeSlot timeSlot : cook.getTimeSlots()) {
            if (timeSlot.getOrder().getId() == order.getId())
                return true;
        }
        return false;
    }

    /**
     * @param cook
     * @param order
     * @return the corresponding TimeSlot if the given cook has the order in his TimeSlot list
     */
    @Override
    public TimeSlot getTimeSlot(Cook cook, Order order) {
        for (TimeSlot timeSlot : cook.getTimeSlots()) {
            if (timeSlot.getOrder().getId() == order.getId())
                return timeSlot;
        }
        return null;
    }

    /**
     * @param order
     * @return TimeSlot with the duration of the given order considering the length of a fragment
     */
    @Override
    public TimeSlot createTimeSlotBasedOnOrder(Order order) {
        LocalDateTime endDateTime = order.getPickupTime();

        int orderPreparationTime = preparationTime(order);
        int orderPreparationTimeMatchingFragmentDuration = orderPreparationTime;
        if (orderPreparationTime % FRAGMENT_TIME_SLOT_DURATION != 0)
            orderPreparationTimeMatchingFragmentDuration += FRAGMENT_TIME_SLOT_DURATION - (orderPreparationTime % FRAGMENT_TIME_SLOT_DURATION);

        LocalDateTime startDateTime = order.getPickupTime().minusMinutes(orderPreparationTimeMatchingFragmentDuration);

        return new TimeSlot(startDateTime, endDateTime, order);
    }

    /**
     * Calculate the preparation duration in minute to prepare all the cookies in the order
     * @return Preparation duration
     */
    private int preparationTime(Order order) {
        int duration = 0;
        for (OrderLine orderLine : order.getOrderLines().values()) {
            duration += orderLine.getQuantity() * orderLine.getRecipe().getPreparationTime();
        }
        return duration;
    }
}
