package fr.unice.components;


import fr.unice.CoD;
import fr.unice.enums.EventType;
import fr.unice.interfaces.togoodtogo.EventListener;
import fr.unice.interfaces.togoodtogo.SuscriberManaging;
import fr.unice.interfaces.togoodtogo.TGTGNotification;
import fr.unice.model.EmailMsgListener;
import fr.unice.model.NotifyTimeConstraint;
import fr.unice.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TGTGNotificationController implements TGTGNotification, SuscriberManaging {


    @Autowired
    public TGTGNotificationController() {

    }


    @Override
    public void notify(Store store, EventType eventType) {
        for (Map.Entry<EmailMsgListener, NotifyTimeConstraint> entry : getSubscribers(store).entrySet()) {
            NotifyTimeConstraint constraint = entry.getValue();
//            if (constraint.getConstraint().getKey() == CoD.CURRENT_DAY && constraint.getConstraint().getValue() == CoD.CURRENT_HOUR) {
//                entry.getKey().notify(eventType);
//            }
        }
    }

    @Override
    public void subscribe(Store store, EventType eventType, EventListener listener, NotifyTimeConstraint constraint) {
        if (eventType == EventType.NEW_SURPRISE_BOX) {
            getSubscribers(store).put((EmailMsgListener) listener, constraint);
        }
    }

    @Override
    public void unsubscribe(Store store, EventListener listener) {
        getSubscribers(store).remove(listener);
    }

    @Override
    public Map<EmailMsgListener, NotifyTimeConstraint> getSubscribers(Store store) {
        return store.getCustomers();
    }
}
