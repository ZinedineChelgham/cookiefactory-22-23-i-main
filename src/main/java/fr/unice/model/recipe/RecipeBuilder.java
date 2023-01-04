package fr.unice.model.recipe;

import fr.unice.enums.CookieSize;
import fr.unice.enums.CookingTypes;
import fr.unice.enums.MixTypes;
import fr.unice.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class RecipeBuilder {
    String name = "";
    List<Ingredient> toppings = new ArrayList<>();
    Ingredient flavor = null;
    Ingredient dough = null;
    MixTypes mix = MixTypes.MIXED;
    CookingTypes cooking = CookingTypes.MEDIUM;
    int preparationTime = 10;
    CookieSize size = CookieSize.Normal;

    public RecipeBuilder(String name){
        if(name == null) throw new IllegalArgumentException("A name as to be implemented.");
        this.name = name;
    }

    RecipeBuilder(Recipe recipe){
        this.name = recipe.getName();
        this.toppings = recipe.getToppings();
        this.flavor = recipe.getFlavor();
        this.dough = recipe.getDough();
        this.mix = recipe.getMix();
        this.cooking = recipe.getCooking();
        this.preparationTime = recipe.getPreparationTime();
    }

    public RecipeBuilder withPreparationTime(int time){
        this.preparationTime = time;
        return this;
    }

    public RecipeBuilder withTopping(Ingredient topping){
        this.toppings.add(topping);
        return this;
    }

    public RecipeBuilder withToppings(List<Ingredient> toppings){
        this.toppings.addAll(toppings);
        return this;
    }

    public RecipeBuilder withFlavor(Ingredient flavor){
        this.flavor = flavor;
        return this;
    }

    public RecipeBuilder withDough(Ingredient dough){
        this.dough = dough;
        return this;
    }

    public RecipeBuilder withMixType(MixTypes mix){
        this.mix = mix;
        return this;
    }

    public RecipeBuilder withCookingType(CookingTypes cooking){
        this.cooking = cooking;
        return this;
    }

    public void withSize(CookieSize size) {
        this.size = size;
    }

    public Recipe getResult(){
        return new Recipe(this);
    }
}
