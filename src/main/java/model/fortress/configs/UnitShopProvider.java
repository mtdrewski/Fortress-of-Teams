package model.fortress.configs;

import model.fortress.shop.providers.UnitShop;
import model.shop.upgrade.UpgradesShop;
import model.units.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public enum UnitShopProvider implements UpgradesShop.InfoProvider<UnitUpgrade>, UnitShop.InfoProvider {
    EASY, HARD;

    static {
        UnitType t;

        //----EASY----

        t = UnitType.HEAVY;
        EASY.setMaxLevel(t, 3);
        EASY.config(t, 1).setCosts(400, 800);
        EASY.config(t, 2).setCosts(500, 1000);
        EASY.config(t, 3).setCosts(600, 0);
        EASY.config(t, 1).setStats(400, 50, 30, 30, 8, 30);
        EASY.config(t, 2).setStats(450, 60, 30, 40, 9, 25);
        EASY.config(t, 3).setStats(500, 70, 30, 50, 10, 20);

        t = UnitType.SOLDIER;
        EASY.setMaxLevel(t, 3);
        EASY.config(t, 1).setCosts(200, 400);
        EASY.config(t, 2).setCosts(250, 500);
        EASY.config(t, 3).setCosts(300, 0);
        EASY.config(t, 1).setStats(200, 40, 40, 20, 10, 20);
        EASY.config(t, 2).setStats(250, 50, 45, 25, 11, 19);
        EASY.config(t, 3).setStats(300, 60, 55, 30, 12, 18);

        t = UnitType.SNIPER;
        EASY.setMaxLevel(t, 3);
        EASY.config(t, 1).setCosts(300, 600);
        EASY.config(t, 2).setCosts(350, 700);
        EASY.config(t, 3).setCosts(450, 0);
        EASY.config(t, 1).setStats(100, 80, 55, 10, 10, 30);
        EASY.config(t, 2).setStats(150, 100, 70, 12, 10, 30);
        EASY.config(t, 3).setStats(200, 120, 90, 14, 10, 30);

        //----HARD----

        t = UnitType.HEAVY;
        HARD.setMaxLevel(t, 3);
        HARD.config(t, 1).setCosts(600, 1200);
        HARD.config(t, 2).setCosts(750, 1500);
        HARD.config(t, 3).setCosts(900, 0);
        HARD.config(t, 1).setStats(400, 50, 30, 30, 8, 30);
        HARD.config(t, 2).setStats(450, 60, 30, 40, 9, 25);
        HARD.config(t, 3).setStats(500, 70, 30, 50, 10, 20);

        t = UnitType.SOLDIER;
        HARD.setMaxLevel(t, 3);
        HARD.config(t, 1).setCosts(300, 600);
        HARD.config(t, 2).setCosts(375, 750);
        HARD.config(t, 3).setCosts(400, 0);
        HARD.config(t, 1).setStats(200, 40, 40, 20, 10, 20);
        HARD.config(t, 2).setStats(250, 50, 45, 25, 11, 19);
        HARD.config(t, 3).setStats(300, 60, 55, 30, 12, 18);

        t = UnitType.SNIPER;
        HARD.setMaxLevel(t, 3);
        HARD.config(t, 1).setCosts(450, 900);
        HARD.config(t, 2).setCosts(525, 1050);
        HARD.config(t, 3).setCosts(675, 0);
        HARD.config(t, 1).setStats(100, 80, 55, 10, 10, 30);
        HARD.config(t, 2).setStats(150, 100, 70, 12, 10, 30);
        HARD.config(t, 3).setStats(200, 120, 90, 14, 10, 30);
    }

    private static class LevelConfig{
        private int cost, costOfNext, hpMax, damage, range,armor,speed, delay;
        public void setCosts(int cost , int costOfNext){
            this.cost = cost;
            this.costOfNext = costOfNext;
        }
        public void setStats(int hpMax, int damage, int range, int armor, int speed, int delay) {
            this.hpMax = hpMax;
            this.damage = damage;
            this.range = range;
            this.armor = armor;
            this.speed = speed;
            this.delay = delay;
        }

        public int getCost() {
            return cost;
        }

        public int getCostOfNext() {
            return costOfNext;
        }

        public UnitStatistics createStats(UnitTeam team, UnitType type){
            return new StatisticsClass(team, hpMax, hpMax, damage, range, speed, armor, delay, type);
        }
    }
    private final Map<UnitUpgrade, LevelConfig[]> map = new HashMap<>();

    @Override
    public int getCostOfNextLevel(UnitUpgrade entry, int current_level) {
        return map.get(entry)[current_level-1].getCostOfNext();
    }

    @Override
    public int getMaxLevel(UnitUpgrade entry) {
        return map.get(entry).length;
    }

    @Override
    public int getCostPerLevel(UnitType type, int level) {
        return map.get(type.upgrade())[level-1].getCost();
    }

    @Override
    public Function<UnitTeam,UnitStatistics> createStatisticsForUnit(UnitType type, int level) {
        return team->map.get(type.upgrade())[level-1].createStats(team, type);
    }

    private void setMaxLevel(UnitType t, int val){
        map.put(t.upgrade(), new LevelConfig[val]);
        for(int i=0;i<val;i++)
            map.get(t.upgrade())[i]= new LevelConfig();
    }
    private LevelConfig config(UnitType t, int level){
        return map.get(t.upgrade())[level-1];
    }
}
