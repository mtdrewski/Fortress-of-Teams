package presentation;

import model.fortress.configs.DifficultyLevel;

public interface PresenterFactory {
    void openMenuView();
    void openGameView(DifficultyLevel difficultyLevel);
}
