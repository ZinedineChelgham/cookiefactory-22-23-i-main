package fr.unice.connectors;


import fr.unice.interfaces.SurpriseBoxHandler;
import fr.unice.interfaces.togoodtogo.TGTGSurpriseBox;
import fr.unice.model.Store;
import fr.unice.model.SurpriseBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ToGoodProxy implements TGTGSurpriseBox {


    SurpriseBoxHandler surpriseBoxModifier;

    @Autowired
    public ToGoodProxy(SurpriseBoxHandler surpriseBoxModifier) {
        this.surpriseBoxModifier = surpriseBoxModifier;
    }

    @Override
    public void uploadSurpriseBox(Store store, SurpriseBox surpriseBox) {
        surpriseBoxModifier.addSurpriseBox(store, surpriseBox);

    }

    @Override
    public void deleteSurpriseBox(Store store, SurpriseBox surpriseBox) {
        surpriseBoxModifier.removeSurpriseBox(store, surpriseBox);
    }


    @Override
    public boolean isSurpriseBoxPayed(SurpriseBox surpriseBox) { return surpriseBox.isPayed(); }

    @Override
    public boolean isSurpriseBoxDelivered(SurpriseBox surpriseBox) {
        return surpriseBox.isDelivered();
    }

    @Override
    public void paySurpriseBox(SurpriseBox surpriseBox) { surpriseBox.setPayed(true); }


}
