package model.units;

import model.shop.ShopEntry;

public enum UnitType implements ShopEntry {
    FORTRESS,
    SOLDIER{
        public String getHatAddress(){return "/view/images/SoldierHat.png";}
    },
    SNIPER{
        public String getHatAddress(){return "/view/images/SniperHat.png";}
    },
    HEAVY{
        public String getHatAddress(){return "/view/images/HeavyHat.png";}
    };

    public String getHatAddress(){return null;}

    @Override
    public String getDescription() {
        return this.name().substring(0,1).toUpperCase()
                +this.name().substring(1).toLowerCase()
                +" unit";
    }
    public UnitUpgrade upgrade() {return UnitUpgrade.valueOf(this.name());}
}
