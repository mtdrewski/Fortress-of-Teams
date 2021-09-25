package model.fortress.shop;

import model.units.FortressStatistics;

public enum FortressActions implements FortressAction{
    LARGE_HEAL("Restore a big amount of HP"){
        @Override
        public void makeFortressAction(FortressStatistics fortressStatistics, Integer amount) {
            fortressStatistics.setHP(fortressStatistics.getHP()+amount);
        }
    },
    SMALL_HEAL("Restore a small amount of HP"){
        @Override
        public void makeFortressAction(FortressStatistics fortressStatistics, Integer amount) {
            fortressStatistics.setHP(fortressStatistics.getHP()+amount);
        }
    };
    private final String description;

    FortressActions(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
