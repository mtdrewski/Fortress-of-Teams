package javafxui;

import presentation.GameView;
import presentation.MenuView;
import presentation.ViewFactory;

public class WindowFactory implements ViewFactory {
    public MenuView createMenuView() {
        return new MenuViewImpl();
    }
    public GameView createGameView() { return new GameViewImpl(); }
}
