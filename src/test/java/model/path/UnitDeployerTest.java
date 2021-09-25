package model.path;

import model.fortress.shop.providers.UnitDeployer;
import model.units.ArmyUnit;
import model.units.UnitStatistics;
import model.units.UnitTeam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.function.Function;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UnitDeployerTest {
    @Mock FortressPath path;
    @Mock Function<UnitTeam, UnitStatistics> f;
    @Mock Function<UnitStatistics, ArmyUnit> unitProducer;
    @Mock ArmyUnit unit;
    @Mock UnitStatistics stats;
    UnitDeployer deployer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deployer = new UnitDeployer(path, UnitTeam.LEFT, unitProducer);
        when(f.apply(UnitTeam.LEFT)).thenReturn(stats);
        when(unitProducer.apply(stats)).thenReturn(unit);
    }

    @Test
    void deployUnitsWithStatistics() {
        deployer.deployUnitsWithStatistics(f);
        verify(path).addUnit(unit);
    }
}