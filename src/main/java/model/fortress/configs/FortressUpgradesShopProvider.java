package model.fortress.configs;

import model.fortress.shop.FortressUpgrade;
import model.fortress.shop.FortressUpgrades;
import model.fortress.shop.providers.FortressUpgradeAction;
import model.shop.upgrade.UpgradesShop;

import java.util.HashMap;
import java.util.Map;

public enum FortressUpgradesShopProvider implements UpgradesShop.InfoProvider<FortressUpgrade>, FortressUpgradeAction.InfoProvider {
    EASY, HARD;
    static {
        FortressUpgrade u;

        //----EASY----

        u = FortressUpgrades.DAMAGE;
        EASY.setMaxLevel(u, 3);
        EASY.config(u, 1, 40, 200);
        EASY.config(u, 2, 60, 400);
        EASY.config(u, 3, 80, 0);

        u = FortressUpgrades.DEFENCE;
        EASY.setMaxLevel(u, 3);
        EASY.config(u, 1, 10, 200);
        EASY.config(u, 2, 20, 400);
        EASY.config(u, 3, 30, 0);

        u = FortressUpgrades.RANGE;
        EASY.setMaxLevel(u, 3);
        EASY.config(u, 1, 100, 100);
        EASY.config(u, 2, 150, 300);
        EASY.config(u, 3, 200, 0);

        u = FortressUpgrades.INCOME;
        EASY.setMaxLevel(u, 3);
        EASY.config(u, 1, 10, 500);
        EASY.config(u, 2, 20, 2000);
        EASY.config(u, 3, 40, 0);

        //----HARD----

        u = FortressUpgrades.DAMAGE;
        HARD.setMaxLevel(u, 3);
        HARD.config(u, 1, 40, 300);
        HARD.config(u, 2, 60, 600);
        HARD.config(u, 3, 80, 0);

        u = FortressUpgrades.DEFENCE;
        HARD.setMaxLevel(u, 3);
        HARD.config(u, 1, 10, 300);
        HARD.config(u, 2, 20, 600);
        HARD.config(u, 3, 30, 0);

        u = FortressUpgrades.RANGE;
        HARD.setMaxLevel(u, 3);
        HARD.config(u, 1, 100, 150);
        HARD.config(u, 2, 150, 450);
        HARD.config(u, 3, 200, 0);

        u = FortressUpgrades.INCOME;
        HARD.setMaxLevel(u, 3);
        HARD.config(u, 1, 10, 750);
        HARD.config(u, 2, 20, 3000);
        HARD.config(u, 3, 40, 0);

    }
    private static class LevelConfig{
        private int amount, costOfNext;

        public void set(int amount, int costOfNext){
            this.amount = amount;
            this.costOfNext = costOfNext;
        }

        public int getAmount() {
            return amount;
        }

        public int getCostOfNext() {
            return costOfNext;
        }
    }
    private final Map<FortressUpgrade, LevelConfig[]> map = new HashMap<>();
    @Override
    public int getAmountPerLevel(FortressUpgrade upgrade, int level) {
        return map.get(upgrade)[level-1].getAmount();
    }

    @Override
    public int getCostOfNextLevel(FortressUpgrade entry, int current_level) {
        return map.get(entry)[current_level-1].getCostOfNext();
    }

    @Override
    public int getMaxLevel(FortressUpgrade entry) {
        return map.get(entry).length;
    }
    private void setMaxLevel(FortressUpgrade entry, int val){
        map.put(entry, new LevelConfig[val]);
        for(int i=0;i<val;i++)
            map.get(entry)[i]= new LevelConfig();
    }

    private void config(FortressUpgrade entry, int level, int amount, int costOfNext){
        map.get(entry)[level-1].set(amount, costOfNext);
    }
}
