package model.fortress.configs;

import model.units.UnitTeam;
import model.units.UnitType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ConfigsProviderTest {
    ConfigsProvider a;

    @BeforeEach
    void setUp() {
        a = new ConfigsProvider(
                FortressActionsShopProvider.EASY,
                FortressUpgradesShopProvider.EASY,
                UnitShopProvider.EASY,
                123, 456);
    }

    @Test
    void getUnitShopInfo() {
        assertThat(a.getUnitShopInfo()).isEqualTo(UnitShopProvider.EASY);
    }

    @Test
    void getUnitUpgradesInfo() {
        assertThat(a.getUnitUpgradesInfo()).isEqualTo(UnitShopProvider.EASY);
    }

    @Test
    void getFortressUpgradesInfo() {
        assertThat(a.getFortressUpgradesInfo()).isEqualTo(FortressUpgradesShopProvider.EASY);
    }

    @Test
    void getFortressActionsInfo() {
        assertThat(a.getFortressActionsInfo()).isEqualTo(FortressActionsShopProvider.EASY);
    }

    @Test
    void getUpgradeActionInfo() {
        assertThat(a.getUpgradeActionInfo()).isEqualTo(FortressUpgradesShopProvider.EASY);
    }

    @Test
    void getFortressMoney() {
        var money = a.createFortressMoney();
        assertThat(money.getMoney()).isEqualTo(123);
    }

    @Test
    void getFortressStatistics() {
        var stats = a.createFortressStatistics(UnitTeam.RIGHT);
        assertThat(stats.getHP()).isEqualTo(456);
        assertThat(stats.getMaxHP()).isEqualTo(456);
        assertThat(stats.getType()).isEqualTo(UnitType.FORTRESS);
        assertThat(stats.getUnitTeam()).isEqualTo(UnitTeam.RIGHT);
    }
}