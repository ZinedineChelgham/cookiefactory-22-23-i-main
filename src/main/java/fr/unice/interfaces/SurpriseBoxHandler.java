package fr.unice.interfaces;


import fr.unice.model.Store;
import fr.unice.model.SurpriseBox;
import java.util.Set;

public interface SurpriseBoxHandler {

    Set<SurpriseBox> getSurpriseBoxes(Store store);

    void addSurpriseBox(Store store, SurpriseBox surpriseBox);

    void removeSurpriseBox(Store store, SurpriseBox surpriseBox);

    void removeDeliveredSurpriseBoxes(Store store);

    SurpriseBox createSurpriseBox(Store store);

    void deliverSurpriseBox(Store store, SurpriseBox surpriseBox);




}
