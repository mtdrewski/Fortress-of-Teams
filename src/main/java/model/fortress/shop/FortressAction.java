package model.fortress.shop;

import model.shop.ShopEntry;
import model.units.FortressStatistics;

public interface FortressAction extends ShopEntry {
    void makeFortressAction(FortressStatistics fortressStatistics, Integer amount);
}
