package model.fortress.configs;

import model.fortress.shop.FortressAction;
import model.fortress.shop.FortressActions;
import model.fortress.shop.providers.FortressActionsShop;

import java.util.HashMap;
import java.util.Map;

public enum FortressActionsShopProvider implements FortressActionsShop.InfoProvider {
    EASY,HARD;
    static {
        EASY.put(FortressActions.LARGE_HEAL, 100, 1000);
        EASY.put(FortressActions.SMALL_HEAL, 50,600);

        HARD.put(FortressActions.LARGE_HEAL, 100, 1500);
        HARD.put(FortressActions.SMALL_HEAL, 50,900);
    }

    private final Map<FortressAction, Integer> amountMap = new HashMap<>();
    private final Map<FortressAction, Integer> costMap=new HashMap<>();

    @Override
    public int getAmount(FortressAction a) {
        return amountMap.get(a);
    }

    @Override
    public int getCost(FortressAction a) {
        return costMap.get(a);
    }
    private void put(FortressAction a, int amount, int cost){
        amountMap.put(a, amount);
        costMap.put(a, cost);
    }
}
