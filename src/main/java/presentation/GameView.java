package presentation;


import model.units.UnitTeam;

public interface GameView {
    void initialize(GamePresenter gamePresenter);
    void open();
    void close();

    void spawnUnit(int coords, UnitTeam unitTeam, String hatAddress, String hpInfo);
    void clearSpawnedUnits();

    void showMessage(String messageInfo);
    void updateInfoValues(int leftFortressHPValue,int rightFortressHPValue, int fortressMoneyValue);
    void finishGame(String messageInfo);

}
