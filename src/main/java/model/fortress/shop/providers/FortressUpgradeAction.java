package model.fortress.shop.providers;

import model.fortress.shop.FortressUpgrade;
import model.shop.Money;
import model.shop.upgrade.UpgradesShop;
import model.units.FortressStatistics;

public class FortressUpgradeAction implements UpgradesShop.ActionPerLevelProvider<FortressUpgrade> {

    public interface InfoProvider{
        int getAmountPerLevel(FortressUpgrade upgrade, int level);
    }
    private final FortressStatistics stats;
    private final Money money;
    private final InfoProvider provider;

    public FortressUpgradeAction(FortressStatistics stats, Money money, InfoProvider provider) {
        this.stats = stats;
        this.money = money;
        this.provider = provider;
    }

    @Override
    public void performAction(FortressUpgrade fortressUpgrade, int level) {
        fortressUpgrade.upgradeFortress(stats,money, provider.getAmountPerLevel(fortressUpgrade, level));
    }
}
