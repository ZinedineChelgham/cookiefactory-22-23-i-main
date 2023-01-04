package fr.unice.interfaces.togoodtogo;


import fr.unice.enums.EventType;
import fr.unice.model.EmailMsgListener;
import fr.unice.model.NotifyTimeConstraint;
import fr.unice.model.Store;

import java.util.Map;

public interface SuscriberManaging {

    void subscribe(Store store, EventType eventType, EventListener listener, NotifyTimeConstraint constraint);

    void unsubscribe(Store store, EventListener listener);

    Map<EmailMsgListener, NotifyTimeConstraint> getSubscribers(Store store);

}

