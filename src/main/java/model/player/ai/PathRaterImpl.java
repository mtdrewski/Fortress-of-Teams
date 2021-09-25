package model.player.ai;

import model.path.FortressPath;
import model.units.ArmyUnit;
import model.units.UnitTeam;

import java.util.ArrayDeque;

public class PathRaterImpl implements PathRater {
    private final UnitTeam team;

    public PathRaterImpl(UnitTeam team){
        this.team = team;
    }

    public PathRating ratePath(FortressPath path) {
        long enemyRating = rateArmy(path.getArmyUnits(team.otherTeam()), path.getPathLength());
        long friendlyRating = rateArmy(path.getArmyUnits(team), path.getPathLength());
        long totalRating = friendlyRating - enemyRating;

        if(totalRating < -100000){
            return PathRating.GREAT_DANGER;
        }
        else if(totalRating < -5000){
            return PathRating.DANGER;
        }
        else if(totalRating < -1000){
            return PathRating.MINOR_DANGER;
        }
        else if(totalRating > 100000){
            return PathRating.GREAT_ADVANTAGE;
        }
        else if(totalRating > 5000){
            return PathRating.ADVANTAGE;
        }
        else if(totalRating > 1000){
            return PathRating.MINOR_ADVANTAGE;
        }
        else {
            return PathRating.DRAW;
        }
    }

    private long rateArmy(ArrayDeque<ArmyUnit> army, int pathLength){
        long armyRating = 0;
        for(ArmyUnit unit: army) {
            armyRating += rateUnit(unit, pathLength);
        }
        return armyRating;
    }

    private long rateUnit(ArmyUnit unit, int pathLength){
        long unitRating = Math.max(unit.getHP(), 1);
        unitRating *= Math.max(unit.getArmor(), 1);
        unitRating *= Math.max(unit.getDamage(), 1);
        unitRating /= Math.max(unit.getDelay(), 1);
        unitRating += Math.max(unit.getRange(), 1);

        int unitCoords = unit.getUnitCoords();
        int passedDistance = (unit.getUnitTeam() == UnitTeam.LEFT ? unitCoords : pathLength - unitCoords);
        double passedPath = ((double) passedDistance) / pathLength;
        unitRating *= 2 * (passedPath * passedPath);
        unitRating *= Math.sqrt(Math.max(unit.getSpeed(), 1));
        return unitRating;
    }
}
