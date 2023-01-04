package fr.unice.interfaces.togoodtogo;


import fr.unice.model.Store;
import fr.unice.model.SurpriseBox;


public interface TGTGSurpriseBox { //TGTG = ToGoodToGo


    void uploadSurpriseBox(Store store, SurpriseBox surpriseBox);

    void deleteSurpriseBox(Store store, SurpriseBox surpriseBox);

    boolean isSurpriseBoxPayed(SurpriseBox surpriseBox);

    boolean isSurpriseBoxDelivered(SurpriseBox surpriseBox);

    void paySurpriseBox(SurpriseBox surpriseBox);

}
