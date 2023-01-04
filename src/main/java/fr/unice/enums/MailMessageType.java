package fr.unice.enums;

public enum MailMessageType {

    CONFIRMATION("Your order has been confirmed"),
    DELIVERY("Your order has been delivered"),
    CANCELLATION("Your order has been cancelled");

    private final String type;

    MailMessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

