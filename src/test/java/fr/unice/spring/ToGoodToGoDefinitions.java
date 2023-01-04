package fr.unice.spring;

import fr.unice.enums.DAY;
import fr.unice.enums.EventType;
import fr.unice.enums.NotifyHour;
import fr.unice.enums.OrderStatus;
import fr.unice.interfaces.SurpriseBoxHandler;
import fr.unice.interfaces.togoodtogo.EventListener;
import fr.unice.interfaces.togoodtogo.SuscriberManaging;
import fr.unice.interfaces.togoodtogo.TGTGNotification;
import fr.unice.interfaces.togoodtogo.TGTGSurpriseBox;
import fr.unice.model.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;






public class ToGoodToGoDefinitions {

    @Autowired
    TGTGSurpriseBox tgtg;

    @Autowired
    SurpriseBoxHandler surpriseBoxHandler;

    @Autowired
    TGTGNotification tgtgNotification;

    @Autowired
    SuscriberManaging suscriberManaging;

    Store store;
    Order order;
    SurpriseBox surpriseBox;

    User user;
    EventListener eventListener;

    @Given("a store")
    public void aStore() {
        store = new Store("test", "test");
    }

    @Given("an order in forgotten state")
    public void anOrderInForgottenState() {
        order = new Order(0);
        order.setStatus(OrderStatus.FORGOTTEN);
        store.addOrder(order);
    }

    @When("the system three hours counter trigger")
    public void theSystemThreeHoursCounterTrigger() {
        surpriseBox = surpriseBoxHandler.createSurpriseBox(store);
    }

    @Then("a new surprise box is created and uploaded to the app")
    public void aNewSurpriseBoxIsCreatedAndUploadedToTheApp() {
        assert surpriseBox != null;
        tgtg.uploadSurpriseBox(store, surpriseBox);
        assertEquals(1, store.getSurpriseBoxes().size());
        assertTrue(store.getSurpriseBoxes().contains(surpriseBox));
    }

    @Given("a surprise box in the to good to go app")
    public void aSurpriseBoxInTheToGoodToGoApp() {
        order = new Order(0);
        order.setStatus(OrderStatus.FORGOTTEN);
        store.addOrder(order);
        surpriseBox = surpriseBoxHandler.createSurpriseBox(store);
        tgtg.uploadSurpriseBox(store, surpriseBox);
    }

    @When("the surprise box is picked up")
    public void theSurpriseBoxIsPickedUp() {
        tgtg.paySurpriseBox(surpriseBox);
        assertTrue(tgtg.isSurpriseBoxPayed(surpriseBox));
        surpriseBoxHandler.deliverSurpriseBox(store, surpriseBox);
        assertTrue(tgtg.isSurpriseBoxDelivered(surpriseBox));

    }

    @Then("the surprise box is removed from the to good to go app")
    public void theSurpriseBoxIsRemovedFromTheToGoodToGoApp() {
        tgtg.deleteSurpriseBox(store, surpriseBox);
        assertEquals(0, store.getSurpriseBoxes().size());
    }

    @And("the surprise box is marked as picked up")
    public void theSurpriseBoxIsMarkedAsPickedUp() {
        assertTrue(tgtg.isSurpriseBoxDelivered(surpriseBox));
    }



    Store store2 = new Store("test", "test");
    @Given("a user")
    public void aUser() {
        user = new User("Test User", "Test Address");
        eventListener = new EmailMsgListener(user);
    }

    @When("the user subscribes to receive notification when a surprise box is available on Monday at eleven AM")
    public void theUserSubscribesToReceiveNotificationWhenASurpriseBoxIsAvailableOnMondayAtElevenAM() {
        suscriberManaging.subscribe(store2, EventType.NEW_SURPRISE_BOX, eventListener, new NotifyTimeConstraint(DAY.MONDAY, NotifyHour.HOUR_11));
        assertEquals(1, suscriberManaging.getSubscribers(store2).size());
    }

    @Then("the user will receives a notification when a surprise box is available on Monday at eleven AM")
    public void theUserWillReceivesANotificationWhenASurpriseBoxIsAvailableOnMondayAtElevenAM() {
        tgtgNotification.notify(store2, EventType.NEW_SURPRISE_BOX);
    }

    @When("the user unsubscribes to receive notification when a surprise box is available on Monday at eleven AM")
    public void theUserUnsubscribesToReceiveNotificationWhenASurpriseBoxIsAvailableOnMondayAtElevenAM() {
        suscriberManaging.subscribe(store2, EventType.NEW_SURPRISE_BOX, eventListener, new NotifyTimeConstraint(DAY.MONDAY, NotifyHour.HOUR_11));
        assertEquals(1, suscriberManaging.getSubscribers(store2).size());
        suscriberManaging.unsubscribe(store2, eventListener);
        assertEquals(0, suscriberManaging.getSubscribers(store2).size());
    }

    @Then("the user will not receives a notification when a surprise box is available on Monday at eleven AM")
    public void theUserWillNotReceivesANotificationWhenASurpriseBoxIsAvailableOnMondayAtElevenAM() {
        tgtgNotification.notify(store2, EventType.NEW_SURPRISE_BOX);
    }
}
