package model.units;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArmyUnitImplTest {

    @Test
    void getStatisticsTest() {
        Integer speedValue = 10;
        Integer rangeValue = 20;
        Integer armorValue = 30;
        Integer hpValue = 40;
        Integer damageValue = 50;
        Integer delayValue = 60;
        UnitTeam teamValue = UnitTeam.LEFT;
        UnitType unitType = UnitType.HEAVY;

        UnitStatistics statistics = mock(UnitStatistics.class);
        when(statistics.getSpeed()).thenReturn(speedValue);
        when(statistics.getRange()).thenReturn(rangeValue);
        when(statistics.getArmor()).thenReturn(armorValue);
        when(statistics.getHP()).thenReturn(hpValue);
        when(statistics.getDamage()).thenReturn(damageValue);
        when(statistics.getDelay()).thenReturn(delayValue);
        when(statistics.getUnitTeam()).thenReturn(teamValue);
        when(statistics.getType()).thenReturn(unitType);
        ArmyUnitImpl unit = new ArmyUnitImpl(statistics);

        assertEquals(speedValue, unit.getSpeed());
        assertEquals(rangeValue, unit.getRange());
        assertEquals(armorValue, unit.getArmor());
        assertEquals(hpValue, unit.getHP());
        assertEquals(damageValue, unit.getDamage());
        assertEquals(delayValue, unit.getDelay());
        assertEquals(teamValue, unit.getUnitTeam());
        assertEquals(unitType, unit.getUnitType());
    }

    @Test
    void unitCoordsTest() {
        UnitStatistics statistics = mock(UnitStatistics.class);
        ArmyUnitImpl unit = new ArmyUnitImpl(statistics);

        assertThrows(RuntimeException.class, unit::getUnitCoords);
        unit.setUnitCoords(10);
        assertEquals(10, unit.getUnitCoords());
        unit.setUnitCoords(20);
        assertEquals(20, unit.getUnitCoords());
    }

    @Test
    void moveUnitLeftTest() {
        UnitStatistics statistics = mock(UnitStatistics.class);
        when(statistics.getSpeed()).thenReturn(10);
        when(statistics.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        ArmyUnitImpl unit = new ArmyUnitImpl(statistics);
        unit.setUnitCoords(30);

        unit.moveUnit(100);

        assertEquals(40, unit.getUnitCoords());
    }

    @Test
    void moveUnitLeftBorderTest() {
        UnitStatistics statistics = mock(UnitStatistics.class);
        when(statistics.getSpeed()).thenReturn(10);
        when(statistics.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        ArmyUnitImpl unit = new ArmyUnitImpl(statistics);
        unit.setUnitCoords(30);

        unit.moveUnit(35);

        assertEquals(35, unit.getUnitCoords());
    }

    @Test
    void moveUnitRightTest() {
        UnitStatistics statistics = mock(UnitStatistics.class);
        when(statistics.getSpeed()).thenReturn(10);
        when(statistics.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        ArmyUnitImpl unit = new ArmyUnitImpl(statistics);
        unit.setUnitCoords(30);

        unit.moveUnit(0);

        assertEquals(20, unit.getUnitCoords());
    }

    @Test
    void moveUnitRightBorderTest() {
        UnitStatistics statistics = mock(UnitStatistics.class);
        when(statistics.getSpeed()).thenReturn(10);
        when(statistics.getUnitTeam()).thenReturn(UnitTeam.RIGHT);
        ArmyUnitImpl unit = new ArmyUnitImpl(statistics);
        unit.setUnitCoords(30);

        unit.moveUnit(25);

        assertEquals(25, unit.getUnitCoords());
    }

    @Test
    void moveUnitBehindBorder() {
        UnitStatistics statistics = mock(UnitStatistics.class);
        when(statistics.getSpeed()).thenReturn(10);
        when(statistics.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        ArmyUnitImpl unit = new ArmyUnitImpl(statistics);
        unit.setUnitCoords(30);

        unit.moveUnit(10);
        assertEquals(10, unit.getUnitCoords());
    }

    @Test
    void enemyInRangeTest() {
        UnitStatistics statistics = mock(UnitStatistics.class);
        when(statistics.getRange()).thenReturn(10);
        ArmyUnitImpl testedUnit = new ArmyUnitImpl(statistics);
        testedUnit.setUnitCoords(30);

        ArmyUnit enemyUnitRightInRange = mock(ArmyUnit.class);
        when(enemyUnitRightInRange.getUnitCoords()).thenReturn(35);
        ArmyUnit enemyUnitRightOutOfReach = mock(ArmyUnit.class);
        when(enemyUnitRightOutOfReach.getUnitCoords()).thenReturn(50);
        ArmyUnit enemyUnitLeftInRange = mock(ArmyUnit.class);
        when(enemyUnitLeftInRange.getUnitCoords()).thenReturn(25);
        ArmyUnit enemyUnitLeftOutOfReach = mock(ArmyUnit.class);
        when(enemyUnitLeftOutOfReach.getUnitCoords()).thenReturn(10);

        assertTrue(testedUnit.enemyInRange(enemyUnitRightInRange));
        assertFalse(testedUnit.enemyInRange(enemyUnitRightOutOfReach));
        assertTrue(testedUnit.enemyInRange(enemyUnitLeftInRange));
        assertFalse(testedUnit.enemyInRange(enemyUnitLeftOutOfReach));
    }

    @Test
    void receiveDamageTest() {
        UnitStatistics testUnitStatistics = mock(UnitStatistics.class);
        when(testUnitStatistics.getHP()).thenReturn(10);
        when(testUnitStatistics.getArmor()).thenReturn(2);
        ArmyUnitImpl testUnit = new ArmyUnitImpl(testUnitStatistics);

        UnitStatistics tankUnitStatistics = mock(UnitStatistics.class);
        when(tankUnitStatistics.getHP()).thenReturn(10);
        when(tankUnitStatistics.getArmor()).thenReturn(10);
        ArmyUnitImpl tankUnit = new ArmyUnitImpl(tankUnitStatistics);

        testUnit.receiveDamage(5);
        tankUnit.receiveDamage(5);

        verify(testUnitStatistics).setHP(7);
        verify(tankUnitStatistics).setHP(9);
    }

    @Test
    void attackTest() {
        UnitStatistics attackingUnitStatistics = mock(UnitStatistics.class);
        when(attackingUnitStatistics.getDamage()).thenReturn(10);
        ArmyUnitImpl attackingUnit = new ArmyUnitImpl(attackingUnitStatistics);
        ArmyUnit attackedUnit = mock(ArmyUnit.class);

        attackingUnit.attack(attackedUnit);

        verify(attackedUnit).receiveDamage(10);
    }

    @Test
    void isDeadTest() {
        UnitStatistics aliveStatistics = mock(UnitStatistics.class);
        when(aliveStatistics.getHP()).thenReturn(10);
        ArmyUnitImpl aliveUnit = new ArmyUnitImpl(aliveStatistics);

        UnitStatistics deadStatistics = mock(UnitStatistics.class);
        when(deadStatistics.getHP()).thenReturn(0);
        ArmyUnitImpl deadUnit = new ArmyUnitImpl(deadStatistics);

        assertFalse(aliveUnit.isDead());
        assertTrue(deadUnit.isDead());
    }

    @Test
    void attackDelayTest() {
        UnitStatistics attackingUnitStatistics = mock(UnitStatistics.class);
        when(attackingUnitStatistics.getDamage()).thenReturn(10);
        when(attackingUnitStatistics.getDelay()).thenReturn(3);
        ArmyUnitImpl attackingUnit = new ArmyUnitImpl(attackingUnitStatistics);
        ArmyUnit attackedUnit = mock(ArmyUnit.class);

        for (int i = 0; i < 7; i ++){
            attackingUnit.attack(attackedUnit);
        }

        verify(attackedUnit, times(3)).receiveDamage(10);
    }

    @Test
    void attackAndMoveDelayTest() {
        UnitStatistics attackingUnitStatistics = mock(UnitStatistics.class);
        when(attackingUnitStatistics.getDamage()).thenReturn(10);
        when(attackingUnitStatistics.getDelay()).thenReturn(3);
        when(attackingUnitStatistics.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        ArmyUnitImpl attackingUnit = new ArmyUnitImpl(attackingUnitStatistics);
        attackingUnit.setUnitCoords(2);
        ArmyUnit attackedUnit = mock(ArmyUnit.class);

        attackingUnit.attack(attackedUnit);
        for (int i = 0; i < 5; i ++) {
            attackingUnit.moveUnit(10);
        }
        attackingUnit.attack(attackedUnit);
        attackingUnit.attack(attackedUnit);
        attackingUnit.attack(attackedUnit);

        verify(attackedUnit, times(2)).receiveDamage(10);
    }

    @Test
    void moveAfterAttackTest() {
        UnitStatistics attackingUnitStatistics = mock(UnitStatistics.class);
        when(attackingUnitStatistics.getDamage()).thenReturn(10);
        when(attackingUnitStatistics.getDelay()).thenReturn(3);
        when(attackingUnitStatistics.getSpeed()).thenReturn(5);
        when(attackingUnitStatistics.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        ArmyUnitImpl attackingUnit = new ArmyUnitImpl(attackingUnitStatistics);
        attackingUnit.setUnitCoords(2);
        ArmyUnit attackedUnit = mock(ArmyUnit.class);

        attackingUnit.attack(attackedUnit);
        attackingUnit.moveUnit(10);

        assertEquals(2, attackingUnit.getUnitCoords());
    }
}
