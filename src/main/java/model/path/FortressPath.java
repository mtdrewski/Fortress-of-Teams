package model.path;

import clock.Tickable;
import model.units.ArmyUnit;
import model.units.UnitTeam;

import java.util.ArrayDeque;

public interface FortressPath extends Tickable {
    void addUnit(ArmyUnit unit);
    ArrayDeque<ArmyUnit> getArmyUnits(UnitTeam team);
    int getPathLength();
}
