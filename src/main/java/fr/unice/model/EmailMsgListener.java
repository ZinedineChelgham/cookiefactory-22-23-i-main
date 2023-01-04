package fr.unice.model;


import fr.unice.enums.EventType;
import fr.unice.interfaces.togoodtogo.EventListener;
import fr.unice.model.User;

public class EmailMsgListener implements EventListener {

    private final User user;

    public EmailMsgListener(User user) {
        this.user = user;
    }

    @Override
    public void notify(EventType eventType) {
        System.out.println("Sending email to " + user.getMail() + " : " + eventType.name());
    }
}
