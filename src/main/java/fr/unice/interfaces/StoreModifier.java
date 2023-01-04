package fr.unice.interfaces;

import fr.unice.model.Store;

import java.time.LocalTime;

public interface StoreModifier {
    void updateOpeningHours(Store store, LocalTime newOpening);
    void updateClosingHours(Store store,LocalTime newClosing);
}
