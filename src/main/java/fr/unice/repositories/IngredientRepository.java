package fr.unice.repositories;

import fr.unice.data.StartingData;
import fr.unice.enums.IngredientTag;
import fr.unice.model.Ingredient;
import org.springframework.stereotype.Repository;

@Repository
public class IngredientRepository extends BasicRepository<Ingredient, IngredientTag>{
    public void initialise() {
        StartingData startingData = new StartingData();
        startingData.getAllIngredients().forEach((k,v)->this.save(v,k));
    }
}
