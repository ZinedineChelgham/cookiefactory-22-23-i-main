package fr.unice.enums;

/**
 * Indicates in what state of the customer journey the order is.
 */
public enum OrderStatus {
    BASKET("basket"),
    PAYED("payed"),
    PREPARATION("preparation"),
    READY("ready"),
    DELIVERED("delivered"),
    CANCELLED("canceled"),
    FORGOTTEN("forgotten"),
    SURPRISE_BOXED("surpriseBox");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
