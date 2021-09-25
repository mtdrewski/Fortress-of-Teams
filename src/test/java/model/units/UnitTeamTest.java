package model.units;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UnitTeamTest {

    @Test
    void otherTeamTest(){
        assertEquals(UnitTeam.LEFT, UnitTeam.RIGHT.otherTeam());
        assertEquals(UnitTeam.RIGHT, UnitTeam.LEFT.otherTeam());
    }

    @Test
    void directionTest(){
        assertTrue(UnitTeam.LEFT.getDirection() > 0);
        assertTrue(UnitTeam.RIGHT.getDirection() < 0);
    }
}