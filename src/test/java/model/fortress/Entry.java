package model.fortress;

import model.shop.ShopEntry;

public enum Entry implements ShopEntry {
    CAT, DOG, BIRD;

    @Override
    public String getDescription() {
        return "";
    }
}
