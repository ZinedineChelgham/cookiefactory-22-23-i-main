package fr.unice.spring;

import fr.unice.interfaces.StoreManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import fr.unice.model.Store;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;




public class ManageStoresDefinition {
    @Autowired
    StoreManager storeManager;

//    CoD cod = new CoD();
    int size;
    String storeName;

    @Given("a list of all stores")
    public void aListOfAllStores() {
        assertNotNull(storeManager);

        size = storeManager.getStoresSize();
    }

    @Given("a not null list of stores named stores")
    public void listNotNull(){
        storeManager.initialiseRepository();
        assertNotEquals(0,storeManager.getStoresSize());
    }

    @When("someone add a new store named {string}")
    public void someoneAddANewStoreNamedStore(String name) {
        storeName = name;
        assertFalse(storeManager.containsKey(name));
        assertTrue(storeManager.addStore(getNewStore(name)));
        size++;
    }

    @Then("the store list size is increment and the store is in the list")
    public void theStoreListSizeIsIncrementAndTheStoreIsInTheList() {
        assertEquals(size, storeManager.getStoresSize());
        assertTrue(storeManager.containsKey(storeName));
    }

    @When("someone remove a existing store named {string}")
    public void someoneRemoveAExistingStoreNamedStore(String name) {
        storeManager.addStore(getNewStore(name));
        size++;

        if(storeManager.containsKey(name)) {
            assertTrue(storeManager.removeStore(name));
            size--;
        }
        else assertFalse(storeManager.removeStore(name));
        storeName = name;
    }

    @Then("the store list size is decrement and the store is not in the list")
    public void theStoreListSizeIsDecrementAndTheStoreIsNotInTheList() {
        assertEquals(size, storeManager.getStoresSize());
        assertFalse(storeManager.containsKey(storeName));
    }

    @When("the store manager changes the vat of {string} to {double}")
    public void theStoreManagerChangesTheVatOfStoreToVat_input(String storeName, double taxRate) {
        Store store = storeManager.get(storeName);
        store.setTaxRate((float) taxRate);
    }

    @Then("the vat of the store {string} is {double}")
    public void theVatOfTheStoreIsVat_effective(String storeName, double taxRate) {
        Store store = storeManager.get(storeName);
        assertEquals(taxRate, store.getTaxRate());
    }

    private Store getNewStore(String name) {
        return new Store("adresse",name);
    }

}
