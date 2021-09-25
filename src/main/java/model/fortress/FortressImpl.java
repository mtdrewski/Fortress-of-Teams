package model.fortress;

import model.fortress.shop.FortressAction;
import model.fortress.shop.FortressUpgrade;
import model.path.FortressPath;
import model.shop.Money;
import model.shop.Shop;
import model.shop.upgrade.UpgradeInfo;
import model.units.*;

import java.util.ArrayDeque;

public class FortressImpl implements Fortress {
    private final Money money;
    private final UnitStatistics health;
    private final Shop<UnitType> unitShop;
    private final Shop<UnitUpgrade> unitUpgradesShop;
    private final Shop<FortressUpgrade> fortressUpgradesShop;
    private final Shop<FortressAction> fortressActionsShop;
    private final FortressPath path;
    private final UpgradeInfo<UnitUpgrade> unitUpgradesInfo;
    private final UpgradeInfo<FortressUpgrade> fortressUpgradesInfo;

    public FortressImpl(
            Money money,
            UnitStatistics health,
            Shop<UnitType> unitShop,
            Shop<UnitUpgrade> unitUpgradesShop,
            Shop<FortressUpgrade> fortressUpgradesShop,
            Shop<FortressAction> fortressActionsShop,
            FortressPath path,
            UpgradeInfo<UnitUpgrade> unitUpgradesInfo,
            UpgradeInfo<FortressUpgrade> fortressUpgradesInfo
    ) {
        this.money = money;
        this.health = health;
        this.unitShop = unitShop;
        this.unitUpgradesShop = unitUpgradesShop;
        this.fortressUpgradesShop = fortressUpgradesShop;
        this.fortressActionsShop = fortressActionsShop;
        this.path = path;
        this.unitUpgradesInfo = unitUpgradesInfo;
        this.fortressUpgradesInfo = fortressUpgradesInfo;
    }

    @Override
    public int getHP() {
        return health.getHP();
    }

    @Override
    public int getArmor() {
        return health.getArmor();
    }

    @Override
    public int getRange() {
        return health.getRange();
    }

    @Override
    public int getDamage() {
        return health.getDamage();
    }

    @Override
    public int getMaxHP() {
        return health.getMaxHP();
    }

    @Override
    public int getMoney() {
        return money.getMoney();
    }

    @Override
    public int getIncomePerTU() {
        return money.getIncomePerTU();
    }

    @Override
    public int getIncomeTU() {
        return money.getTU();
    }

    @Override
    public UnitTeam getTeam() {
        return health.getUnitTeam();
    }

    @Override
    public FortressPath getPath(){
        return path;
    }

    @Override
    public ArrayDeque<ArmyUnit> getArmyUnits() {
        return path.getArmyUnits(health.getUnitTeam());
    }

    @Override
    public boolean buy(FortressAction action) {
        return fortressActionsShop.buy(action);
    }

    @Override
    public boolean isAffordable(FortressAction action) {
        return fortressActionsShop.isAffordable(action);
    }

    @Override
    public boolean isAvailable(FortressAction action) {
        return fortressActionsShop.isAvailable(action);
    }

    @Override
    public int getCost(FortressAction action) {
        return fortressActionsShop.getCost(action);
    }

    @Override
    public boolean buy(FortressUpgrade action) {
        return fortressUpgradesShop.buy(action);
    }

    @Override
    public boolean isAffordable(FortressUpgrade action) {
        return fortressUpgradesShop.isAffordable(action);
    }

    @Override
    public boolean isAvailable(FortressUpgrade action) {
        return fortressUpgradesShop.isAvailable(action);
    }

    @Override
    public int getCost(FortressUpgrade action) {
        return fortressUpgradesShop.getCost(action);
    }

    @Override
    public boolean buy(UnitType action) {
        return unitShop.buy(action);
    }

    @Override
    public boolean isAffordable(UnitType action) {
        return unitShop.isAffordable(action);
    }

    @Override
    public boolean isAvailable(UnitType action) {
        return unitShop.isAvailable(action);
    }

    @Override
    public int getCost(UnitType action) {
        return unitShop.getCost(action);
    }

    @Override
    public boolean buy(UnitUpgrade action) {
        return unitUpgradesShop.buy(action);
    }

    @Override
    public boolean isAffordable(UnitUpgrade action) {
        return unitUpgradesShop.isAffordable(action);
    }

    @Override
    public boolean isAvailable(UnitUpgrade action) {
        return unitUpgradesShop.isAvailable(action);
    }

    @Override
    public int getCost(UnitUpgrade action) {
        return unitUpgradesShop.getCost(action);
    }

    public int getUnitLevel(UnitType t){
        return unitUpgradesInfo.getLevel(t.upgrade());
    }
    public int getUnitMaxLevel(UnitType t){
        return unitUpgradesInfo.getMaxLevel(t.upgrade());
    }
    public int getUpgradeLevel(FortressUpgrade t){
        return fortressUpgradesInfo.getLevel(t);
    }
    public int getUpgradeMaxLevel(FortressUpgrade t){
        return fortressUpgradesInfo.getMaxLevel(t);
    }
}
