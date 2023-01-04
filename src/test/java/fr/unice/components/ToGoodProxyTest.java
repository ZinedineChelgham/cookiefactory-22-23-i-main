package fr.unice.components;


import fr.unice.connectors.ToGoodProxy;
import fr.unice.model.Store;
import fr.unice.model.SurpriseBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@SpringBootTest
public class ToGoodProxyTest {

    @Autowired
    ToGoodProxy toGoodProxy;

    Store store;
    SurpriseBox surpriseBox;


    @BeforeEach
    void setUp() {
        store = new Store("123", "abc");
        surpriseBox = new SurpriseBox(0, store, 0, "desc");
    }

    @Test
    void uploadSurpriseBox() {
        toGoodProxy.uploadSurpriseBox(store, surpriseBox);
        assertTrue(store.getSurpriseBoxes().contains(surpriseBox));
    }

    @Test
    void deleteSurpriseBox() {
        toGoodProxy.uploadSurpriseBox(store, surpriseBox);
        toGoodProxy.deleteSurpriseBox(store, surpriseBox);
        assertFalse(store.getSurpriseBoxes().contains(surpriseBox));
    }

    @Test
    void isSurpriseBoxPayed() {
        assertFalse(toGoodProxy.isSurpriseBoxPayed(surpriseBox));
    }

    @Test
    void isSurpriseBoxDelivered() {
        assertFalse(toGoodProxy.isSurpriseBoxDelivered(surpriseBox));
    }

    @Test
    void paySurpriseBox() {
        toGoodProxy.uploadSurpriseBox(store, surpriseBox);
        toGoodProxy.paySurpriseBox(surpriseBox);
        assertTrue(toGoodProxy.isSurpriseBoxPayed(surpriseBox));
    }

}
