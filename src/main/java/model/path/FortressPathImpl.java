package model.path;

import model.units.ArmyUnit;
import model.units.UnitTeam;

import java.util.ArrayDeque;
import java.util.Iterator;

public class FortressPathImpl implements FortressPath  {

    //LEFT: 0 ------------- pathLength :RIGHT\\
    private final ArrayDeque<ArmyUnit> leftArmyUnits = new ArrayDeque<>();
    private final ArrayDeque<ArmyUnit> rightArmyUnits = new ArrayDeque<>();
    private final ArrayDeque<ArmyUnit> leftWaitingUnits = new ArrayDeque<>();
    private final ArrayDeque<ArmyUnit> rightWaitingUnits = new ArrayDeque<>();
    private final int pathLength;
    private final int deployDelay;
    private int leftDelayed;
    private int rightDelayed;
    private final int interspace;

    public FortressPathImpl(int pathLength, ArmyUnit leftFortressUnit, ArmyUnit rightFortressUnit, int deployDelay, int interspace) {
        if (pathLength <= 0)
            throw new IllegalArgumentException("Length of a path: " + pathLength + " is not a proper value");
        this.pathLength = pathLength;
        this.deployDelay = deployDelay;
        this.interspace = interspace;
        leftDelayed = deployDelay;
        rightDelayed = deployDelay;

        if(leftFortressUnit.getUnitTeam() != UnitTeam.LEFT || rightFortressUnit.getUnitTeam() != UnitTeam.RIGHT) {
            throw new IllegalArgumentException("Fortress units belongs to wrong teams");
        }

        addFortressUnit(leftFortressUnit);
        addFortressUnit(rightFortressUnit);
    }

    public int getPathLength(){
        return pathLength;
    }

    public void tick() {
        armyAction(UnitTeam.LEFT);
        armyAction(UnitTeam.RIGHT);
    }

    synchronized public void addUnit(ArmyUnit unit) {
        getWaitingUnits(unit.getUnitTeam()).addLast(unit);
    }

    synchronized private void addFortressUnit(ArmyUnit unit) {
        getArmyUnits(unit.getUnitTeam()).addLast(unit);
        int place = (unit.getUnitTeam() == UnitTeam.LEFT ? 0 : pathLength);
        unit.setUnitCoords(place);
    }

    void armyAction(UnitTeam team) {
        attackEnemy(team);
        moveArmy(team);
        deployUnit(team);
    }

    void attackEnemy(UnitTeam team) {
        ArrayDeque<ArmyUnit> friendlyArmy = getArmyUnits(team);
        ArrayDeque<ArmyUnit> enemyArmy = getArmyUnits(team.otherTeam());

        ArmyUnit enemyFrontUnit = enemyArmy.getFirst();

        for (ArmyUnit friendlyUnit: friendlyArmy) {
            if (friendlyUnit.enemyInRange(enemyFrontUnit)){
                friendlyUnit.attack(enemyFrontUnit);
            }
        }

        if(enemyFrontUnit.isDead() && enemyArmy.size() > 1){
            enemyArmy.removeFirst();
        }
    }

    void moveArmy(UnitTeam team) {
        ArrayDeque<ArmyUnit> friendlyArmy = getArmyUnits(team);
        ArrayDeque<ArmyUnit> enemyArmy = getArmyUnits(team.otherTeam());
        int direction = team.getDirection();

        Iterator<ArmyUnit> iterator = friendlyArmy.iterator();
        ArmyUnit frontUnit = iterator.next();
        ArmyUnit frontEnemyUnit = enemyArmy.peekFirst();

        assert frontEnemyUnit != null;
        int border = frontEnemyUnit.getUnitCoords() - (interspace) * direction;
        frontUnit.moveUnit(border);

        ArmyUnit previousUnit;
        while(iterator.hasNext()){
            previousUnit = frontUnit;
            frontUnit = iterator.next();
            border = previousUnit.getUnitCoords() - (interspace) * direction;
            frontUnit.moveUnit(border);
        }
    }

    synchronized private void deployUnit(UnitTeam team) {
        incrementDelayed(team);
        if (isDelayed(team)) {
            if (!getWaitingUnits(team).isEmpty()) {
                ArmyUnit waitingUnit = getWaitingUnits(team).peekFirst();
                assert waitingUnit != null;
                if (deployUnit(waitingUnit)) {
                    resetDelayed(team);
                    getWaitingUnits(team).pollFirst();
                }
            }
        }
    }

    synchronized private boolean deployUnit(ArmyUnit unit){
        ArrayDeque<ArmyUnit> friendlyArmy = getArmyUnits(unit.getUnitTeam());
        ArmyUnit fortressUnit = friendlyArmy.pollLast();

        int place = (unit.getUnitTeam() == UnitTeam.LEFT ? interspace : pathLength - interspace);
        int direction = unit.getUnitTeam().getDirection();
        int deployZone = place + interspace * direction;

        boolean deployed = false;

        if(friendlyArmy.peekLast() == null || friendlyArmy.peekLast().getUnitCoords() * direction >= deployZone * direction){
            unit.setUnitCoords(place);
            friendlyArmy.addLast(unit);
            deployed = true;
        }

        assert fortressUnit != null;
        friendlyArmy.addLast(fortressUnit);
        return deployed;
    }

    private boolean isDelayed(UnitTeam team) {
        return getDelayed(team) >= deployDelay;
    }

    private int getDelayed(UnitTeam team) {
        return team == UnitTeam.LEFT ? leftDelayed : rightDelayed;
    }

    private void incrementDelayed(UnitTeam team) {
        switch (team) {
            case LEFT:
                leftDelayed += (isDelayed(team) ? 0 : 1);
                break;
            case RIGHT:
                rightDelayed += (isDelayed(team) ? 0 : 1);
                break;
        }
    }

    private void resetDelayed(UnitTeam team) {
        switch (team) {
            case LEFT:
                leftDelayed = 0;
                break;
            case RIGHT:
                rightDelayed = 0;
                break;
        }
    }

    public ArrayDeque<ArmyUnit> getArmyUnits(UnitTeam team) {
        return team == UnitTeam.LEFT ? leftArmyUnits : rightArmyUnits;
    }

    private ArrayDeque<ArmyUnit> getWaitingUnits(UnitTeam team) {
        return team == UnitTeam.LEFT ? leftWaitingUnits : rightWaitingUnits;
    }
}
