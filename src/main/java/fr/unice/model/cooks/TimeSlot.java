package fr.unice.model.cooks;

import fr.unice.model.Order;

import java.time.LocalDateTime;

public class TimeSlot {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Order order;

    public TimeSlot(LocalDateTime startDateTime, LocalDateTime endDateTime, Order order) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.order = order;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public Order getOrder() {
        return order;
    }
}
