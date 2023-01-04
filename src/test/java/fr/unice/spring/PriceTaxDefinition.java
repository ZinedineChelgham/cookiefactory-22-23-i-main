package fr.unice.spring;

import fr.unice.enums.CookieSize;
import fr.unice.enums.IngredientTag;
import fr.unice.enums.OrderStatus;
import fr.unice.interfaces.OrderProcessing;
import fr.unice.interfaces.RecipeCalculator;
import fr.unice.model.Order;
import fr.unice.model.OrderLine;
import fr.unice.model.Store;
import fr.unice.model.User;
import fr.unice.model.recipe.Recipe;
import fr.unice.model.recipe.RecipeBuilder;
import fr.unice.repositories.IngredientRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


public class PriceTaxDefinition {
    Order order;

    @Autowired
    OrderProcessing orderProcessing;

    @Autowired
    IngredientRepository ingredientRepository;

    @Given("an Order")
    public void createOrder(){
        ingredientRepository.initialise();
        int id = 1;
        order = new Order(id);

        Store store = new Store("a","a");
        assertTrue(store.setTaxRate(25.0F));

        order.setStore(store);
        order.setBuyer(new User());

        assertEquals(id, order.getId());
        assertEquals(OrderStatus.BASKET, order.getStatus());
        assertEquals(0, order.getOrderLines().size());
        assertNotNull(order.getStore());
    }

    @Given("a list of {int} same cookies")
    public void initPriceOfCookies(int n) {
        RecipeBuilder cb = new RecipeBuilder("cookie1")
                .withTopping(ingredientRepository.findById((IngredientTag.WHITE_CHOCOLATE)).get());
        cb.withSize(CookieSize.Normal);
        Recipe recipe1 = cb.getResult();
        orderProcessing.addOrderLine(order,new OrderLine(recipe1,n));
    }

    @When("the a cookie is added")
    public void theACookieOfPricePrice_newIsAdded() {
        RecipeBuilder cb = new RecipeBuilder("cookie2")
                .withTopping(ingredientRepository.findById((IngredientTag.BLACK_CHOCOLATE)).get());
        cb.withSize(CookieSize.Normal);
        Recipe recipe2 = cb.getResult();
        orderProcessing.addOrderLine(order,new OrderLine(recipe2,1));
    }


    @Then("the price change to the according value without taxes {double}")
    public void thePriceChangeToTheAccordingValueFinal_price(double final_price) {
        assertNotEquals(0,order.getStore().getTaxRate()/100);
        assertEquals(final_price, orderProcessing.processPrice(order));
    }
}
