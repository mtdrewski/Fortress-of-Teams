package model.fortress;

import model.fortress.shop.FortressUpgrades;
import model.shop.Money;
import model.units.FortressStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class FortressUpgradesTest {
    @Mock FortressStatistics stats;
    @Mock
    Money money;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void upgrade() {
        FortressUpgrades.DAMAGE.upgradeFortress(stats, money, 400);
        verify(stats).setDamage(400);
        FortressUpgrades.DEFENCE.upgradeFortress(stats, money, 300);
        verify(stats).setArmor(300);
        FortressUpgrades.RANGE.upgradeFortress(stats, money, 200);
        verify(stats).setRange(200);
        FortressUpgrades.INCOME.upgradeFortress(stats, money, 100);
        verify(money).setIncomePerTU(100);
    }
}