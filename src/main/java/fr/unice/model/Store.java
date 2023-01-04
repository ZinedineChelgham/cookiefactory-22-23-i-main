package fr.unice.model;

import fr.unice.enums.OrderStatus;
import fr.unice.model.cooks.Cook;

import java.time.LocalTime;
import java.util.*;

/**
 * Contains all the information of a store and all the interactions to work with the store.
 */
public class Store {
    private String address;
    private LocalTime openingHour;
    private LocalTime closingHour;
    private String name;
    private float taxRate = 0;

    private final List<Cook> cooks;
    private final List<Order> orders;

    private final HashMap<String, Stock> stock;

    private final Set<SurpriseBox> surpriseBoxes;

    private final Map<EmailMsgListener, NotifyTimeConstraint> customers;


    public Store(String address, String name){
        this.address = address;
        this.name = name;
        orders = new ArrayList<>();
        stock = new HashMap<>();
        cooks = new ArrayList<>();
        surpriseBoxes = new HashSet<>();
        customers = new HashMap<>();

        // default opening hours
        openingHour = LocalTime.of(8, 0);
        closingHour = LocalTime.of(19, 0);
    }

    public HashMap<String, Stock> getStock() {
        return stock;
    }

    public Map<EmailMsgListener, NotifyTimeConstraint> getCustomers() {
        return customers;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public float getTaxRate() {
        return taxRate;
    }

    public boolean setTaxRate(float taxRate) {
        if (taxRate < 0.0 || taxRate > 100.0)
            return false;
        this.taxRate = taxRate;
        return true;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addCook(Cook cook) {
        cooks.add(cook);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getOpeningHour() {
        return openingHour;
    }

    public LocalTime getClosingHour() {
        return closingHour;
    }

    public List<Cook> getCooks() {
        return cooks;
    }



    public Set<SurpriseBox> getSurpriseBoxes() {
        return surpriseBoxes;
    }

    public void setOpeningHour(LocalTime openingHour) {
        this.openingHour = openingHour;
    }

    public void setClosingHour(LocalTime closingHour) {
        this.closingHour = closingHour;
    }
}
