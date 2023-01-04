package fr.unice.repositories;

import fr.unice.data.StartingData;
import fr.unice.model.recipe.Recipe;
import org.springframework.stereotype.Repository;

import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class RecipeRepository extends BasicRepository<Recipe, String>{
    public void initialise() {
        StartingData startingData = new StartingData();
        startingData.getRecipes().forEach((k,v)->this.save(v,k));
    }
}
