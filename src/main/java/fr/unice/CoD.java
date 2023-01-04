package fr.unice;

import fr.unice.enums.DAY;
import fr.unice.enums.NotifyHour;
import fr.unice.model.Store;
import fr.unice.data.StartingData;
import fr.unice.enums.IngredientTag;
import fr.unice.model.*;
import fr.unice.model.cooks.Cook;
import fr.unice.model.recipe.Recipe;
import fr.unice.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import fr.unice.services.CatalogService;
import fr.unice.services.MailService;

import javax.naming.ServiceUnavailableException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * The fr.unice.CoD represents the controller of the project
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class CoD {
    public static void main(String[] args) {
        SpringApplication.run(CoD.class, args);
    }
}
