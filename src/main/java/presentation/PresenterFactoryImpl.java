package presentation;


import clock.Clock;
import clock.ClockImpl;
import model.GameFactory;
import model.GameFactoryImpl;
import model.fortress.configs.DifficultyLevel;

public class PresenterFactoryImpl implements PresenterFactory {

    private final ViewFactory views;
    private final GameFactory<GamePresenter> gameFactory;
    public PresenterFactoryImpl(ViewFactory views, GameFactory<GamePresenter> gameFactory){
        this.views=views;
        this.gameFactory = gameFactory;
    }

    public void openMenuView() {
        MenuView menuView= views.createMenuView();
        MenuPresenter menuPresenter = new MenuPresenter(menuView,this);
        menuView.initialize(menuPresenter);
        menuPresenter.open();
    }

    public void openGameView(DifficultyLevel difficultyLevel){
        GameView gameView = views.createGameView();
        GamePresenter gamePresenter = setUpGamePresenter(gameView, difficultyLevel);
        gameView.initialize(gamePresenter);
        gamePresenter.open();
        gamePresenter.runGame();
    }


    private GamePresenter setUpGamePresenter(GameView gameView, DifficultyLevel difficultyLevel) {

        Clock clock = new ClockImpl(System::nanoTime,30, Thread::sleep);

        GamePresenter gamePresenter = gameFactory.setupGame(
                clock,
                difficultyLevel,
                (f1,f2)->new GamePresenter(
                    gameView,
                    this,
                    f1,
                    f2,
                    clock)
            );

        clock.addToTick(gamePresenter);
        return gamePresenter;
    }
}
