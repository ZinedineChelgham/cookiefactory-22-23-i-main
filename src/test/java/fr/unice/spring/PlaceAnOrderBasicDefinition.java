package fr.unice.spring;

import fr.unice.CoD;
import fr.unice.Exceptions.BadUserInformationsException;
import fr.unice.Exceptions.ErrorPaymentOrderExcpetion;
import fr.unice.Exceptions.MissOrderInformationException;
import fr.unice.enums.MailMessageType;
import fr.unice.enums.MailSubjectsType;
import fr.unice.interfaces.OrderCreator;
import fr.unice.interfaces.OrderProcessing;
import fr.unice.model.Order;
import fr.unice.model.OrderLine;
import fr.unice.model.Store;
import fr.unice.model.User;
import fr.unice.model.cooks.Cook;
import fr.unice.model.recipe.Recipe;
import fr.unice.repositories.OrderRepository;
import fr.unice.repositories.StoreRepository;
import fr.unice.services.BasicMailService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



public class PlaceAnOrderBasicDefinition {
    Store store;
    Recipe recipe;
    Order order;
    Cook cook;

    @Autowired
    BasicMailService mailService;

    @Autowired
    OrderProcessing orderProcessing;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    OrderCreator orderCreator;

    @Autowired
    OrderRepository orderRepository;

    @Given("a store named {string} exists")
    public void a_store_named_exists(String storeName) {
        storeRepository.initialise();
        store = new Store("random adress", storeName);
        storeRepository.save(store,storeName);
    }

    @Given("a cookie recipe named {string} exists")
    public void a_cookie_recipe_named_exists(String recipeName) {
        recipe = mock(Recipe.class);
        when(recipe.getName()).thenReturn(recipeName);
    }

    @Given("an available cook {string} in the store")
    public void anAvailableCookInTheStore(String cookName) {
        cook = new Cook(cookName);
        store.addCook(cook);
    }

    @Given("a list of stores")
    public void a_list_of_stores() {

    }

    @When("the client select the store {string} to make his order with id {int}")
    public void the_client_select_the_store_to_make_his_order(String storeName, int id) {
        Store selectedStore = storeRepository.findById(storeName).get();
        order = orderCreator.createOrder(selectedStore, id);
    }

    @Then("the order is create in the repository {int}")
    public void the_order_is_create_in_the_repository(int id) {
        assertTrue(orderRepository.existsById(id));
    }

    @Then("the order has the store {string}")
    public void the_order_has_the_store(String storeName) {
        assertEquals(storeName, order.getStore().getName());
    }

    @Then("the order has the initial status {string}")
    public void the_order_has_the_initial_status(String orderStatusLabel) {
        assertEquals(orderStatusLabel, order.getStatus().getLabel());
    }

    @Given("an empty order")
    public void an_empty_order() {
        order = orderCreator.createOrder(store,1);
    }

    @When("the client add a cookie {string} in the order")
    public void the_client_want_to_add_cookie_in_the_order(String recipeName) {
        OrderLine orderLine = new OrderLine(recipe, 1);
        orderProcessing.addOrderLine(order,orderLine);
    }

    @Then("the order contains a cookie of the recipe {string}")
    public void the_order_has_cookie_of_the_recipe(String recipeName) {
        assertTrue(order.getOrderLines().containsKey(recipeName));
    }


    @When("the client validate the order")
    public void the_client_validate_the_order() throws ErrorPaymentOrderExcpetion, MissOrderInformationException {
        order.setBuyer(new User("test@gmail.com"));
        orderProcessing.validateOrder(order);

    }

    @Then("the order has the status {string}")
    public void the_order_has_the_status(String orderStatusLabel) {
        assertEquals(orderStatusLabel, order.getStatus().getLabel());
    }

    @And("a confirmation email was sent to the client")
    public void aConfirmationEmailIsSentToTheClient() {
        assertNotNull(mailService.sendMail(order.getBuyer().getMail(), MailSubjectsType.ORDER_CONFIRMATION,
                MailMessageType.CONFIRMATION, orderProcessing.generateBill(order)));

    }

    @Given("an order with the status {string}")
    public void an_order_with_the_status(String string) {
        order = orderCreator.createOrder(store,2);
    }
    @Given("a chosen time to pickup")
    public void a_chosen_time_to_pickup() {
        order.setPickupTime(LocalDateTime.of(2022,01,01,10,00));
    }
    @Given("a user with mail {string}")
    public void a_user_with_mail(String mail) {
        User user = new User();
        user.setEmail(mail);
        order.setBuyer(user);
    }

    @When("the client link his order with the mail {string} and phone {string}")
    public void the_client_link_his_order_with_the_mail_and_phone(String mail, String phone) throws BadUserInformationsException {
        orderProcessing.associateUserInfoInOrder(order, mail, phone);
    }
    @Then("the order has a user with mail {string} and phone {string}")
    public void the_order_has_a_user_with_mail_and_phone(String mail, String phone) {
        assertNotNull(order.getBuyer());
        assertEquals(mail, order.getBuyer().getMail());
        assertEquals(phone, order.getBuyer().getPhoneNumber());
    }
}
