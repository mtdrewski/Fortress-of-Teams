package model.player.ai;

import model.path.FortressPath;
import model.units.ArmyUnit;
import model.units.UnitTeam;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PathRaterImplTest {
    @Test
    void emptyPathTest(){
        FortressPath path = mock(FortressPath.class);
        ArrayDeque<ArmyUnit> emptyArray = new ArrayDeque<>();
        when(path.getArmyUnits(UnitTeam.LEFT)).thenReturn(emptyArray);
        when(path.getArmyUnits(UnitTeam.RIGHT)).thenReturn(emptyArray);
        PathRater pathRater = new PathRaterImpl(UnitTeam.LEFT);

        PathRating rating = pathRater.ratePath(path);

        assertEquals(PathRating.DRAW, rating);
    }

    @Test
    void minorStrongerEnemyTest(){
        FortressPath path = mock(FortressPath.class);
        when(path.getPathLength()).thenReturn(100);

        ArmyUnit enemyUnit = mock(ArmyUnit.class);
        when(enemyUnit.getHP()).thenReturn(50);
        when(enemyUnit.getDamage()).thenReturn(20);
        when(enemyUnit.getArmor()).thenReturn(10);
        when(enemyUnit.getSpeed()).thenReturn(10);
        when(enemyUnit.getUnitCoords()).thenReturn(80);
        when(enemyUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        ArrayDeque<ArmyUnit> enemyArmy = new ArrayDeque<>();
        enemyArmy.add(enemyUnit);
        when(path.getArmyUnits(UnitTeam.RIGHT)).thenReturn(enemyArmy);

        ArmyUnit friendlyUnit = mock(ArmyUnit.class);
        when(friendlyUnit.getHP()).thenReturn(1);
        when(friendlyUnit.getDamage()).thenReturn(1);
        when(friendlyUnit.getArmor()).thenReturn(1);
        when(friendlyUnit.getSpeed()).thenReturn(1);
        when(friendlyUnit.getUnitCoords()).thenReturn(1);
        when(friendlyUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        ArrayDeque<ArmyUnit> friendlyArmy = new ArrayDeque<>();
        friendlyArmy.add(friendlyUnit);
        when(path.getArmyUnits(UnitTeam.LEFT)).thenReturn(friendlyArmy);

        PathRater pathRater = new PathRaterImpl(UnitTeam.LEFT);

        PathRating rating = pathRater.ratePath(path);
        assertEquals(PathRating.MINOR_DANGER, rating);
    }

    @Test
    void strongerEnemyTest(){
        FortressPath path = mock(FortressPath.class);
        when(path.getPathLength()).thenReturn(100);

        ArmyUnit enemyUnit = mock(ArmyUnit.class);
        when(enemyUnit.getHP()).thenReturn(50);
        when(enemyUnit.getDamage()).thenReturn(20);
        when(enemyUnit.getArmor()).thenReturn(10);
        when(enemyUnit.getSpeed()).thenReturn(10);
        when(enemyUnit.getUnitCoords()).thenReturn(10);
        when(enemyUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        ArrayDeque<ArmyUnit> enemyArmy = new ArrayDeque<>();
        enemyArmy.add(enemyUnit);
        when(path.getArmyUnits(UnitTeam.RIGHT)).thenReturn(enemyArmy);

        ArmyUnit friendlyUnit = mock(ArmyUnit.class);
        when(friendlyUnit.getHP()).thenReturn(1);
        when(friendlyUnit.getDamage()).thenReturn(1);
        when(friendlyUnit.getArmor()).thenReturn(1);
        when(friendlyUnit.getSpeed()).thenReturn(1);
        when(friendlyUnit.getUnitCoords()).thenReturn(1);
        when(friendlyUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        ArrayDeque<ArmyUnit> friendlyArmy = new ArrayDeque<>();
        friendlyArmy.add(friendlyUnit);
        when(path.getArmyUnits(UnitTeam.LEFT)).thenReturn(friendlyArmy);

        PathRater pathRater = new PathRaterImpl(UnitTeam.LEFT);

        PathRating rating = pathRater.ratePath(path);
        assertEquals(PathRating.DANGER, rating);
    }

    @Test
    void greatlyStrongerEnemyTest(){
        FortressPath path = mock(FortressPath.class);
        when(path.getPathLength()).thenReturn(100);

        ArmyUnit enemyUnit = mock(ArmyUnit.class);
        when(enemyUnit.getHP()).thenReturn(100);
        when(enemyUnit.getDamage()).thenReturn(100);
        when(enemyUnit.getArmor()).thenReturn(100);
        when(enemyUnit.getSpeed()).thenReturn(10);
        when(enemyUnit.getDelay()).thenReturn(1);
        when(enemyUnit.getUnitCoords()).thenReturn(5);
        when(enemyUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        ArrayDeque<ArmyUnit> enemyArmy = new ArrayDeque<>();
        enemyArmy.add(enemyUnit);
        when(path.getArmyUnits(UnitTeam.RIGHT)).thenReturn(enemyArmy);

        ArmyUnit friendlyUnit = mock(ArmyUnit.class);
        when(friendlyUnit.getHP()).thenReturn(1);
        when(friendlyUnit.getDamage()).thenReturn(1);
        when(friendlyUnit.getArmor()).thenReturn(1);
        when(friendlyUnit.getSpeed()).thenReturn(1);
        when(friendlyUnit.getDelay()).thenReturn(1);
        when(friendlyUnit.getUnitCoords()).thenReturn(1);
        when(friendlyUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        ArrayDeque<ArmyUnit> friendlyArmy = new ArrayDeque<>();
        enemyArmy.add(friendlyUnit);
        when(path.getArmyUnits(UnitTeam.LEFT)).thenReturn(friendlyArmy);

        PathRater pathRater = new PathRaterImpl(UnitTeam.LEFT);

        PathRating rating = pathRater.ratePath(path);
        assertEquals(PathRating.GREAT_DANGER, rating);
    }

    @Test
    void minorStrongerFriendlyTest(){
        FortressPath path = mock(FortressPath.class);
        when(path.getPathLength()).thenReturn(100);

        ArmyUnit enemyUnit = mock(ArmyUnit.class);
        when(enemyUnit.getHP()).thenReturn(1);
        when(enemyUnit.getDamage()).thenReturn(1);
        when(enemyUnit.getArmor()).thenReturn(1);
        when(enemyUnit.getSpeed()).thenReturn(1);
        when(enemyUnit.getDelay()).thenReturn(1);
        when(enemyUnit.getUnitCoords()).thenReturn(99);
        when(enemyUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        ArrayDeque<ArmyUnit> enemyArmy = new ArrayDeque<>();
        enemyArmy.add(enemyUnit);
        when(path.getArmyUnits(UnitTeam.RIGHT)).thenReturn(enemyArmy);

        ArmyUnit friendlyUnit = mock(ArmyUnit.class);
        when(friendlyUnit.getHP()).thenReturn(50);
        when(friendlyUnit.getDamage()).thenReturn(20);
        when(friendlyUnit.getArmor()).thenReturn(10);
        when(friendlyUnit.getSpeed()).thenReturn(10);
        when(friendlyUnit.getDelay()).thenReturn(1);
        when(friendlyUnit.getUnitCoords()).thenReturn(20);
        when(friendlyUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        ArrayDeque<ArmyUnit> friendlyArmy = new ArrayDeque<>();
        friendlyArmy.add(friendlyUnit);
        when(path.getArmyUnits(UnitTeam.LEFT)).thenReturn(friendlyArmy);

        PathRater pathRater = new PathRaterImpl(UnitTeam.LEFT);

        PathRating rating = pathRater.ratePath(path);
        assertEquals(PathRating.MINOR_ADVANTAGE, rating);
    }

    @Test
    void strongerFriendlyTest(){
        FortressPath path = mock(FortressPath.class);
        when(path.getPathLength()).thenReturn(100);

        ArmyUnit enemyUnit = mock(ArmyUnit.class);
        when(enemyUnit.getHP()).thenReturn(1);
        when(enemyUnit.getDamage()).thenReturn(1);
        when(enemyUnit.getArmor()).thenReturn(1);
        when(enemyUnit.getSpeed()).thenReturn(1);
        when(enemyUnit.getDelay()).thenReturn(2);
        when(enemyUnit.getUnitCoords()).thenReturn(99);
        when(enemyUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        ArrayDeque<ArmyUnit> enemyArmy = new ArrayDeque<>();
        enemyArmy.add(enemyUnit);
        when(path.getArmyUnits(UnitTeam.RIGHT)).thenReturn(enemyArmy);

        ArmyUnit friendlyUnit = mock(ArmyUnit.class);
        when(friendlyUnit.getHP()).thenReturn(50);
        when(friendlyUnit.getDamage()).thenReturn(20);
        when(friendlyUnit.getArmor()).thenReturn(10);
        when(friendlyUnit.getSpeed()).thenReturn(10);
        when(friendlyUnit.getDelay()).thenReturn(1);
        when(friendlyUnit.getUnitCoords()).thenReturn(90);
        when(friendlyUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        ArrayDeque<ArmyUnit> friendlyArmy = new ArrayDeque<>();
        friendlyArmy.add(friendlyUnit);
        when(path.getArmyUnits(UnitTeam.LEFT)).thenReturn(friendlyArmy);

        PathRater pathRater = new PathRaterImpl(UnitTeam.LEFT);

        PathRating rating = pathRater.ratePath(path);
        assertEquals(PathRating.ADVANTAGE, rating);
    }

    @Test
    void greatlyStrongerFriendlyTest(){
        FortressPath path = mock(FortressPath.class);
        when(path.getPathLength()).thenReturn(100);

        ArmyUnit enemyUnit = mock(ArmyUnit.class);
        when(enemyUnit.getHP()).thenReturn(1);
        when(enemyUnit.getDamage()).thenReturn(1);
        when(enemyUnit.getArmor()).thenReturn(1);
        when(enemyUnit.getSpeed()).thenReturn(1);
        when(enemyUnit.getDelay()).thenReturn(10);
        when(enemyUnit.getUnitCoords()).thenReturn(99);
        when(enemyUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        ArrayDeque<ArmyUnit> enemyArmy = new ArrayDeque<>();
        enemyArmy.add(enemyUnit);
        when(path.getArmyUnits(UnitTeam.RIGHT)).thenReturn(enemyArmy);

        ArmyUnit friendlyUnit = mock(ArmyUnit.class);
        when(friendlyUnit.getHP()).thenReturn(100);
        when(friendlyUnit.getDamage()).thenReturn(100);
        when(friendlyUnit.getArmor()).thenReturn(50);
        when(friendlyUnit.getSpeed()).thenReturn(10);
        when(friendlyUnit.getDelay()).thenReturn(1);
        when(friendlyUnit.getUnitCoords()).thenReturn(95);
        when(friendlyUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        ArrayDeque<ArmyUnit> friendlyArmy = new ArrayDeque<>();
        friendlyArmy.add(friendlyUnit);
        when(path.getArmyUnits(UnitTeam.LEFT)).thenReturn(friendlyArmy);

        PathRater pathRater = new PathRaterImpl(UnitTeam.LEFT);

        PathRating rating = pathRater.ratePath(path);
        assertEquals(PathRating.GREAT_ADVANTAGE, rating);
    }
}