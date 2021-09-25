package model.units;

import model.shop.ShopEntry;

public enum UnitUpgrade implements ShopEntry {
    SOLDIER,HEAVY,SNIPER;

    @Override
    public String getDescription() {
        return this.name().substring(0,1).toUpperCase()
                +this.name().substring(1).toLowerCase()
                +" upgrade";
    }
}
