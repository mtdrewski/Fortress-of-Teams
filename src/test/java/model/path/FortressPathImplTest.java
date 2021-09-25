package model.path;

import model.units.ArmyUnit;
import model.units.UnitTeam;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FortressPathImplTest {
    @Test
    void pathLengthTest(){
        ArmyUnit rightFortressUnit = mock(ArmyUnit.class);
        when(rightFortressUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        ArmyUnit leftFortressUnit = mock(ArmyUnit.class);
        when(leftFortressUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);

        FortressPath path = new FortressPathImpl(100, leftFortressUnit, rightFortressUnit, 1, 10);

        assertEquals(100, path.getPathLength());
    }

    @Test
    void emptyGetterTest(){
        ArmyUnit rightFortressUnit = mock(ArmyUnit.class);
        when(rightFortressUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        ArmyUnit leftFortressUnit = mock(ArmyUnit.class);
        when(leftFortressUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        FortressPath path = new FortressPathImpl(100, leftFortressUnit, rightFortressUnit, 1, 10);

        Object[] rightArmy = path.getArmyUnits(UnitTeam.RIGHT).toArray();
        Object[] leftArmy = path.getArmyUnits(UnitTeam.LEFT).toArray();

        ArmyUnit[] rightUnits = {rightFortressUnit};
        ArmyUnit[] leftUnits = {leftFortressUnit};
        assertArrayEquals(rightUnits, rightArmy);
        assertArrayEquals(leftUnits, leftArmy);
    }

    @Test
    void shortPathTest(){
        ArmyUnit rightFortressUnit = mock(ArmyUnit.class);
        when(rightFortressUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        ArmyUnit leftFortressUnit = mock(ArmyUnit.class);
        when(leftFortressUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);

        assertThrows(IllegalArgumentException.class, () -> new FortressPathImpl(-1, leftFortressUnit, rightFortressUnit, 1, 10));
    }

    @Test
    void badFortressUnitsTest(){
        ArmyUnit rightFortressUnit = mock(ArmyUnit.class);
        when(rightFortressUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        ArmyUnit leftFortressUnit = mock(ArmyUnit.class);
        when(leftFortressUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);

        assertThrows(IllegalArgumentException.class, () -> new FortressPathImpl(100, leftFortressUnit, rightFortressUnit, 1, 10));
    }

    @Test
    void addUnitTest(){
        ArmyUnit rightFortressUnit = mock(ArmyUnit.class);
        when(rightFortressUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        ArmyUnit leftFortressUnit = mock(ArmyUnit.class);
        when(leftFortressUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        int interspace = 10;
        FortressPathImpl path = new FortressPathImpl(100, leftFortressUnit, rightFortressUnit, 1, interspace);

        ArmyUnit rightArmyUnit = mock(ArmyUnit.class);
        when(rightArmyUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        ArmyUnit leftArmyUnit = mock(ArmyUnit.class);
        when(leftArmyUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);

        path.addUnit(rightArmyUnit);
        path.addUnit(leftArmyUnit);
        path.tick();
        Object[] pathRightArmy = path.getArmyUnits(UnitTeam.RIGHT).toArray();
        Object[] pathLeftArmy = path.getArmyUnits(UnitTeam.LEFT).toArray();

        ArmyUnit[] rightUnits = {rightArmyUnit, rightFortressUnit};
        ArmyUnit[] leftUnits = {leftArmyUnit, leftFortressUnit};
        assertArrayEquals(rightUnits, pathRightArmy);
        assertArrayEquals(leftUnits, pathLeftArmy);
        verify(rightFortressUnit).setUnitCoords(100);
        verify(rightArmyUnit).setUnitCoords(100 - interspace);
        verify(leftFortressUnit).setUnitCoords(0);
        verify(leftArmyUnit).setUnitCoords(interspace);
    }

    @Test
    void addTwoUnitsOneTickTest(){
        ArmyUnit rightFortressUnit = mock(ArmyUnit.class);
        when(rightFortressUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        ArmyUnit leftFortressUnit = mock(ArmyUnit.class);
        when(leftFortressUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        int interspace = 10;
        FortressPathImpl path = new FortressPathImpl(100, leftFortressUnit, rightFortressUnit, 1, interspace);

        ArmyUnit unit1 = mock(ArmyUnit.class);
        when(unit1.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        when(unit1.getSpeed()).thenReturn(5);
        ArmyUnit unit2 = mock(ArmyUnit.class);
        when(unit2.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        when(unit2.getSpeed()).thenReturn(5);

        path.addUnit(unit1);
        path.addUnit(unit2);
        path.tick();
        Object[] pathLeftArmy = path.getArmyUnits(UnitTeam.LEFT).toArray();

        ArmyUnit[] leftUnits = {unit1, leftFortressUnit};
        assertArrayEquals(leftUnits, pathLeftArmy);
        verify(leftFortressUnit).setUnitCoords(0);
        verify(unit1).setUnitCoords(interspace);
    }

    @Test
    void addTwoUnitsTwoTicksTest(){
        ArmyUnit rightFortressUnit = mock(ArmyUnit.class);
        when(rightFortressUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        when(rightFortressUnit.getUnitCoords()).thenReturn(100);
        ArmyUnit leftFortressUnit = mock(ArmyUnit.class);
        when(leftFortressUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        int interspace = 10;
        FortressPathImpl path = new FortressPathImpl(100, leftFortressUnit, rightFortressUnit, 1, interspace);

        ArmyUnit unit1 = mock(ArmyUnit.class);
        when(unit1.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        when(unit1.getUnitCoords()).thenReturn(1 + 2 * interspace);
        ArmyUnit unit2 = mock(ArmyUnit.class);
        when(unit2.getUnitTeam()).thenReturn(UnitTeam.LEFT);

        path.addUnit(unit1);
        path.addUnit(unit2);
        path.tick();
        path.tick();
        Object[] pathLeftArmy = path.getArmyUnits(UnitTeam.LEFT).toArray();

        ArmyUnit[] leftUnits = {unit1, unit2, leftFortressUnit};
        assertArrayEquals(leftUnits, pathLeftArmy);
        verify(leftFortressUnit).setUnitCoords(0);
        verify(unit1).setUnitCoords(interspace);
        verify(unit1).moveUnit(100 - interspace);
        verify(unit2).setUnitCoords(interspace);
    }

    @Test
    void addTwoUnitsThreeTicksTest(){
        ArmyUnit rightFortressUnit = mock(ArmyUnit.class);
        when(rightFortressUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        when(rightFortressUnit.getUnitCoords()).thenReturn(100);
        ArmyUnit leftFortressUnit = mock(ArmyUnit.class);
        when(leftFortressUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        int interspace = 10;
        FortressPathImpl path = new FortressPathImpl(100, leftFortressUnit, rightFortressUnit, 1, interspace);

        ArmyUnit unit1 = mock(ArmyUnit.class);
        when(unit1.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        ArmyUnit unit2 = mock(ArmyUnit.class);
        when(unit2.getUnitTeam()).thenReturn(UnitTeam.LEFT);

        path.addUnit(unit1);
        path.addUnit(unit2);
        path.tick();
        when(unit1.getUnitCoords()).thenReturn(1 + 2 * interspace);
        path.tick();
        when(unit1.getUnitCoords()).thenReturn(1 + 3 * interspace);
        path.tick();
        Object[] pathLeftArmy = path.getArmyUnits(UnitTeam.LEFT).toArray();

        ArmyUnit[] leftUnits = {unit1, unit2, leftFortressUnit};
        assertArrayEquals(leftUnits, pathLeftArmy);
        verify(leftFortressUnit).setUnitCoords(0);
        verify(unit1).setUnitCoords(interspace);
        verify(unit1, times(2)).moveUnit(100 - interspace);
        verify(unit2).setUnitCoords(interspace);
        verify(unit2).moveUnit(1 + 2 * interspace);
    }

    @Test
    void addTwoUnitsBlockingTest(){
        ArmyUnit rightFortressUnit = mock(ArmyUnit.class);
        when(rightFortressUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        when(rightFortressUnit.getUnitCoords()).thenReturn(100);
        ArmyUnit leftFortressUnit = mock(ArmyUnit.class);
        when(leftFortressUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        int interspace = 10;
        FortressPathImpl path = new FortressPathImpl(100, leftFortressUnit, rightFortressUnit, 1, interspace);

        ArmyUnit unit1 = mock(ArmyUnit.class);
        when(unit1.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        ArmyUnit unit2 = mock(ArmyUnit.class);
        when(unit2.getUnitTeam()).thenReturn(UnitTeam.LEFT);

        path.addUnit(unit1);
        path.addUnit(unit2);
        path.tick();
        when(unit1.getUnitCoords()).thenReturn(1);
        path.tick();
        Object[] pathLeftArmy = path.getArmyUnits(UnitTeam.LEFT).toArray();

        ArmyUnit[] leftUnits = {unit1, leftFortressUnit};
        assertArrayEquals(leftUnits, pathLeftArmy);
        verify(leftFortressUnit).setUnitCoords(0);
        verify(unit1).setUnitCoords(interspace);
        verify(unit1).moveUnit(100 - interspace);
    }

    @Test
    void addTwoUnitsLongDelayTest(){
        ArmyUnit rightFortressUnit = mock(ArmyUnit.class);
        when(rightFortressUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        when(rightFortressUnit.getUnitCoords()).thenReturn(100);
        ArmyUnit leftFortressUnit = mock(ArmyUnit.class);
        when(leftFortressUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        int interspace = 10;
        FortressPathImpl path = new FortressPathImpl(100, leftFortressUnit, rightFortressUnit, 3, interspace);

        ArmyUnit unit1 = mock(ArmyUnit.class);
        when(unit1.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        ArmyUnit unit2 = mock(ArmyUnit.class);
        when(unit2.getUnitTeam()).thenReturn(UnitTeam.LEFT);

        path.addUnit(unit1);
        path.addUnit(unit2);
        path.tick();
        when(unit1.getUnitCoords()).thenReturn(5);
        path.tick();
        path.tick();
        Object[] pathLeftArmy = path.getArmyUnits(UnitTeam.LEFT).toArray();

        ArmyUnit[] leftUnits = {unit1, leftFortressUnit};
        assertArrayEquals(leftUnits, pathLeftArmy);
        verify(leftFortressUnit).setUnitCoords(0);
        verify(unit1).setUnitCoords(interspace);
        verify(unit1, times(2)).moveUnit(100 - interspace);
    }

    @Test
    void addTwoUnitsShortDelayTest(){
        ArmyUnit rightFortressUnit = mock(ArmyUnit.class);
        when(rightFortressUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        when(rightFortressUnit.getUnitCoords()).thenReturn(100);
        ArmyUnit leftFortressUnit = mock(ArmyUnit.class);
        when(leftFortressUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        int interspace = 10;
        FortressPathImpl path = new FortressPathImpl(100, leftFortressUnit, rightFortressUnit, 3, interspace);

        ArmyUnit unit1 = mock(ArmyUnit.class);
        when(unit1.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        ArmyUnit unit2 = mock(ArmyUnit.class);
        when(unit2.getUnitTeam()).thenReturn(UnitTeam.LEFT);

        path.addUnit(unit1);
        path.addUnit(unit2);
        path.tick();
        when(unit1.getUnitCoords()).thenReturn(1 + 2 * interspace);
        path.tick();
        path.tick();
        path.tick();
        Object[] pathLeftArmy = path.getArmyUnits(UnitTeam.LEFT).toArray();

        ArmyUnit[] leftUnits = {unit1, unit2, leftFortressUnit};
        assertArrayEquals(leftUnits, pathLeftArmy);
        verify(leftFortressUnit).setUnitCoords(0);
        verify(unit1).setUnitCoords(interspace);
        verify(unit1, times(3)).moveUnit(100 - interspace);
    }

    @Test
    void attackTest(){
        ArmyUnit rightFortressUnit = mock(ArmyUnit.class);
        when(rightFortressUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        when(rightFortressUnit.getUnitCoords()).thenReturn(10);
        ArmyUnit leftFortressUnit = mock(ArmyUnit.class);
        when(leftFortressUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        FortressPath path = new FortressPathImpl(10, leftFortressUnit, rightFortressUnit, 1, 10);

        ArmyUnit leftUnit = mock(ArmyUnit.class);
        when(leftUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        ArmyUnit rightUnit = mock(ArmyUnit.class);
        when(rightUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);

        when(leftUnit.enemyInRange(rightUnit)).thenReturn(true);

        path.addUnit(leftUnit);
        path.addUnit(rightUnit);
        path.tick();
        path.tick();

        verify(leftUnit).attack(rightUnit);
        verify(leftUnit, times(0)).attack(rightFortressUnit);
        verify(rightUnit, times(0)).attack(leftUnit);
        verify(rightUnit, times(0)).attack(leftFortressUnit);
    }

    @Test
    void twoUnitsAttackOneTest(){
        ArmyUnit rightFortressUnit = mock(ArmyUnit.class);
        when(rightFortressUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        when(rightFortressUnit.getUnitCoords()).thenReturn(10);
        ArmyUnit leftFortressUnit = mock(ArmyUnit.class);
        when(leftFortressUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        FortressPath path = new FortressPathImpl(100, leftFortressUnit, rightFortressUnit, 1, 1);

        ArmyUnit leftUnit1 = mock(ArmyUnit.class);
        when(leftUnit1.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        when(leftUnit1.getUnitCoords()).thenReturn(30);
        ArmyUnit leftUnit2 = mock(ArmyUnit.class);
        when(leftUnit2.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        ArmyUnit rightUnit = mock(ArmyUnit.class);
        when(rightUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);

        path.addUnit(leftUnit1);
        path.addUnit(leftUnit2);
        path.addUnit(rightUnit);
        path.tick();
        path.tick();
        when(leftUnit1.enemyInRange(rightUnit)).thenReturn(true);
        when(leftUnit2.enemyInRange(rightUnit)).thenReturn(true);
        path.tick();

        verify(leftUnit1).attack(rightUnit);
        verify(leftUnit1, times(0)).attack(rightFortressUnit);
        verify(leftUnit2).attack(rightUnit);
        verify(leftUnit2, times(0)).attack(rightFortressUnit);
        verify(rightUnit, times(0)).attack(leftUnit1);
        verify(rightUnit, times(0)).attack(leftUnit2);
        verify(rightUnit, times(0)).attack(leftFortressUnit);
    }

    @Test
    void lethalAttackTest(){
        ArmyUnit rightFortressUnit = mock(ArmyUnit.class);
        when(rightFortressUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        when(rightFortressUnit.getUnitCoords()).thenReturn(10);
        ArmyUnit leftFortressUnit = mock(ArmyUnit.class);
        when(leftFortressUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        FortressPath path = new FortressPathImpl(10, leftFortressUnit, rightFortressUnit, 1, 10);

        ArmyUnit leftUnit = mock(ArmyUnit.class);
        when(leftUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        when(leftUnit.isDead()).thenReturn(true);
        ArmyUnit rightUnit = mock(ArmyUnit.class);
        when(rightUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        when(rightUnit.isDead()).thenReturn(true);

        when(leftUnit.enemyInRange(rightUnit)).thenReturn(true);


        path.addUnit(leftUnit);
        path.addUnit(rightUnit);
        path.tick();
        path.tick();

        Object[] pathRightArmy = path.getArmyUnits(UnitTeam.RIGHT).toArray();
        Object[] pathLeftArmy = path.getArmyUnits(UnitTeam.LEFT).toArray();

        ArmyUnit[] rightUnits = {rightFortressUnit};
        ArmyUnit[] leftUnits = {leftFortressUnit};
        assertArrayEquals(rightUnits, pathRightArmy);
        assertArrayEquals(leftUnits, pathLeftArmy);
    }

    @Test
    void fortressUnitIsDeadTest() {
        ArmyUnit rightFortressUnit = mock(ArmyUnit.class);
        when(rightFortressUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        when(rightFortressUnit.getUnitCoords()).thenReturn(10);
        ArmyUnit leftFortressUnit = mock(ArmyUnit.class);
        when(leftFortressUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        when(leftFortressUnit.isDead()).thenReturn(true);
        FortressPath path = new FortressPathImpl(10, leftFortressUnit, rightFortressUnit, 1, 1);

        ArmyUnit rightUnit = mock(ArmyUnit.class);
        when(rightUnit.getUnitTeam()).thenReturn(UnitTeam.RIGHT);

        when(rightUnit.enemyInRange(leftFortressUnit)).thenReturn(true);

        path.addUnit(rightUnit);
        path.tick();
        path.tick();

        Object[] pathRightArmy = path.getArmyUnits(UnitTeam.RIGHT).toArray();
        Object[] pathLeftArmy = path.getArmyUnits(UnitTeam.LEFT).toArray();

        ArmyUnit[] rightUnits = {rightUnit, rightFortressUnit};
        ArmyUnit[] leftUnits = {leftFortressUnit};
        assertArrayEquals(rightUnits, pathRightArmy);
        assertArrayEquals(leftUnits, pathLeftArmy);
    }
}
