package model.shop;

public class ShopImpl<T extends ShopEntry> implements Shop<T> {
    private final Money fortressMoney;
    private final ShopInfoProvider<T> provider;

    public ShopImpl(Money fortressMoney, ShopInfoProvider<T> provider) {
        this.fortressMoney = fortressMoney;
        this.provider = provider;
    }
    @Override
    public int getCost(T e) {
        return provider.getCost(e);
    }
    @Override
    public boolean isAvailable(T e) {
        return provider.getAvailability(e);
    }
    @Override
    public boolean isAffordable(T e){
        return fortressMoney.getMoney() >= getCost(e);
    }
    @Override
    public boolean buy(T e){
        if (!isAvailable(e)) return false;
        if (!fortressMoney.charge(getCost(e))) return false;
        provider.performAction(e);
        return true;
    }
}
