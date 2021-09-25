package model.fortress.shop;

import model.fortress.Entry;
import model.shop.Money;
import model.shop.ShopImpl;
import model.shop.ShopInfoProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FortressShopImplTest {

    @Mock
    ShopInfoProvider<Entry> sip;
    @Mock
    Money money;
    ShopImpl<Entry> a;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        a = new ShopImpl<>(money, sip);
    }

    @Test
    void getCost() {
        when(sip.getCost(Entry.DOG)).thenReturn(12);
        when(sip.getCost(Entry.BIRD)).thenReturn(13);
        when(sip.getCost(Entry.CAT)).thenReturn(14);
        assertThat(a.getCost(Entry.CAT)).isEqualTo(14);
        assertThat(a.getCost(Entry.DOG)).isEqualTo(12);
        assertThat(a.getCost(Entry.BIRD)).isEqualTo(13);
    }

    @Test
    void isAvailable() {
        when(sip.getAvailability(Entry.DOG)).thenReturn(false);
        when(sip.getAvailability(Entry.CAT)).thenReturn(true);
        assertThat(a.isAvailable(Entry.DOG)).isFalse();
        assertThat(a.isAvailable(Entry.CAT)).isTrue();
    }

    @Test
    void isAffordable() {
        when(money.getMoney()).thenReturn(300);
        when(sip.getCost(Entry.DOG)).thenReturn(100);
        when(sip.getCost(Entry.BIRD)).thenReturn(300);
        when(sip.getCost(Entry.CAT)).thenReturn(400);
        assertThat(a.isAffordable(Entry.DOG)).isTrue();
        assertThat(a.isAffordable(Entry.BIRD)).isTrue();
        assertThat(a.isAffordable(Entry.CAT)).isFalse();
    }

    @Test
    void buy() {
        when(money.getMoney()).thenReturn(300);
        when(sip.getCost(Entry.DOG)).thenReturn(100);
        when(sip.getCost(Entry.BIRD)).thenReturn(300);
        when(sip.getCost(Entry.CAT)).thenReturn(400);
        when(money.charge(100)).thenReturn(true);
        when(money.charge(300)).thenReturn(true);
        when(money.charge(400)).thenReturn(false);
        when(sip.getAvailability(Entry.DOG)).thenReturn(false);
        when(sip.getAvailability(Entry.CAT)).thenReturn(true);
        when(sip.getAvailability(Entry.BIRD)).thenReturn(true);
        assertThat(a.buy(Entry.DOG)).isFalse();
        assertThat(a.buy(Entry.BIRD)).isTrue();
        assertThat(a.buy(Entry.CAT)).isFalse();
        verify(money).charge(300);
    }
}