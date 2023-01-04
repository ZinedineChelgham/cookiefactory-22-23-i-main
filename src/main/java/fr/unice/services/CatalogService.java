package fr.unice.services;

import fr.unice.model.Ingredient;

import javax.naming.ServiceUnavailableException;
import java.util.List;

/**
 * Contains all the functionality to get the ingredients from the providers catalog
 */
public interface CatalogService {
    /**
     * Gets the list of available ingredients from the supplier
     * @return list of available ingredients
     * @throws ServiceUnavailableException when the service is not reachable or not available
     */
    List<Ingredient> loadIngredients() throws ServiceUnavailableException;
}
