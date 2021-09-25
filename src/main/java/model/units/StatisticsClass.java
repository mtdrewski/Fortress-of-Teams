package model.units;

public class StatisticsClass implements FortressStatistics {
    model.units.UnitTeam unitTeam;

    int HP, maxHP, damage, range, speed, armor, delay;
    private final UnitType type;
    public StatisticsClass(UnitTeam unitTeam, int maxHP, int HP, int damage, int range, int speed, int armor, int delay, UnitType type) {
        this.unitTeam = unitTeam;
        this.damage = damage;
        this.range = range;
        this.speed = speed;
        this.armor = armor;
        this.maxHP = maxHP;
        this.type = type;
        setHP(HP);
        this.delay = delay;
    }

    public int getSpeed() {
        return speed;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = Integer.min(HP, getMaxHP());
    }

    public model.units.UnitTeam getUnitTeam() {
        return unitTeam;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
        setHP(getHP());
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public UnitType getType() {
        return type;
    }
    @Override
    public String toString(){
        return unitTeam.name()+" "
                +type.name()
                +" [ HP: "+ HP +"/"+ maxHP
                +", d:"+damage
                +", r:"+range
                +", s:"+speed
                +", a:"+armor
                +", d:"+delay
                +"]";
    }
}
