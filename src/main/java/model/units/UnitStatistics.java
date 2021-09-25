package model.units;

public interface UnitStatistics {
    int getSpeed();
    int getRange();
    int getArmor();
    int getHP();
    void setHP(int hp);
    int getMaxHP();
    int getDamage();
    UnitTeam getUnitTeam();
    int getDelay();
    UnitType getType();
}
