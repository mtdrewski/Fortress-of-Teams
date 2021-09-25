package presentation;

import clock.Clock;
import clock.Tickable;
import model.fortress.Fortress;
import model.fortress.shop.FortressAction;
import model.fortress.shop.FortressActions;
import model.fortress.shop.FortressUpgrade;
import model.units.ArmyUnit;
import model.units.UnitType;
import model.units.UnitUpgrade;

public class GamePresenter implements Tickable {

    private final GameView gameView;
    private final PresenterFactory presenterFactory;
    private final Fortress leftFortress;
    private final Fortress rightFortress;
    private final Clock clock;

    GamePresenter(GameView gameView, PresenterFactory presenterFactoryImpl,Fortress leftFortress, Fortress rightFortress, Clock clock){
        this.gameView=gameView;
        this.presenterFactory= presenterFactoryImpl;
        this.leftFortress= leftFortress;
        this.rightFortress = rightFortress;
        this.clock= clock;
    }

    public void openMenuView(){
        presenterFactory.openMenuView();
    }

    public void open(){
        gameView.open();
    }

    public void close(){
        stopGame();
        gameView.close();
    }

    Thread clockThread;
    public void runGame() {
        clockThread = new Thread(clock::startClock);
        clockThread.start();
    }

    public void stopGame(){
        clock.stopClock();
        clockThread.interrupt();
    }

    public void buyNewUnit(UnitType unitType){
        if (leftFortress.isAffordable(unitType))
            leftFortress.buy(unitType);
        else
            gameView.showMessage("Not enough money, this unit costs: " + leftFortress.getCost(unitType));
    }

    public void buyUnitUpgrade(UnitUpgrade unitUpgrade){
        if(leftFortress.isAvailable(unitUpgrade)) {
            if(leftFortress.isAffordable(unitUpgrade))
                leftFortress.buy(unitUpgrade);
            else
                gameView.showMessage("Not enough money, this unit upgrade costs: " + leftFortress.getCost(unitUpgrade));
        }
        else
            gameView.showMessage("Unit upgrade maximum level!");

    }

    public void buyFortressUpgrade(FortressUpgrade fortressUpgrade){
        if(leftFortress.isAvailable(fortressUpgrade)) {
            if (leftFortress.isAffordable(fortressUpgrade))
                leftFortress.buy(fortressUpgrade);
            else
                gameView.showMessage("Not enough money, this fortress upgrade costs: " + leftFortress.getCost(fortressUpgrade));
        }
        else
            gameView.showMessage("Fortress upgrade maximum level!");
    }

    public void buyFortressAction(FortressAction fortressAction){
        if(leftFortress.getHP()==leftFortress.getMaxHP())
            gameView.showMessage("Maximum health!");
        else {
            if (leftFortress.isAffordable(fortressAction))
                leftFortress.buy(fortressAction);
            else {
                gameView.showMessage("Not enough money, this fortress action costs: " + leftFortress.getCost(fortressAction));
            }
        }
    }

    public void displayUnitInfo(UnitType unitType){
        gameView.showMessage(unitType.getDescription() + ", cost: "+leftFortress.getCost(unitType));
    }

    public void displayUnitUpgradeInfo(UnitUpgrade unitUpgrade){
        if(leftFortress.isAvailable(unitUpgrade))
            gameView.showMessage(unitUpgrade.getDescription() + ", cost: "+leftFortress.getCost(unitUpgrade));
        else
            gameView.showMessage(unitUpgrade.getDescription()+" at max level!");
    }

    public void displayFortressUpgradeInfo(FortressUpgrade fortressUpgrade){
        if(leftFortress.isAvailable(fortressUpgrade))
            gameView.showMessage(fortressUpgrade.getDescription() + ", cost: "+leftFortress.getCost(fortressUpgrade));
        else
            gameView.showMessage(fortressUpgrade.getDescription()+" at max level!");
    }

    public void displayFortressActionInfo(FortressActions fortressAction){
        gameView.showMessage(fortressAction.getDescription() + ", cost: "+leftFortress.getCost(fortressAction));
    }

    public void tick(){
        gameView.clearSpawnedUnits();
        spawnArmy(leftFortress);
        spawnArmy(rightFortress);
        gameView.updateInfoValues(leftFortress.getHP(),rightFortress.getHP(),leftFortress.getMoney());
        checkIfGameFinished();
    }

    private void spawnArmy(Fortress fortress){
        for(ArmyUnit armyUnit: fortress.getArmyUnits()){
            if(armyUnit.getUnitType()!=UnitType.FORTRESS)
                gameView.spawnUnit(armyUnit.getUnitCoords(),armyUnit.getUnitTeam(),armyUnit.getUnitType().getHatAddress(), String.valueOf(armyUnit.getHP()));
        }
    }

    private void checkIfGameFinished(){
        if(leftFortress.getHP()<=0){
            stopGame();
            gameView.finishGame("Right fortress victory!");
        }
        else if(rightFortress.getHP()<=0){
            stopGame();
            gameView.finishGame("Left fortress victory!");
        }
    }
}