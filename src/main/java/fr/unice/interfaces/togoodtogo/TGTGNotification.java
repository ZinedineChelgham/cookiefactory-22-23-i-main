package fr.unice.interfaces.togoodtogo;


import fr.unice.enums.EventType;
import fr.unice.model.EmailMsgListener;
import fr.unice.model.NotifyTimeConstraint;
import fr.unice.model.Store;

import java.util.HashMap;
import java.util.Map;

public interface TGTGNotification {

     void notify(Store store, EventType eventType);



}


