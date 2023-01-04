package fr.unice.spring;

import fr.unice.Exceptions.ErrorPaymentOrderExcpetion;
import fr.unice.Exceptions.MissOrderInformationException;
import fr.unice.interfaces.OrderProcessing;
import fr.unice.interfaces.RecipeCalculator;
import fr.unice.model.Store;
import fr.unice.data.StartingData;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import fr.unice.model.*;
import fr.unice.model.cooks.Cook;
import fr.unice.model.recipe.Recipe;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;


public class LoyaltyProgramDefinitions {
    LoyalMember member;
    OrderLine orderLine;
    Order order;
    Recipe recipe;
    Store store;

    @Autowired
    RecipeCalculator recipeCalculator;

    @Autowired
    OrderProcessing orderProcessing;

    @Given("A Store")
    public void aStore() {
        store =  new Store("Store", "random");
        store.setTaxRate(0.1f);
        store.addCook(new Cook("r"));
    }

    @Given("A member that subscribed to the loyalty program")
    public void aMemberThatSubscribedToTheLoyaltyProgram() {
        member = new LoyalMember("test@gmail", "06 05 04 03 02", 0);
        member.setPassword("psw");
    }


    @Given("the recipe {string} that cost {string}")
    public void theRecipeThatCost(String arg0, String arg1) {
        recipe = new StartingData().getRecipes().get(arg0);
        assertEquals(Double.parseDouble(arg1), recipeCalculator.getPrice(recipe));
    }

    @Given("An order line that contains one {string}")
    public void anOrderLineThatContainsOne(String arg0) {
        orderLine = new OrderLine(recipe, 1);
    }

    @Given("An order that contains the previous order line")
    public void anOrderThatContainsThePreviousOrderLine() {
        order = new Order(0,null);
        order.setStore(store);
        orderProcessing.addOrderLine(order,orderLine);
        order.setPickupTime(LocalDateTime.of(2022,01,01,10,00));
    }


   //Sce 1
    @Given("A member that order his {int}th cookie")
    public void aMemberThatOrderHisThCookie(int arg0) {
    member.setCookiesSinceLastLoyalty(arg0-1);
    order.setBuyer(member);


    }

    @When("The member validate the order")
    public void theMemberValidateTheOrder() throws ErrorPaymentOrderExcpetion, MissOrderInformationException {
       orderProcessing.validateOrder(order);
    }

    @Then("The price should be {string}")
    public void thePriceShouldBe(String arg0) {
        assertEquals(Double.parseDouble(arg0), order.getPrice(), 0.01);
        assertEquals(0, member.getCookiesSinceLastLoyalty());

    }


    //Sce 2
    @Given("A member that order his {int}thh cookie")
    public void aMemberThatOrderHisThhCookie(int arg0) {
        member.setCookiesSinceLastLoyalty(arg0-1);
        orderLine = new OrderLine(recipe, 1);
        order = new Order(0, null);
        order.setBuyer(member);
        order.setStore(store);
        orderProcessing.addOrderLine(order,orderLine);
        order.setPickupTime(LocalDateTime.of(2022,01,01,10,00));
    }

    @When("The member validate this new order")
    public void theMemberValidateThisNewOrder() throws ErrorPaymentOrderExcpetion, MissOrderInformationException {
        orderProcessing.validateOrder(order);
    }

    @Then("The price should be now {string}")
    public void thePriceShouldBeNow(String arg0) {
        assertEquals(Double.parseDouble(arg0), order.getPrice(), 0.2);
        assertEquals(20, member.getCookiesSinceLastLoyalty());

    }

}
