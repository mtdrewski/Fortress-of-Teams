package model.units;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticsClassTest {
    @Test
    void gettersTest(){
        StatisticsClass statistics = new StatisticsClass(UnitTeam.LEFT, 1, 1, 3, 4, 5, 6, 7, UnitType.SOLDIER);

        assertEquals(UnitTeam.LEFT, statistics.getUnitTeam());
        assertEquals(1, statistics.getMaxHP());
        assertEquals(1, statistics.getHP());
        assertEquals(3, statistics.getDamage());
        assertEquals(4, statistics.getRange());
        assertEquals(5, statistics.getSpeed());
        assertEquals(6, statistics.getArmor());
        assertEquals(7, statistics.getDelay());
        assertEquals(UnitType.SOLDIER, statistics.getType());
    }

    @Test
    void settersTest(){
        StatisticsClass statistics = new StatisticsClass(UnitTeam.LEFT, 1, 2, 3,4, 5, 6, 7, UnitType.SOLDIER);

        statistics.setRange(10);
        statistics.setArmor(11);
        statistics.setDamage(13);
        statistics.setMaxHP(14);
        statistics.setHP(12);
        statistics.setDelay(15);

        assertEquals(UnitTeam.LEFT, statistics.getUnitTeam());
        assertEquals(12, statistics.getHP());
        assertEquals(10, statistics.getRange());
        assertEquals(5, statistics.getSpeed());
        assertEquals(11, statistics.getArmor());
        assertEquals(15, statistics.getDelay());
        assertEquals(14, statistics.getMaxHP());
    }

    @Test
    void maxHPTest(){
        StatisticsClass s1 = new StatisticsClass(UnitTeam.LEFT, 6, 10, 2, 3, 4, 5, 1, UnitType.SOLDIER);
        StatisticsClass s2 = new StatisticsClass(UnitTeam.LEFT, 6, 1, 2, 3, 4, 5, 1, UnitType.SOLDIER);
        StatisticsClass s3 = new StatisticsClass(UnitTeam.LEFT, 16, 10, 2, 3, 4, 5, 1, UnitType.SOLDIER);

        s2.setHP(10);
        s3.setMaxHP(6);

        assertEquals(6, s1.getHP());
        assertEquals(6, s2.getHP());
        assertEquals(6, s3.getHP());
    }
}