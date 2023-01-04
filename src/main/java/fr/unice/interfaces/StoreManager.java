package fr.unice.interfaces;

import fr.unice.model.Store;

public interface StoreManager {
    boolean addStore(Store store);
    boolean removeStore(String storeName);
    int getStoresSize();
    boolean containsKey(String storeName);
    Store get(String storeName);
    void initialiseRepository();
}
