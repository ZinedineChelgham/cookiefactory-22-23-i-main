package fr.unice.components;


import fr.unice.enums.DAY;
import fr.unice.enums.EventType;
import fr.unice.enums.NotifyHour;
import fr.unice.interfaces.togoodtogo.EventListener;
import fr.unice.interfaces.togoodtogo.SuscriberManaging;
import fr.unice.model.EmailMsgListener;
import fr.unice.model.NotifyTimeConstraint;
import fr.unice.model.Store;
import fr.unice.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertEquals;

@SpringBootTest
public class ToGoodNotificationTest {


    @Autowired
    TGTGNotificationController tgtgNotification;
    @Autowired
    SuscriberManaging suscriberManaging;


    Store store;
    EventListener eventListener;
    User user;
    @BeforeEach
    void setUp() {
        store = new Store("address", "test");
        user = new User("test@gmail.com");
        eventListener = new EmailMsgListener(user);
    }

    @Test
    void subscribe() {
        suscriberManaging.subscribe(store, EventType.NEW_SURPRISE_BOX, eventListener, new NotifyTimeConstraint(DAY.MONDAY, NotifyHour.HOUR_11));
        assertEquals(1, suscriberManaging.getSubscribers(store).size());
    }


    @Test
    void testNotify() {
        suscriberManaging.subscribe(store, EventType.NEW_SURPRISE_BOX, eventListener, new NotifyTimeConstraint(DAY.MONDAY, NotifyHour.HOUR_11));
        tgtgNotification.notify(store, EventType.NEW_SURPRISE_BOX);
    }

    @Test
    void unsubscribe() {
        suscriberManaging.subscribe(store, EventType.NEW_SURPRISE_BOX, eventListener, new NotifyTimeConstraint(DAY.MONDAY, NotifyHour.HOUR_11));
        assertEquals(1, suscriberManaging.getSubscribers(store).size());
        suscriberManaging.unsubscribe(store, eventListener);
        assertEquals(0, suscriberManaging.getSubscribers(store).size());

    }


}
