package fr.unice.model;


public class SurpriseBox {

    private final int id;
    private final Store store;
    private final double price;
    private final String description;
    private boolean isPayed = false;
    private boolean isDelivered = false;

    public SurpriseBox(int id, Store store, double price, String description) {
        this.id = id;
        this.store = store;
        this.price = price;
        this.description = description;
    }

    // Getters
    public Store getStore() {
        return store;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    @Override
    public String toString() {
        return "SurpriseBox{" +
                "store=" + store +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
