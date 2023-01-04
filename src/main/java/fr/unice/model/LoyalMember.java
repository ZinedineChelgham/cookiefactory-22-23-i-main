package fr.unice.model;


public class LoyalMember extends User {
    private int cookiesSinceLastLoyalty;

    public LoyalMember(String mail, String phoneNumber, int cookiesSinceLastLoyalty) {
        super(mail, phoneNumber);
        this.cookiesSinceLastLoyalty = cookiesSinceLastLoyalty;
    }

    public LoyalMember(User user){
        super(user.getMail(), user.getMail());
        this.cookiesSinceLastLoyalty = 0;
    }


    public boolean canUseDiscount(){
        return cookiesSinceLastLoyalty >= 30;
    }

    public void resetCookiesSinceLastLoyalty(){
        cookiesSinceLastLoyalty = 0;
    }

    public void incrementCookiesNumberBy(int cookies) {
        cookiesSinceLastLoyalty += cookies;
    }

    public void setCookiesSinceLastLoyalty(int cookiesSinceLastLoyalty) {
        this.cookiesSinceLastLoyalty = cookiesSinceLastLoyalty;
    }

    public int getCookiesSinceLastLoyalty() {
        return cookiesSinceLastLoyalty;
    }

    @Override
    public String getMail() {
        return super.getMail();
    }
}

