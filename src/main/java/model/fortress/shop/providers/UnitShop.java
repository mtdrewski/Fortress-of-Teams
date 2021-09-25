package model.fortress.shop.providers;

import model.shop.ShopInfoProvider;
import model.shop.upgrade.UpgradeInfo;
import model.units.UnitStatistics;
import model.units.UnitTeam;
import model.units.UnitType;
import model.units.UnitUpgrade;

import java.util.function.Function;

public class UnitShop implements ShopInfoProvider<UnitType> {
    public interface InfoProvider{
        int getCostPerLevel(UnitType type, int level);
        Function<UnitTeam, UnitStatistics> createStatisticsForUnit(UnitType type, int level);
    }

    private final UpgradeInfo<UnitUpgrade> upgradesInfo;
    private final InfoProvider provider;
    private final Deployer deployer;

    public UnitShop(UpgradeInfo<UnitUpgrade> upgradesInfo, InfoProvider provider, Deployer deployer) {
        this.upgradesInfo = upgradesInfo;
        this.provider = provider;
        this.deployer = deployer;
    }

    @Override
    public void performAction(UnitType type) {
        deployer.deployUnitsWithStatistics(provider.createStatisticsForUnit(type, upgradesInfo.getLevel(type.upgrade())));
    }

    @Override
    public Boolean getAvailability(UnitType type) {
        return true;
    }

    @Override
    public Integer getCost(UnitType type) {
        return provider.getCostPerLevel(type, upgradesInfo.getLevel(type.upgrade()));
    }
}
