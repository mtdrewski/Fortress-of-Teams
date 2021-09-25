package presentation;

import clock.Clock;
import model.fortress.Fortress;
import model.fortress.shop.FortressActions;
import model.fortress.shop.FortressUpgrades;
import model.units.ArmyUnit;
import model.units.UnitTeam;
import model.units.UnitType;
import model.units.UnitUpgrade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;

import static org.mockito.Mockito.*;

public class GamePresenterTest {

    GameView gameView;
    PresenterFactory presenterFactory;
    Fortress leftFortress;
    Fortress rightFortress;
    Clock clock;


    @BeforeEach
    void setup() {
        gameView = mock(GameView.class);
        presenterFactory = mock(PresenterFactory.class);
        leftFortress = mock(Fortress.class);
        rightFortress = mock(Fortress.class);
        clock = mock(Clock.class);
    }

    @Test
    void openMenuViewTest(){
        new GamePresenter(gameView,presenterFactory,leftFortress,rightFortress,clock).openMenuView();
        verify(presenterFactory).openMenuView();
    }

    @Test
    void openTest(){
        new GamePresenter(gameView,presenterFactory,leftFortress,rightFortress,clock).open();
        verify(gameView).open();
    }

    @Test
    void runCloseTest(){
        GamePresenter gamePresenter = new GamePresenter(gameView,presenterFactory,leftFortress,rightFortress,clock);
        gamePresenter.runGame();
        gamePresenter.close();
        verify(clock).stopClock();
        verify(gameView).close();
    }

    @Test
    void buyNewUnitTest(){
        GamePresenter gamePresenter = new GamePresenter(gameView,presenterFactory,leftFortress,rightFortress,clock);

        when(leftFortress.isAffordable(UnitType.SOLDIER)).thenReturn(true);
        when(leftFortress.isAffordable(UnitType.SNIPER)).thenReturn(false);
        when(leftFortress.isAffordable(UnitType.HEAVY)).thenReturn(true);

        gamePresenter.buyNewUnit(UnitType.SOLDIER);
        verify(leftFortress).buy(UnitType.SOLDIER);
        verify(gameView,times(0)).showMessage(anyString());
        gamePresenter.buyNewUnit(UnitType.SNIPER);
        verify(leftFortress,times(0)).buy(UnitType.SNIPER);
        verify(gameView).showMessage(anyString());
        gamePresenter.buyNewUnit(UnitType.HEAVY);
        verify(leftFortress).buy(UnitType.HEAVY);
    }

    @Test
    void buyUnitUpgradeTest(){
        GamePresenter gamePresenter = new GamePresenter(gameView,presenterFactory,leftFortress,rightFortress,clock);

        when(leftFortress.isAvailable(UnitUpgrade.SOLDIER)).thenReturn(true);
        when(leftFortress.isAffordable(UnitUpgrade.SOLDIER)).thenReturn(true);

        when(leftFortress.isAvailable(UnitUpgrade.HEAVY)).thenReturn(true);
        when(leftFortress.isAffordable(UnitUpgrade.HEAVY)).thenReturn(false);

        when(leftFortress.isAvailable(UnitUpgrade.SNIPER)).thenReturn(false);


        gamePresenter.buyUnitUpgrade(UnitUpgrade.SOLDIER);
        verify(leftFortress).buy(UnitUpgrade.SOLDIER);
        verify(gameView,times(0)).showMessage(anyString());

        gamePresenter.buyUnitUpgrade(UnitUpgrade.HEAVY);
        verify(leftFortress,times(0)).buy(UnitUpgrade.HEAVY);
        verify(gameView,times(1)).showMessage(anyString());

        gamePresenter.buyUnitUpgrade(UnitUpgrade.SNIPER);
        verify(leftFortress,times(0)).buy(UnitUpgrade.SNIPER);
        verify(gameView,times(2)).showMessage(anyString());

    }

    @Test
    void buyFortressUpgradeTest(){
        GamePresenter gamePresenter = new GamePresenter(gameView,presenterFactory,leftFortress,rightFortress,clock);

        when(leftFortress.isAvailable(FortressUpgrades.DAMAGE)).thenReturn(true);
        when(leftFortress.isAffordable(FortressUpgrades.DAMAGE)).thenReturn(true);

        when(leftFortress.isAvailable(FortressUpgrades.DEFENCE)).thenReturn(true);
        when(leftFortress.isAffordable(FortressUpgrades.DEFENCE)).thenReturn(false);

        when(leftFortress.isAvailable(FortressUpgrades.INCOME)).thenReturn(false);

        when(leftFortress.isAvailable(FortressUpgrades.RANGE)).thenReturn(true);
        when(leftFortress.isAffordable(FortressUpgrades.RANGE)).thenReturn(true);

        gamePresenter.buyFortressUpgrade(FortressUpgrades.DAMAGE);
        verify(leftFortress).buy(FortressUpgrades.DAMAGE);
        verify(gameView,times(0)).showMessage(anyString());

        gamePresenter.buyFortressUpgrade(FortressUpgrades.DEFENCE);
        verify(leftFortress,times(0)).buy(FortressUpgrades.DEFENCE);
        verify(gameView,times(1)).showMessage(anyString());

        gamePresenter.buyFortressUpgrade(FortressUpgrades.INCOME);
        verify(leftFortress,times(0)).buy(FortressUpgrades.INCOME);
        verify(gameView,times(2)).showMessage(anyString());

        gamePresenter.buyFortressUpgrade(FortressUpgrades.RANGE);
        verify(leftFortress).buy(FortressUpgrades.RANGE);
    }

    @Test
    void buyFortressActionTest(){
        GamePresenter gamePresenter = new GamePresenter(gameView,presenterFactory,leftFortress,rightFortress,clock);

        when(leftFortress.getHP()).thenReturn(1000);
        when(leftFortress.getMaxHP()).thenReturn(1000);
        gamePresenter.buyFortressAction(FortressActions.LARGE_HEAL);
        verify(gameView).showMessage(anyString());
        verify(leftFortress,times(0)).buy(FortressActions.LARGE_HEAL);

        when(leftFortress.getHP()).thenReturn(500);
        when(leftFortress.isAffordable(FortressActions.LARGE_HEAL)).thenReturn(true);
        gamePresenter.buyFortressAction(FortressActions.LARGE_HEAL);
        verify(gameView,times(1)).showMessage(anyString());
        verify(leftFortress).buy(FortressActions.LARGE_HEAL);

        when(leftFortress.isAffordable(FortressActions.SMALL_HEAL)).thenReturn(false);
        gamePresenter.buyFortressAction(FortressActions.SMALL_HEAL);
        verify(gameView,times(2)).showMessage(anyString());
        verify(leftFortress,times(0)).buy(FortressActions.SMALL_HEAL);

    }

    @Test
    void displayInfoTest(){
        GamePresenter gamePresenter = new GamePresenter(gameView,presenterFactory,leftFortress,rightFortress,clock);
        gamePresenter.displayUnitInfo(UnitType.HEAVY);
        verify(gameView).showMessage(anyString());

        when(leftFortress.isAvailable(UnitUpgrade.SOLDIER)).thenReturn(true);
        gamePresenter.displayUnitUpgradeInfo(UnitUpgrade.SOLDIER);
        verify(gameView,times(2)).showMessage(anyString());
        when(leftFortress.isAvailable(UnitUpgrade.SNIPER)).thenReturn(false);
        gamePresenter.displayUnitUpgradeInfo(UnitUpgrade.SNIPER);
        verify(gameView,times(3)).showMessage(anyString());

        when(leftFortress.isAvailable(FortressUpgrades.RANGE)).thenReturn(false);
        gamePresenter.displayFortressUpgradeInfo(FortressUpgrades.RANGE);
        verify(gameView,times(4)).showMessage(anyString());
        when(leftFortress.isAvailable(FortressUpgrades.INCOME)).thenReturn(true);
        gamePresenter.displayFortressUpgradeInfo(FortressUpgrades.INCOME);
        verify(gameView,times(5)).showMessage(anyString());

        gamePresenter.displayFortressActionInfo(FortressActions.LARGE_HEAL);
        verify(gameView,times(6)).showMessage(anyString());
    }


    @Test
    void testSpawnArmy(){
        GamePresenter gamePresenter = new GamePresenter(gameView,presenterFactory,leftFortress,rightFortress,clock);
        when(leftFortress.getHP()).thenReturn(400);
        when(rightFortress.getHP()).thenReturn(400);
        when(leftFortress.getMoney()).thenReturn(0);

        ArmyUnit armyUnit = mock(ArmyUnit.class);
        when(armyUnit.getUnitCoords()).thenReturn(0);
        when(armyUnit.getUnitTeam()).thenReturn(UnitTeam.LEFT);
        when(armyUnit.getUnitType()).thenReturn(UnitType.SOLDIER);
        when(armyUnit.getHP()).thenReturn(0);
        ArrayDeque<ArmyUnit> arrayDeque = new ArrayDeque<>();
        arrayDeque.add(armyUnit);

        when(leftFortress.getArmyUnits()).thenReturn(arrayDeque);
        when(rightFortress.getArmyUnits()).thenReturn(new ArrayDeque<>());
        gamePresenter.tick();
        verify(gameView,times(0)).finishGame(anyString());
        verify(armyUnit).getUnitCoords();
        verify(armyUnit).getUnitTeam();
        verify(armyUnit,atLeast(1)).getUnitType();
        verify(armyUnit).getHP();
    }



    @Test
    void checkIfGameFinished(){
        GamePresenter gamePresenter = new GamePresenter(gameView,presenterFactory,leftFortress,rightFortress,clock);
        when(leftFortress.getHP()).thenReturn(400);
        when(rightFortress.getHP()).thenReturn(400);
        when(leftFortress.getMoney()).thenReturn(0);
        when(leftFortress.getArmyUnits()).thenReturn(new ArrayDeque<>());
        when(rightFortress.getArmyUnits()).thenReturn(new ArrayDeque<>());

        gamePresenter.runGame();
        gamePresenter.tick();
        verify(gameView,times(0)).finishGame(anyString());

        when(rightFortress.getHP()).thenReturn(0);
        gamePresenter.tick();
        verify(gameView,times(1)).finishGame(anyString());

        when(leftFortress.getHP()).thenReturn(0);
        when(rightFortress.getHP()).thenReturn(400);
        gamePresenter.runGame();
        gamePresenter.tick();
        verify(gameView,times(2)).finishGame(anyString());

    }

}



