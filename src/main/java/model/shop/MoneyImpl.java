package model.shop;

import clock.Tickable;


public class MoneyImpl implements Tickable, Money {
    private int money;
    private int incomePerTU;
    private int TU;
    private int cooldown;
    public MoneyImpl(int money, int incomePerTU, int TU) {
        setMoney(money);
        setIncomePerTU(incomePerTU);
        setTU(TU);
        setCooldown(this.TU);
    }

    public void tick() {
        cooldown--;
        if (cooldown <= 0) {
            setCooldown(TU);
            setMoney(money + incomePerTU);
        }
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = Integer.max(money, 0);
    }
    public boolean charge(int cost){
        if(money>=cost){
            money-=cost;
            return true;
        }
        return false;
    }

    public int getIncomePerTU() {
        return incomePerTU;
    }

    public void setIncomePerTU(int incomePerTU) {
        this.incomePerTU = Integer.max(incomePerTU, 0);
    }

    public int getTU() {
        return TU;
    }

    public void setTU(int TU) {
        this.TU = Integer.max(TU, 1);
        this.cooldown = Integer.min(this.cooldown, this.TU);
    }

    public int getCooldown() {
        return cooldown;
    }

    private void setCooldown(int cooldown) {
        this.cooldown = Integer.min(cooldown, TU);
    }
}