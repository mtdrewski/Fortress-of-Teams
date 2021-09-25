package model.fortress.shop;

import model.shop.MoneyImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FortressMoneyTest {
    @Test
    void settersTest() {
        MoneyImpl a = new MoneyImpl(11, 12, 13);
        MoneyImpl b = new MoneyImpl(0, 100, 10);
        b.setMoney(1);
        b.setIncomePerTU(2);
        b.setTU(3);
        assertEquals(11, a.getMoney());
        assertEquals(12, a.getIncomePerTU());
        assertEquals(13, a.getTU());
        assertEquals(1, b.getMoney());
        assertEquals(2, b.getIncomePerTU());
        assertEquals(3, b.getTU());
    }

    @Test
    void incomeTestBasic() {
        MoneyImpl a = new MoneyImpl(0, 100, 10);
        for (int i = 0; i < 9; i++) a.tick();
        assertEquals(0, a.getMoney());
        a.tick();
        assertEquals(100, a.getMoney());
        for (int i = 0; i < 9; i++) a.tick();
        assertEquals(100, a.getMoney());
        a.tick();
        assertEquals(200, a.getMoney());
    }

    @Test
    void chargeTest() {
        MoneyImpl a = new MoneyImpl(99, 100, 10);
        MoneyImpl b = new MoneyImpl(100, 100, 10);
        assertFalse(a.charge(100));
        assertEquals(99, a.getMoney());
        assertTrue(b.charge(100));
        assertEquals(0, b.getMoney());
    }

    @Test
    void validStructure() {
        MoneyImpl a = new MoneyImpl(-1, -1, -1);
        MoneyImpl b = new MoneyImpl(1000, 10, 10);
        b.setMoney(-1);
        b.setIncomePerTU(-1);
        b.setTU(-1);
        assertEquals(0, a.getMoney());
        assertEquals(0, a.getIncomePerTU());
        assertEquals(1, a.getTU());
        assertEquals(1, a.getCooldown());

        assertEquals(0, b.getMoney());
        assertEquals(0, b.getIncomePerTU());
        assertEquals(1, b.getTU());
        assertEquals(1, b.getCooldown());
    }

    @Test
    void incomeTestTUChange() {
        MoneyImpl a = new MoneyImpl(0, 100, 10);
        for (int i = 0; i < 3; i++) a.tick();
        a.setTU(3);
        for (int i = 0; i < 3; i++) a.tick();
        assertEquals(100, a.getMoney());
    }
}