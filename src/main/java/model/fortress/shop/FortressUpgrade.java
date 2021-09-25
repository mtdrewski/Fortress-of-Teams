package model.fortress.shop;

import model.shop.Money;
import model.shop.ShopEntry;
import model.units.FortressStatistics;

public interface FortressUpgrade extends ShopEntry {
    void upgradeFortress(FortressStatistics f, Money m, int amount);
}
