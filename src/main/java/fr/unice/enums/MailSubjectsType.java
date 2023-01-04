package fr.unice.enums;



public enum MailSubjectsType {

    ORDER_CONFIRMATION("Order confirmation"),
    ORDER_DELIVERY("Order delivery"),
    ORDER_CANCELLATION("Order cancellation");

    private final String subject;

    MailSubjectsType(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }
}
