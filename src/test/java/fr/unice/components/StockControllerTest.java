package fr.unice.components;

import fr.unice.enums.IngredientTag;
import fr.unice.enums.IngredientType;
import fr.unice.interfaces.IngredientsUpdater;
import fr.unice.interfaces.StockModifier;
import fr.unice.model.Ingredient;
import fr.unice.model.Store;
import fr.unice.repositories.IngredientRepository;
import fr.unice.services.CatalogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.naming.ServiceUnavailableException;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StockControllerTest {
    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    @MockBean
    private CatalogService catalogService;

    @Autowired
    private StockModifier stockModifier;

    @Autowired
    private IngredientsUpdater ingredientsUpdater;

    private Store store;

    @BeforeEach
    public void setupContext() {
        ingredientRepository.deleteAll();
        ingredientRepository.save(new Ingredient(IngredientTag.FLOOR.getName(), 0.2, IngredientType.BASIS), IngredientTag.FLOOR);
        store = new Store("address", "name");
    }

    @Test
    public void getNotExistingIngredientAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            stockModifier.getStockOfStore(store, IngredientTag.WHITE_CHOCOLATE.getName());
        });
    }

    @Test
    public void addStock() {
        stockModifier.addIngredientsToStore(store, IngredientTag.FLOOR.getName(), 5);
        assertEquals(5, stockModifier.getStockOfStore(store, IngredientTag.FLOOR.getName()));
        stockModifier.addIngredientsToStore(store, IngredientTag.FLOOR.getName(), 2);
        assertEquals(7, stockModifier.getStockOfStore(store, IngredientTag.FLOOR.getName()));
        stockModifier.addIngredientsToStore(store, IngredientTag.FLOOR.getName(), -3);
        assertEquals(4, stockModifier.getStockOfStore(store, IngredientTag.FLOOR.getName()));
    }

    @Test
    public void addNotExistingIngredientAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            stockModifier.addIngredientsToStore(store, IngredientTag.WHITE_CHOCOLATE.getName(), 1);
        });
    }

    @Test
    public void updateIngredients() throws ServiceUnavailableException {
        List<Ingredient> ingredientList = new LinkedList<>();
        ingredientRepository.findAll().forEach(ingredientList::add);
        ingredientList.add(new Ingredient(IngredientTag.WHITE_CHOCOLATE.getName(), 0.2, IngredientType.BASIS));

        when(catalogService.loadIngredients()).thenReturn(ingredientList);
        ingredientsUpdater.updateIngredientListViaCatalog();

        ingredientRepository.findAll().forEach(ingredient -> {
            assertTrue(ingredientList.contains(ingredient));
        });
    }
}
