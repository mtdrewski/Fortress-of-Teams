package model.fortress.shop;

import model.shop.Money;
import model.units.FortressStatistics;


public enum FortressUpgrades implements FortressUpgrade {
    DAMAGE("Increase turret's damage") {
        @Override
        public void upgradeFortress(FortressStatistics f, Money m, int amount) {
            f.setDamage(amount);
        }
    },
    RANGE("Increase turret's range"){
        @Override
        public void upgradeFortress(FortressStatistics f, Money m, int amount) {
            f.setRange(amount);
        }
    },
    DEFENCE("Increase fortress' armor"){
        @Override
        public void upgradeFortress(FortressStatistics f, Money m, int amount) {
            f.setArmor(amount);
        }
    },
    INCOME("Increase passive income"){
        @Override
        public void upgradeFortress(FortressStatistics f, Money m, int amount) {
            m.setIncomePerTU(amount);
        }
    };

    private final String description;

    FortressUpgrades(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
