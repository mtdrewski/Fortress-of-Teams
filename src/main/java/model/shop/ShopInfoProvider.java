package model.shop;

public interface ShopInfoProvider<T extends ShopEntry> {
    void performAction(T entry);
    Boolean getAvailability(T entry);
    Integer getCost(T entry);
}
