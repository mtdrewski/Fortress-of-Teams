package model.fortress;

import model.fortress.shop.FortressAction;
import model.fortress.shop.providers.FortressActionsShop;
import model.units.FortressStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FortressActionsShopTest {

    @Mock FortressActionsShop.InfoProvider provider;
    @Mock FortressStatistics stats;
    @Mock
    FortressAction action1, action2, action3;
    FortressActionsShop a;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        a = new FortressActionsShop(stats, provider);
    }

    @Test
    void performAction() {
        when(provider.getAmount(action1)).thenReturn(1);
        when(provider.getAmount(action2)).thenReturn(2);
        when(provider.getAmount(action3)).thenReturn(3);
        a.performAction(action1);
        a.performAction(action2);
        a.performAction(action3);
        verify(action1).makeFortressAction(stats, 1);
        verify(action2).makeFortressAction(stats, 2);
        verify(action3).makeFortressAction(stats, 3);
    }

    @Test
    void getAvailability() {
        assertThat(a.getAvailability(action1)).isTrue();
        assertThat(a.getAvailability(action2)).isTrue();
        assertThat(a.getAvailability(action3)).isTrue();
    }

    @Test
    void getCost() {
        when(provider.getCost(action1)).thenReturn(100);
        when(provider.getCost(action2)).thenReturn(200);
        when(provider.getCost(action3)).thenReturn(400);
        assertThat(a.getCost(action1)).isEqualTo(100);
        assertThat(a.getCost(action2)).isEqualTo(200);
        assertThat(a.getCost(action3)).isEqualTo(400);
    }
}