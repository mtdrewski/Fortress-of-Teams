package model.shop;

public interface Shop<T extends ShopEntry> {
    int getCost(T entry);
    boolean isAffordable(T entry);
    boolean isAvailable(T entry);
    boolean buy(T entry);
}
