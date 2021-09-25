package model.units;

public interface FortressStatistics extends UnitStatistics{
    void setDamage(int damage);
    void setRange(int range);
    void setArmor(int armor);
    void setMaxHP(int maxHP);
    void setDelay(int delay);
}