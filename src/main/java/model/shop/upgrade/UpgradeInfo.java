package model.shop.upgrade;

import model.shop.ShopEntry;

public interface UpgradeInfo<T extends ShopEntry> {
    int getLevel(T entry);
    int getMaxLevel(T entry);
}
