package fr.unice.interfaces;

import javax.naming.ServiceUnavailableException;

/**
 * Contains all functionalities to update the stock base (the ingredients) automaticly
 */
public interface IngredientsUpdater {

    /**
     * Updates the list of ingredients via the catalog of a supplier.
     *
     * @throws ServiceUnavailableException is thrown if the service is not available.
     * @throws IllegalStateException is thrown if the service is set up badly.
     */
    void updateIngredientListViaCatalog() throws ServiceUnavailableException, IllegalStateException;
}
