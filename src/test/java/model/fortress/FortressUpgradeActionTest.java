package model.fortress;

import model.fortress.shop.FortressUpgrade;
import model.fortress.shop.providers.FortressUpgradeAction;
import model.shop.Money;
import model.units.FortressStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FortressUpgradeActionTest {
    @Mock
    Money money;
    @Mock FortressStatistics stats;
    @Mock FortressUpgradeAction.InfoProvider provider;
    @Mock
    FortressUpgrade upg;
    FortressUpgradeAction a;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        a = new FortressUpgradeAction(stats, money, provider);
    }

    @Test
    void performAction() {
        when(provider.getAmountPerLevel(upg, 3)).thenReturn(300);
        a.performAction(upg, 3);
        verify(upg).upgradeFortress(stats, money, 300);
    }
}