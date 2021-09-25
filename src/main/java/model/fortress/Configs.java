package model.fortress;

import model.fortress.shop.FortressUpgrade;
import model.fortress.shop.providers.FortressActionsShop;
import model.fortress.shop.providers.FortressUpgradeAction;
import model.fortress.shop.providers.UnitShop;
import model.shop.upgrade.UpgradesShop;
import model.units.UnitUpgrade;

public interface Configs {
    UnitShop.InfoProvider getUnitShopInfo();
    UpgradesShop.InfoProvider<UnitUpgrade> getUnitUpgradesInfo();
    UpgradesShop.InfoProvider<FortressUpgrade> getFortressUpgradesInfo();
    FortressActionsShop.InfoProvider getFortressActionsInfo();
    FortressUpgradeAction.InfoProvider getUpgradeActionInfo();
}
