package fr.unice.model;


import java.time.LocalDateTime;

public class Bill {


    private final LocalDateTime date;
    private final Order order;


    public Bill(LocalDateTime date, Order order) {
        this.date = date;
        this.order = order;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "date=" + date +
                ", order=" + order +
                '}';
    }
}
