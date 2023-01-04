package fr.unice.services;

import fr.unice.model.Ingredient;
import org.springframework.stereotype.Component;

import javax.naming.ServiceUnavailableException;
import java.util.LinkedList;
import java.util.List;

@Component
public class BasicCatalogService implements CatalogService {
    @Override
    public List<Ingredient> loadIngredients() throws ServiceUnavailableException {
        return new LinkedList<>();
    }
}
