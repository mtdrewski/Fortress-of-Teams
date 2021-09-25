package model.shop.upgrade;

import model.shop.ShopEntry;
import model.shop.ShopInfoProvider;

import java.util.HashMap;
import java.util.Map;

public class UpgradesShop<T extends ShopEntry> implements ShopInfoProvider<T>, UpgradeInfo<T> {
    public interface InfoProvider<T extends ShopEntry>{
        int getCostOfNextLevel(T entry, int current_level);
        int getMaxLevel(T entry);
    }
    public interface ActionPerLevelProvider<T extends ShopEntry>{
        void performAction(T entry, int level);
    }
    private final InfoProvider<T> provider;
    private final ActionPerLevelProvider<T> actionMaker;
    private final Map<T, Integer> currentLevels = new HashMap<>();

    public UpgradesShop(InfoProvider<T> provider, ActionPerLevelProvider<T> actionMaker) {
        this.provider = provider;
        this.actionMaker = actionMaker;
    }

    @Override
    public void performAction(T entry){
        upgrade(entry);
        actionMaker.performAction(entry, getLevel(entry));
    }
    @Override
    public Boolean getAvailability(T entry){
        return getLevel(entry) < provider.getMaxLevel(entry);
    }
    @Override
    public Integer getCost(T entry){
        return provider.getCostOfNextLevel(entry, getLevel(entry));
    }
    @Override
    public int getLevel(T e) {
        if (!currentLevels.containsKey(e)) currentLevels.put(e, 1);
        return currentLevels.get(e);
    }

    @Override
    public int getMaxLevel(T entry) {
        return provider.getMaxLevel(entry);
    }

    private void upgrade(T e){
        currentLevels.put(e, getLevel(e)+1);
    }

    public void initialize(T[] entrys){
        for(T entry : entrys){
            actionMaker.performAction(entry, 1);
        }
    }
}
