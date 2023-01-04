package fr.unice.model;

import fr.unice.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.*;

/**
 * Contains all the information and functionality of an order.
 */
public class Order {
    private final Map<OrderStatus, Set<OrderStatus>> possibleNextStatuses = Map.of(
            OrderStatus.BASKET, Set.of(
                    OrderStatus.PAYED,
                    OrderStatus.CANCELLED
            ),
            OrderStatus.PAYED, Set.of(
                    OrderStatus.PREPARATION,
                    OrderStatus.CANCELLED
            ),
            OrderStatus.CANCELLED, Set.of(),
            OrderStatus.PREPARATION, Set.of(
                    OrderStatus.READY
            ),
            OrderStatus.READY, Set.of(
                    OrderStatus.DELIVERED,
                    OrderStatus.FORGOTTEN
            ),
            OrderStatus.DELIVERED, Set.of(),
            OrderStatus.FORGOTTEN, Set.of(
                    OrderStatus.SURPRISE_BOXED
            ));

    private final int id;
    private LocalDateTime pickupTime;
    private OrderStatus status;
    private User buyer;
    private Store store;
    private boolean sendMessage;

    private HashMap<String, OrderLine> orderLines;

    private double price = 0;

    /**
     * Initializes a new instance of the Order class.
     *
     * @param id the id by which an order can be identified
     */
    public Order(int id) {
        status = OrderStatus.BASKET;

        this.id = id;
        this.orderLines = new HashMap<>();
    }

    public Map<OrderStatus, Set<OrderStatus>> getPossibleNextStatuses() {
        return possibleNextStatuses;
    }

    public Order(int id, LocalDateTime pickupTime) {
        this(id);

        this.pickupTime = pickupTime;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public int getId() {
        return id;
    }

    public OrderLine getOrderLine(String recipeName) {
        return orderLines.get(recipeName);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public HashMap<String, OrderLine> getOrderLines() {
        return orderLines;
    }

    public int getNumberOfCookiesOrdered() {
        return orderLines.values().stream().mapToInt(OrderLine::getQuantity).sum();
    }



    public LocalDateTime getPickupTime() {
        return pickupTime;
    }



    public void setPickupTime(LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Order{" +
                ", id=" + id +
                ", pickupTime=" + pickupTime +
                ", status=" + status +
                ", buyer=" + buyer +
                ", store=" + store +
                ", mail='" + buyer.getMail() + '\'' +
                ", orderLines=" + orderLines +
                '}';
    }

    public boolean isSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(boolean sendMessage) {
        this.sendMessage = sendMessage;
    }
}
