package fr.unice.enums;

public enum IngredientTag {
    FLOOR("Floor"),
    WHITE_CHOCOLATE("White chocolate"),
    BLACK_CHOCOLATE("Black chocolate"),
    MILK_CHOCOLATE("Milk chocolate"),
    STRAWBERRY("Strawberry"),
    RASPBERRY("Raspberry"),
    EGG("Egg"),
    WATER("Water"),
    MILK("Milk"),
    SUGAR("Sugar"),
    VANILLA("Vanilla"),
    CHOCOLATE_DOUGH("Chocolate Dough"),
    CHOCOLATE_FLAVOUR("Chocolate Flavour"),
    CLASSIC_DOUGH("Classic Dough");

    private final String label;

    IngredientTag(String label) {
        this.label = label;
    }

    public String getName(){
        return label;
    }
}
