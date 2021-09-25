package model.fortress;

import model.fortress.shop.FortressAction;
import model.fortress.shop.FortressActions;
import model.fortress.shop.FortressUpgrade;
import model.fortress.shop.FortressUpgrades;
import model.path.FortressPath;
import model.shop.Money;
import model.shop.Shop;
import model.shop.upgrade.UpgradeInfo;
import model.units.UnitStatistics;
import model.units.UnitTeam;
import model.units.UnitType;
import model.units.UnitUpgrade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
class FortressImplTest {
    @Mock
    Money money;
    @Mock UnitStatistics health;
    @Mock
    Shop<UnitType> unitShop;
    @Mock
    Shop<UnitUpgrade> unitUpgradesShop;
    @Mock
    Shop<FortressUpgrade> fortressUpgradesShop;
    @Mock
    Shop<FortressAction> fortressActionsShop;
    @Mock FortressPath path;
    @Mock UpgradeInfo<UnitUpgrade> unitUpgradesInfo;
    @Mock UpgradeInfo<FortressUpgrade> fortressUpgradesInfo;
    FortressImpl a;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        a = new FortressImpl(
                money,
                health,
                unitShop,
                unitUpgradesShop,
                fortressUpgradesShop,
                fortressActionsShop,
                path,
                unitUpgradesInfo,
                fortressUpgradesInfo
        );
    }
    @Test
    void statsGetters() {
        when(health.getHP()).thenReturn(1);
        when(health.getArmor()).thenReturn(2);
        when(health.getDamage()).thenReturn(3);
        when(health.getMaxHP()).thenReturn(4);
        when(health.getRange()).thenReturn(5);
        when(health.getSpeed()).thenReturn(6);
        when(health.getUnitTeam()).thenReturn(UnitTeam.LEFT);

        assertThat(a.getHP()).isEqualTo(1);
        assertThat(a.getArmor()).isEqualTo(2);
        assertThat(a.getDamage()).isEqualTo(3);
        assertThat(a.getMaxHP()).isEqualTo(4);
        assertThat(a.getRange()).isEqualTo(5);
        assertThat(a.getTeam()).isEqualTo(UnitTeam.LEFT);


        when(health.getHP()).thenReturn(10);
        when(health.getArmor()).thenReturn(20);
        when(health.getDamage()).thenReturn(30);
        when(health.getMaxHP()).thenReturn(40);
        when(health.getRange()).thenReturn(50);
        when(health.getSpeed()).thenReturn(60);
        when(health.getUnitTeam()).thenReturn(UnitTeam.RIGHT);

        assertThat(a.getHP()).isEqualTo(10);
        assertThat(a.getArmor()).isEqualTo(20);
        assertThat(a.getDamage()).isEqualTo(30);
        assertThat(a.getMaxHP()).isEqualTo(40);
        assertThat(a.getRange()).isEqualTo(50);
        assertThat(a.getTeam()).isEqualTo(UnitTeam.RIGHT);
    }

    @Test
    void shops() {
        a.buy(FortressUpgrades.DAMAGE);
        verify(fortressUpgradesShop).buy(FortressUpgrades.DAMAGE);
        a.isAffordable(FortressUpgrades.DAMAGE);
        verify(fortressUpgradesShop).isAffordable(FortressUpgrades.DAMAGE);
        a.isAvailable(FortressUpgrades.DAMAGE);
        verify(fortressUpgradesShop).isAvailable(FortressUpgrades.DAMAGE);
        a.getCost(FortressUpgrades.DAMAGE);
        verify(fortressUpgradesShop).buy(FortressUpgrades.DAMAGE);

        a.buy(FortressActions.LARGE_HEAL);
        verify(fortressActionsShop).buy(FortressActions.LARGE_HEAL);
        a.isAffordable(FortressActions.LARGE_HEAL);
        verify(fortressActionsShop).isAffordable(FortressActions.LARGE_HEAL);
        a.isAvailable(FortressActions.LARGE_HEAL);
        verify(fortressActionsShop).isAvailable(FortressActions.LARGE_HEAL);
        a.getCost(FortressActions.LARGE_HEAL);
        verify(fortressActionsShop).buy(FortressActions.LARGE_HEAL);

        a.buy(UnitUpgrade.HEAVY);
        verify(unitUpgradesShop).buy(UnitUpgrade.HEAVY);
        a.isAffordable(UnitUpgrade.HEAVY);
        verify(unitUpgradesShop).isAffordable(UnitUpgrade.HEAVY);
        a.isAvailable(UnitUpgrade.HEAVY);
        verify(unitUpgradesShop).isAvailable(UnitUpgrade.HEAVY);
        a.getCost(UnitUpgrade.HEAVY);
        verify(unitUpgradesShop).buy(UnitUpgrade.HEAVY);

        a.buy(UnitType.HEAVY);
        verify(unitShop).buy(UnitType.HEAVY);
        a.isAffordable(UnitType.HEAVY);
        verify(unitShop).isAffordable(UnitType.HEAVY);
        a.isAvailable(UnitType.HEAVY);
        verify(unitShop).isAvailable(UnitType.HEAVY);
        a.getCost(UnitType.HEAVY);
        verify(unitShop).buy(UnitType.HEAVY);
    }

    @Test
    void moneyGetters() {
        when(money.getMoney()).thenReturn(3);
        when(money.getIncomePerTU()).thenReturn(4);
        when(money.getTU()).thenReturn(5);

        assertThat(a.getMoney()).isEqualTo(3);
        assertThat(a.getIncomePerTU()).isEqualTo(4);
        assertThat(a.getIncomeTU()).isEqualTo(5);
    }

    @Test
    void upgradesInfoGetters() {
        FortressUpgrade u1 = mock(FortressUpgrade.class);
        FortressUpgrade u2 = mock(FortressUpgrade.class);

        when(fortressUpgradesInfo.getLevel(u1)).thenReturn(3);
        when(fortressUpgradesInfo.getLevel(u2)).thenReturn(5);
        when(fortressUpgradesInfo.getMaxLevel(u1)).thenReturn(6);
        when(fortressUpgradesInfo.getMaxLevel(u2)).thenReturn(7);
        when(unitUpgradesInfo.getLevel(UnitUpgrade.SOLDIER)).thenReturn(30);
        when(unitUpgradesInfo.getLevel(UnitUpgrade.HEAVY)).thenReturn(50);
        when(unitUpgradesInfo.getMaxLevel(UnitUpgrade.SOLDIER)).thenReturn(60);
        when(unitUpgradesInfo.getMaxLevel(UnitUpgrade.HEAVY)).thenReturn(70);

        assertThat(a.getUpgradeLevel(u1)).isEqualTo(3);
        assertThat(a.getUpgradeLevel(u2)).isEqualTo(5);
        assertThat(a.getUpgradeMaxLevel(u1)).isEqualTo(6);
        assertThat(a.getUpgradeMaxLevel(u2)).isEqualTo(7);
        assertThat(a.getUnitLevel(UnitType.SOLDIER)).isEqualTo(30);
        assertThat(a.getUnitLevel(UnitType.HEAVY)).isEqualTo(50);
        assertThat(a.getUnitMaxLevel(UnitType.SOLDIER)).isEqualTo(60);
        assertThat(a.getUnitMaxLevel(UnitType.HEAVY)).isEqualTo(70);
    }

    @Test
    void pathsTests() {
        when(health.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        a.getArmyUnits();
        verify(path).getArmyUnits(a.getTeam());

        assertThat(a.getPath()).isEqualTo(path);
    }
}