package model.units;

public interface ArmyUnit {
    int getUnitCoords();
    int getSpeed();
    int getDamage();
    int getHP();
    int getArmor();
    int getRange();
    int getDelay();
    UnitTeam getUnitTeam();
    UnitType getUnitType();
    void setUnitCoords(int unitCoords);

    void moveUnit(int border);
    boolean enemyInRange(ArmyUnit enemyUnit);
    boolean isDead();
    void attack(ArmyUnit enemy);
    void receiveDamage(int damage);
}
