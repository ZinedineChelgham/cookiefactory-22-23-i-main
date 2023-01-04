package fr.unice.model;

import fr.unice.enums.IngredientType;

import java.util.Objects;

/**
 * Contains all information of an ingredient
 */
public final class Ingredient {
    private String name;
    private double price;
    private IngredientType type;
    private boolean isAvailable = true;

    /**
     *
     */
    public Ingredient(String name, double price, IngredientType type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public String name() {
        return name;
    }

    public double price() {
        return price;
    }

    public IngredientType type() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setType(IngredientType type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailability(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Double.compare(that.price, price) == 0 && isAvailable == that.isAvailable && Objects.equals(name, that.name) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, type, isAvailable);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", type=" + type +
                ", isAvailable=" + isAvailable +
                '}';
    }
}

