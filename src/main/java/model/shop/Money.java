package model.shop;

import clock.Tickable;

public interface Money extends Tickable {
    int getMoney();
    boolean charge(int cost);
    int getIncomePerTU();
    void setIncomePerTU(int income);
    int getTU();
    void setTU(int TU);
}
