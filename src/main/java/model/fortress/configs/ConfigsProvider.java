package model.fortress.configs;

import model.fortress.Configs;
import model.fortress.shop.FortressUpgrade;
import model.fortress.shop.providers.FortressActionsShop;
import model.fortress.shop.providers.FortressUpgradeAction;
import model.fortress.shop.providers.UnitShop;
import model.shop.Money;
import model.shop.MoneyImpl;
import model.shop.upgrade.UpgradesShop;
import model.units.*;

public class ConfigsProvider implements Configs, FortressMoneyFactory, FortressStatisticsFactory {
    private final FortressActionsShopProvider fortressActionsShopProvider;
    private final FortressUpgradesShopProvider fortressUpgradesShopProvider;
    private final UnitShopProvider unitShopProvider;
    private final int money, initialHp;
    public ConfigsProvider(FortressActionsShopProvider fortressActionsShopProvider, FortressUpgradesShopProvider fortressUpgradesShopProvider, UnitShopProvider unitShopProvider, int money, int initialHp) {
        this.fortressActionsShopProvider = fortressActionsShopProvider;
        this.fortressUpgradesShopProvider = fortressUpgradesShopProvider;
        this.unitShopProvider = unitShopProvider;
        this.money = money;
        this.initialHp = initialHp;
    }

    @Override
    public UnitShop.InfoProvider getUnitShopInfo() {
        return unitShopProvider;
    }

    @Override
    public UpgradesShop.InfoProvider<UnitUpgrade> getUnitUpgradesInfo() {
        return unitShopProvider;
    }

    @Override
    public UpgradesShop.InfoProvider<FortressUpgrade> getFortressUpgradesInfo() {
        return fortressUpgradesShopProvider;
    }

    @Override
    public FortressActionsShop.InfoProvider getFortressActionsInfo() {
        return fortressActionsShopProvider;
    }

    @Override
    public FortressUpgradeAction.InfoProvider getUpgradeActionInfo() {
        return fortressUpgradesShopProvider;
    }

    @Override
    public Money createFortressMoney() {
        return new MoneyImpl(money, 0, 10);
    }

    @Override
    public FortressStatistics createFortressStatistics(UnitTeam team) {
        return new StatisticsClass(team, initialHp,initialHp,0,0,0,0,10,UnitType.FORTRESS);
    }
}
