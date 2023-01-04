package fr.unice.spring;

import fr.unice.enums.IngredientTag;
import fr.unice.enums.IngredientType;
import fr.unice.interfaces.IngredientsUpdater;
import fr.unice.model.Ingredient;
import fr.unice.repositories.IngredientRepository;
import fr.unice.services.CatalogService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.naming.ServiceUnavailableException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UpdateIngredientsDefinitions {
    List<Ingredient> ingredients;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private CatalogService catalogServiceMock;

    @Autowired
    private IngredientsUpdater ingredientsUpdater;

    @Before
    public void setup() {
        ingredients = new LinkedList<>();

        ingredients.add(new Ingredient(IngredientTag.FLOOR.getName(), 0.2, IngredientType.BASIS));
        ingredients.add(new Ingredient(IngredientTag.EGG.getName(), 0.1, IngredientType.BASIS));

        ingredientRepository.deleteAll();
        ingredientRepository.save(ingredients.get(0), IngredientTag.FLOOR);
        ingredientRepository.save(new Ingredient(IngredientTag.WHITE_CHOCOLATE.getName(),
                                            0.3,
                                                 IngredientType.TOPPING),
                                  IngredientTag.WHITE_CHOCOLATE);
    }

    boolean errorDuringLoading = false;

    @Given("A system with accessible Catalog Service")
    public void aSystemWithAccessibleCatalogService() throws ServiceUnavailableException {
        errorDuringLoading = false;
        when(catalogServiceMock.loadIngredients()).thenReturn(ingredients);
    }

    @Given("A system without accessible Catalog Service")
    public void aSystemWithoutAccessibleCatalogService() {
        errorDuringLoading = false;
        try {
            when(catalogServiceMock.loadIngredients()).thenThrow(new ServiceUnavailableException());
        } catch (ServiceUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Given("A system without a Catalog Service")
    public void aSystemWithoutACatalogService() {
        errorDuringLoading = false;
        try {
            when(catalogServiceMock.loadIngredients()).thenThrow(new IllegalStateException());
        } catch (ServiceUnavailableException e) {
            e.printStackTrace();
        }
    }

    @When("An administrator tries to update the ingredients via service")
    public void anAdministratorTriesToUpdateTheIngredientsViaService() {
        try {
            ingredientsUpdater.updateIngredientListViaCatalog();
        } catch (IllegalStateException | ServiceUnavailableException e) {
            errorDuringLoading = true;
        }
    }

    @Then("All ingredients provided by the service are registered")
    public void allIngredientsProvidedByTheServiceAreInStore() {
        for (Ingredient il : ingredients) {
            IngredientTag tag = IngredientTag.valueOf(il.name().toUpperCase().replace(' ', '_'));
            Ingredient ingredient = ingredientRepository.findById(tag).orElse(null);
            if (ingredient == null)
                fail();
            assertEquals(il, ingredient);
        }
    }

    @Then("An error is thrown")
    public void anErrorIsThrown() {
        assertTrue(errorDuringLoading);
    }

    @Then("All ingredients already registered but not provided are marked unavailable")
    public void allIngredientsAlreadyRegisteredButNotProvidedAreMarkedUnavailable() {
        for (Ingredient ingredient : ingredientRepository.findAll()) {
            boolean notInService = ingredients.stream().noneMatch(i -> Objects.equals(i.name(), ingredient.name()));
            if (notInService)
                assertFalse(ingredient.isAvailable());
        }
    }
}
