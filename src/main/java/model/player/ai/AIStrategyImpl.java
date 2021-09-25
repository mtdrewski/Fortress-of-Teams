package model.player.ai;

import model.fortress.Fortress;
import model.fortress.shop.FortressActions;
import model.fortress.shop.FortressUpgrades;
import model.player.ChainExecutor;
import model.units.UnitType;
import model.units.UnitUpgrade;

public class AIStrategyImpl implements AIStrategy {
    private final Fortress fortress;
    private final ChainExecutor executor;

    public AIStrategyImpl(Fortress fortress, ChainExecutor executor) {
        this.fortress = fortress;
        this.executor = executor;
    }

    public void decide(PathRating rating) {
        switch (rating) {
            case GREAT_DANGER:
                greatDefence();
                break;
            case DANGER:
                executor.firstDo(this::defence)
                        .elseDo(this::upgradeFortress)
                        .execute();
                break;
            case MINOR_DANGER:
                executor.firstDo(this::upgradeFortress)
                        .elseDo(this::defence)
                        .execute();
                break;
            case DRAW:
                executor.firstDo(this::upgradeUnits)
                        .elseDo(this::upgradeFortress)
                        .elseDo(this::attack)
                        .execute();
                break;
            case MINOR_ADVANTAGE:
                executor.firstDo(this::upgradeUnits)
                        .elseDo(this::attack)
                        .execute();
                break;
            case ADVANTAGE:
                executor.firstDo(this::upgradeFortress)
                        .elseDo(this::heavyAttack)
                        .execute();
                break;
            case GREAT_ADVANTAGE:
                executor.firstDo(this::greatUpgrade)
                        .elseDo(this::heavyAttack)
                        .execute();
                break;
        }
    }

    private boolean greatDefence() {
        return executor.firstDo(() -> fortress.buy(UnitType.HEAVY))
                .elseDo(() -> fortress.buy(UnitType.SOLDIER))
                .elseDo(() -> fortress.buy(FortressUpgrades.DAMAGE))
                .elseDo(() -> fortress.buy(FortressUpgrades.DEFENCE))
                .elseDo(() -> getHPRatio() < 0.2
                && fortress.buy(FortressActions.LARGE_HEAL))
                .elseDo(() -> getHPRatio() < 0.5
                        && fortress.buy(FortressActions.SMALL_HEAL))
                .execute();
    }

    private boolean defence() {
        return executor.firstDo(() -> fortress.buy(UnitType.HEAVY))
                .elseDo(() -> fortress.buy(UnitType.SOLDIER))
                .elseDo(() -> getHPRatio() < 0.2
                        && fortress.buy(FortressActions.LARGE_HEAL))
                .elseDo(() -> getHPRatio() < 0.5
                        && fortress.buy(FortressActions.SMALL_HEAL))
                .execute();
    }

    private boolean greatUpgrade() {
        if (fortress.getUpgradeLevel(FortressUpgrades.INCOME) < 3) {
            return fortress.buy(FortressUpgrades.INCOME);
        }
        return executor.firstDo(this::upgradeFortress)
                .elseDo(this::upgradeUnits)
                .execute();
    }

    private boolean upgradeUnits() {
        return executor.firstDo(() -> fortress.buy(UnitUpgrade.SOLDIER))
                .elseDo(() -> fortress.buy(UnitUpgrade.HEAVY))
                .elseDo(() -> fortress.buy(UnitUpgrade.SNIPER))
                .execute();
    }

    private boolean upgradeFortress() {
        if (fortress.getUpgradeLevel(FortressUpgrades.INCOME) < 2) {
            return fortress.buy(FortressUpgrades.INCOME);
        }
        return executor.firstDo(() -> fortress.buy(FortressUpgrades.INCOME))
                .elseDo(() -> getHPRatio() < 0.75
                && fortress.buy(FortressActions.LARGE_HEAL))
                .elseDo(() -> getHPRatio() < 0.75
                        && fortress.buy(FortressActions.SMALL_HEAL))
                .elseDo(() -> fortress.buy(FortressUpgrades.DAMAGE))
                .elseDo(() -> fortress.buy(FortressUpgrades.RANGE))
                .elseDo(() -> fortress.buy(FortressUpgrades.DEFENCE))
                .execute();
    }

    private boolean heavyAttack() {
        if (fortress.getMoney() >= fortress.getCost(UnitType.HEAVY) + fortress.getCost(UnitType.SNIPER)) {
            fortress.buy(UnitType.HEAVY);
            fortress.buy(UnitType.SNIPER);
            return true;
        }
        return false;
    }

    private boolean attack() {
        if (fortress.getUnitLevel(UnitType.SOLDIER) > 1 && fortress.getUnitLevel(UnitType.HEAVY) > 1 && fortress.getUnitLevel(UnitType.SNIPER) > 1) {
            return executor.firstDo(this::heavyAttack)
                    .elseDo(() -> fortress.buy(UnitType.SOLDIER))
                    .elseDo(() -> fortress.buy(UnitType.HEAVY))
                    .execute();
        }
        return false;
    }

    private double getHPRatio() {
        return (double) fortress.getHP() / fortress.getMaxHP();
    }
}
