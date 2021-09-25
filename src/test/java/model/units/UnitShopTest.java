package model.units;

import model.fortress.shop.providers.Deployer;
import model.fortress.shop.providers.UnitShop;
import model.shop.upgrade.UpgradeInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UnitShopTest {
    @Mock UnitShop.InfoProvider provider;
    @Mock UpgradeInfo<UnitUpgrade> upgradeInfo;
    @Mock
    Deployer deployer;
    @Mock Function<UnitTeam, UnitStatistics> stats1, stats2;
    UnitShop a;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        a = new UnitShop(upgradeInfo, provider, deployer);
    }

    @Test
    void performAction() {
        when(provider.createStatisticsForUnit(UnitType.SOLDIER, 2)).thenReturn(stats1);
        when(provider.createStatisticsForUnit(UnitType.SOLDIER, 3)).thenReturn(stats2);

        when(upgradeInfo.getLevel(UnitUpgrade.SOLDIER)).thenReturn(2);
        a.performAction(UnitType.SOLDIER);
        verify(deployer).deployUnitsWithStatistics(stats1);

        when(upgradeInfo.getLevel(UnitUpgrade.SOLDIER)).thenReturn(3);
        a.performAction(UnitType.SOLDIER);
        verify(deployer).deployUnitsWithStatistics(stats2);

    }

    @Test
    void getAvailability() {
        for(UnitType t :UnitType.values()) assertThat(a.getAvailability(t)).isTrue();
    }

    @Test
    void getCost() {
        when(provider.getCostPerLevel(UnitType.SOLDIER, 1)).thenReturn(100);
        when(provider.getCostPerLevel(UnitType.SOLDIER, 2)).thenReturn(200);
        when(provider.getCostPerLevel(UnitType.SOLDIER, 3)).thenReturn(300);

        when(upgradeInfo.getLevel(UnitUpgrade.SOLDIER)).thenReturn(1);
        assertThat(a.getCost(UnitType.SOLDIER)).isEqualTo(100);

        when(upgradeInfo.getLevel(UnitUpgrade.SOLDIER)).thenReturn(2);
        assertThat(a.getCost(UnitType.SOLDIER)).isEqualTo(200);

        when(upgradeInfo.getLevel(UnitUpgrade.SOLDIER)).thenReturn(3);
        assertThat(a.getCost(UnitType.SOLDIER)).isEqualTo(300);
    }
}