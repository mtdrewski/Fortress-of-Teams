package model.units;

public class ArmyUnitImpl implements ArmyUnit {

    private Integer unitCoords = null;
    private final UnitStatistics stats;
    private int delayed;
    private boolean attacked = false;

    public ArmyUnitImpl(UnitStatistics statistics) {
        this.stats = statistics;
        delayed = statistics.getDelay();
    }

    public int getUnitCoords() {
        if(unitCoords == null){
            throw new RuntimeException("First need to set unit's coords");
        }
        return unitCoords;
    }

    public int getSpeed() {
        return stats.getSpeed();
    }

    public int getDamage() {
        return stats.getDamage();
    }

    public int getHP() {
        return stats.getHP();
    }

    public int getArmor() {
        return stats.getArmor();
    }

    public int getRange() {
        return stats.getRange();
    }

    public int getDelay() {
        return stats.getDelay();
    }

    public UnitTeam getUnitTeam() {
        return stats.getUnitTeam();
    }

    public UnitType getUnitType() {
        return stats.getType();
    }

    public void setUnitCoords(int unitCoords) {
        this.unitCoords = unitCoords;
    }

    public void moveUnit(int border) {
        if (!attacked) {
            incrementDelay();
            int newCoords = unitCoords + getSpeed() * getUnitTeam().getDirection();
            unitCoords = (getUnitTeam().getDirection() > 0 ? Math.min(newCoords, border) : Math.max(newCoords, border));
        }
        attacked = false;
    }

    public boolean enemyInRange(ArmyUnit enemyUnit) {
        return Math.abs(this.getUnitCoords() - enemyUnit.getUnitCoords()) <= stats.getRange();
    }

    public boolean isDead() {
        return stats.getHP() <= 0;
    }

    public void attack(ArmyUnit enemy){
        incrementDelay();
        if (hadWaited()) {
            resetDelay();
            enemy.receiveDamage(getDamage());
            attacked = true;
        }
    }

    private void incrementDelay() {
        delayed += (delayed > getDelay() ? 0 : 1);
    }

    private void resetDelay() {
        delayed = 0;
    }

    private boolean hadWaited() {
        return delayed >= getDelay();
    }

    public void receiveDamage(int damage) {
        stats.setHP(stats.getHP() - Math.max(damage - stats.getArmor(), 1));
    }
}
