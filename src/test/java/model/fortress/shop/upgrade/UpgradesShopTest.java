package model.fortress.shop.upgrade;

import model.fortress.Entry;
import model.shop.upgrade.UpgradesShop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpgradesShopTest {

    @Mock UpgradesShop.InfoProvider<Entry> provider;
    @Mock UpgradesShop.ActionPerLevelProvider<Entry> action;
    UpgradesShop<Entry> a;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        a = new UpgradesShop<>(provider, action);
    }

    @Test
    void initializeTest() {
        a.initialize(Entry.values());
        verify(action).performAction(Entry.DOG, 1);
        verify(action).performAction(Entry.CAT, 1);
        verify(action).performAction(Entry.BIRD, 1);
    }

    @Test
    void performAction() {
        a.performAction(Entry.CAT);
        verify(action).performAction(Entry.CAT, 2);
        a.performAction(Entry.DOG);
        a.performAction(Entry.CAT);
        verify(action).performAction(Entry.CAT, 3);
        verify(action).performAction(Entry.DOG, 2);
        assertThat(a.getLevel(Entry.CAT)).isEqualTo(3);
        assertThat(a.getLevel(Entry.DOG)).isEqualTo(2);
        assertThat(a.getLevel(Entry.BIRD)).isEqualTo(1);
    }

    @Test
    void getAvailability() {
        when(provider.getMaxLevel(Entry.CAT)).thenReturn(2);
        when(provider.getMaxLevel(Entry.DOG)).thenReturn(1);
        assertThat(a.getAvailability(Entry.CAT)).isTrue();
        a.performAction(Entry.CAT);
        assertThat(a.getAvailability(Entry.DOG)).isFalse();
        assertThat(a.getAvailability(Entry.CAT)).isFalse();
    }

    @Test
    void getCost() {
        when(provider.getCostOfNextLevel(Entry.CAT, 1)).thenReturn(100);
        when(provider.getCostOfNextLevel(Entry.CAT, 2)).thenReturn(200);
        when(provider.getCostOfNextLevel(Entry.DOG, 1)).thenReturn(300);
        assertThat(a.getCost(Entry.CAT)).isEqualTo(100);
        a.performAction(Entry.CAT);
        assertThat(a.getCost(Entry.CAT)).isEqualTo(200);
        assertThat(a.getCost(Entry.DOG)).isEqualTo(300);
    }

    @Test
    void getMaxLevel() {
        when(provider.getMaxLevel(Entry.CAT)).thenReturn(3);
        when(provider.getMaxLevel(Entry.DOG)).thenReturn(4);
        assertThat(a.getMaxLevel(Entry.CAT)).isEqualTo(3);
        assertThat(a.getMaxLevel(Entry.DOG)).isEqualTo(4);
    }
}