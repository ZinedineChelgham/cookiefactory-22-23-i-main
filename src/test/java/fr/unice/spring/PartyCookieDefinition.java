package fr.unice.spring;

import fr.unice.CoD;
import fr.unice.Exceptions.ErrorPaymentOrderExcpetion;
import fr.unice.Exceptions.MissOrderInformationException;
import fr.unice.enums.CookieSize;
import fr.unice.enums.IngredientTag;
import fr.unice.interfaces.OrderProcessing;
import fr.unice.model.Order;
import fr.unice.model.OrderLine;
import fr.unice.model.User;
import fr.unice.model.recipe.Recipe;
import fr.unice.model.recipe.RecipeBuilder;
import fr.unice.repositories.IngredientRepository;
import fr.unice.repositories.StoreRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;



public class PartyCookieDefinition {
    Order orderParty;
    Order orderNormal;
    Recipe recipeParty;
    Recipe recipeNormal;

    @Autowired
    OrderProcessing orderProcessing;

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    StoreRepository storeRepository;


    @Given("recipe of party cookie is available")
    public void aRecipeOfPartyCookieIsAvailable() {
        assertEquals(CookieSize.Normal, recipeNormal.getSize());
        assertEquals(CookieSize.XL, recipeParty.getSize());
    }

    @Given("a user that want to order some party cookies")
    public void aUserThatWantToOrderSomePartyCookies() {
        ingredientRepository.initialise();
        storeRepository.initialise();

        RecipeBuilder cb = new RecipeBuilder("partyTest")
                .withTopping(ingredientRepository.findById(IngredientTag.BLACK_CHOCOLATE).get());
        cb.withSize(CookieSize.XL);
        recipeParty = cb.getResult();

        RecipeBuilder r = new RecipeBuilder("normalTest")
                .withTopping(ingredientRepository.findById(IngredientTag.BLACK_CHOCOLATE).get());
        recipeNormal = r.getResult();
    }

    @When("the user add {int} recipe in his order")
    public void theUserAddNbCookieAddedRecipeInHisOrder(int nbCookie) throws ErrorPaymentOrderExcpetion, MissOrderInformationException {
        User user = new User("emal", "nb");

        orderParty = new Order(52);
        orderParty.setStore(storeRepository.findById("CookieCooked").get());
        orderProcessing.addOrderLine(orderParty,new OrderLine(recipeParty, nbCookie));
        orderParty.setBuyer(user);
        orderParty.setPickupTime(LocalDateTime.of(2022,01,01,10,00));
        orderProcessing.validateOrder(orderParty);

        orderNormal = new Order(53);
        orderNormal.setStore(storeRepository.findById("CookieCooked").get());
        orderProcessing.addOrderLine(orderNormal,new OrderLine(recipeNormal, nbCookie));
        orderNormal.setBuyer(user);
        orderNormal.setPickupTime(LocalDateTime.of(2022,01,01,10,00));
        orderProcessing.validateOrder(orderNormal);
    }

    @Then("the party cookie is in the order")
    public void thePartyCookieIsInTheOrder() {
        assertNotEquals(0,orderParty.getPrice());
        assertTrue(orderParty.getPrice()>orderNormal.getPrice());
    }
}