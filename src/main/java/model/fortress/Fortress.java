package model.fortress;

import model.fortress.shop.FortressAction;
import model.fortress.shop.FortressUpgrade;
import model.path.FortressPath;
import model.units.ArmyUnit;
import model.units.UnitTeam;
import model.units.UnitType;
import model.units.UnitUpgrade;

import java.util.ArrayDeque;

public interface Fortress {
    int getHP();
    int getArmor();
    int getRange();
    int getDamage();
    int getMaxHP();
    UnitTeam getTeam();

    FortressPath getPath();

    ArrayDeque<ArmyUnit> getArmyUnits();

    int getMoney();
    int getIncomePerTU();
    int getIncomeTU();

    boolean buy(FortressAction action);
    boolean isAffordable(FortressAction action);
    boolean isAvailable(FortressAction action);
    int getCost(FortressAction action);

    boolean buy(FortressUpgrade upgrade);
    boolean isAffordable(FortressUpgrade upgrade);
    boolean isAvailable(FortressUpgrade upgrade);
    int getCost(FortressUpgrade upgrade);

    boolean buy(UnitType type);
    boolean isAffordable(UnitType type);
    boolean isAvailable(UnitType type);
    int getCost(UnitType type);

    boolean buy(UnitUpgrade upgrade);
    boolean isAffordable(UnitUpgrade upgrade);
    boolean isAvailable(UnitUpgrade upgrade);
    int getCost(UnitUpgrade upgrade);

    int getUnitLevel(UnitType t);
    int getUnitMaxLevel(UnitType t);
    int getUpgradeLevel(FortressUpgrade t);
    int getUpgradeMaxLevel(FortressUpgrade t);
}
