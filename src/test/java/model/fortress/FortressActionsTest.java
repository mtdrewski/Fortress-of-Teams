package model.fortress;

import model.fortress.shop.FortressActions;
import model.units.FortressStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FortressActionsTest {
    @Mock
    FortressStatistics stats;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void makeFortressAction() {
        when(stats.getHP()).thenReturn(100);
        FortressActions.LARGE_HEAL.makeFortressAction(stats, 300);
        verify(stats).setHP(400);
        FortressActions.SMALL_HEAL.makeFortressAction(stats, 100);
        verify(stats).setHP(200);
    }
}