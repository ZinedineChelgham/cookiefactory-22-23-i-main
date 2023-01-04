package fr.unice.components;

import fr.unice.data.StartingData;
import fr.unice.enums.OrderStatus;
import fr.unice.interfaces.*;
import fr.unice.model.*;
import fr.unice.model.cooks.Cook;
import fr.unice.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StoreController implements StoreModifier, StoreOrderProcessing, StoreManager, SurpriseBoxHandler {

    CookOrderProcessing cookOrderProcessing;
    StoreRepository storeRepository;
    TimeSlotManager timeSlotManager;

    @Autowired
    public StoreController(CookOrderProcessing cookOrderProcessing, StoreRepository storeRepository, TimeSlotManager timeSlotManager) {
        this.cookOrderProcessing = cookOrderProcessing;
        this.storeRepository = storeRepository;
        this.timeSlotManager = timeSlotManager;
    }

    @Override
    public void updateOpeningHours(Store store, LocalTime newOpening) {
        store.setOpeningHour(newOpening);
        removeOrderInCloseTimeFrame(store);
    }

    @Override
    public void updateClosingHours(Store store, LocalTime newClosing) {
        store.setClosingHour(newClosing);
        removeOrderInCloseTimeFrame(store);
    }

    /**
     * Associate the given order with one of the cooks schedule
     *
     * @param order Order
     * @return true if the order has been associated, false otherwise
     */
    @Override
    public boolean tryAssociateOrderWithScheduler(Store store, Order order) {
        if (store.getOpeningHour().isAfter(order.getPickupTime().toLocalTime()) || store.getClosingHour().isBefore(order.getPickupTime().toLocalTime()))
            return false;

        for (Cook cook : store.getCooks()) {
            if (cookOrderProcessing.tryAddingOrderInSchedule(cook, order))
                return true;
        }
        return false;
    }

    @Override
    public Optional<Cook> getCookByOrder(Order order) {
        return cookOrderProcessing.getCookByOrder(order);
    }

    @Override
    public boolean deleteTimeSlot(Cook cook, Order order) {
        return timeSlotManager.deleteTimeSlot(cook, order);
    }

    @Override
    public boolean addStore(Store store){
        storeRepository.save(store, store.getName());
        return true;
    }

    @Override
    public boolean removeStore(String storeName){
        storeRepository.deleteById(storeName);
        return true;
    }

    @Override
    public int getStoresSize(){
        int i = 0;
        for(Store s: storeRepository.findAll()) i++;
        return i;
    }

    @Override
    public boolean containsKey(String storeName){
        return storeRepository.existsById(storeName);
    }

    @Override
    public Store get(String storeName){
        return storeRepository.findById(storeName).get();
    }

    @Override
    public void initialiseRepository(){
        StartingData startingData = new StartingData();
        for (Store s: startingData.getStores().values()) storeRepository.save(s,s.getName());
    }

    @Override
    public Set<SurpriseBox> getSurpriseBoxes(Store store) {
        return store.getSurpriseBoxes();
    }

    @Override
    public SurpriseBox createSurpriseBox(Store store) {
        List<Order> forgottenOrders = store.getOrders().stream()
                .filter(order -> order.getStatus() == OrderStatus.FORGOTTEN)
                .collect(Collectors.toList());
        if(forgottenOrders.size() == 0) throw new IllegalStateException("No forgotten orders to create a surprise box");
        double price = forgottenOrders.stream().mapToDouble(Order::getPrice).sum();
        int nbCookies = forgottenOrders.stream().mapToInt(Order::getNumberOfCookiesOrdered).sum();
        String desc = String.format("Surprise box containing %d cookies", nbCookies);

        //change state of forgotten orders
        forgottenOrders.forEach(order -> order.setStatus(OrderStatus.SURPRISE_BOXED));
        return new SurpriseBox(getSurpriseBoxes(store).size(), store, price, desc);
    }

    @Override
    public void deliverSurpriseBox(Store store, SurpriseBox surpriseBox) {
            if(surpriseBox.isPayed()) {
                surpriseBox.setDelivered(true);
                removeDeliveredSurpriseBoxes(store);
            }
    }


    @Override
    public void addSurpriseBox(Store store, SurpriseBox surpriseBox) {
        store.getSurpriseBoxes().add(surpriseBox);
    }

    @Override
    public void removeSurpriseBox(Store store, SurpriseBox surpriseBox) {
        store.getSurpriseBoxes().remove(surpriseBox);
    }

    @Override
    public void removeDeliveredSurpriseBoxes(Store store) {
        getSurpriseBoxes(store).removeIf(SurpriseBox::isDelivered);
    }

    private void removeOrderInCloseTimeFrame(Store store){
        for(Order order : store.getOrders()){
            if(store.getOpeningHour().isAfter(order.getPickupTime().toLocalTime()) || store.getClosingHour().isBefore(order.getPickupTime().toLocalTime())) {
                order.setStatus(OrderStatus.CANCELLED);
            }
        }
    }
}
