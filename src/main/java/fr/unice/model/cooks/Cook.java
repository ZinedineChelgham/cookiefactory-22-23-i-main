package fr.unice.model.cooks;

import fr.unice.model.Order;
import fr.unice.enums.OrderStatus;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains all the information of a cook.
 */
public class Cook{
    private String name;
    private Order orderToPrepare;
    private LocalTime startWorkingHour;
    private LocalTime endWorkingHour;
    private final List<TimeSlot> timeSlots;

    public Cook(String name) {
        this.name = name;
        timeSlots = new ArrayList<>();

        // default working hours
        startWorkingHour = LocalTime.of(8, 0);
        endWorkingHour = LocalTime.of(19, 0);
    }

    public String getName() {
        return name;
    }

    public void setOrderToPrepare(Order orderToPrepare) {
        this.orderToPrepare = orderToPrepare;
    }

    public Order getOrderToPrepare() {
        return orderToPrepare;
    }

    public LocalTime getStartWorkingHour() {
        return startWorkingHour;
    }

    public void setStartWorkingHour(LocalTime startWorkingHour) {
        this.startWorkingHour = startWorkingHour;
    }

    public LocalTime getEndWorkingHour() {
        return endWorkingHour;
    }

    public void setEndWorkingHour(LocalTime endWorkingHour) {
        this.endWorkingHour = endWorkingHour;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }
}
