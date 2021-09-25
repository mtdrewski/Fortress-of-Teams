package presentation;

import model.fortress.configs.DifficultyLevel;

public class MenuPresenter {

    private final MenuView menuView;
    private final PresenterFactory presenterFactory;

    MenuPresenter(MenuView menuView, PresenterFactory presenterFactoryImpl){
        this.menuView=menuView;
        this.presenterFactory = presenterFactoryImpl;
    }

    public void openGameView(DifficultyLevel difficultyLevel){
        presenterFactory.openGameView(difficultyLevel);
    }

    public void open(){
        menuView.open();
    }

    public void close(){
        menuView.close();
    }
}
