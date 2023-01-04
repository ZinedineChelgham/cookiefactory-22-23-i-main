package fr.unice.repositories;

import fr.unice.data.StartingData;
import fr.unice.model.Store;
import org.springframework.stereotype.Repository;

@Repository
public class StoreRepository extends BasicRepository<Store, String> {
    public void initialise() {
        StartingData startingData = new StartingData();
        startingData.getStores().forEach((k,v)->this.save(v,k));
    }
}
