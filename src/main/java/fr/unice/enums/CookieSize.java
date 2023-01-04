package fr.unice.enums;

public enum CookieSize {
    Normal(1),
    L(4),
    XL(5),
    XXL(6);

    private int multiplier;

    CookieSize(int multiplier){
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }
}
