package presentation;

import model.fortress.configs.DifficultyLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MenuPresenterTest {

    MenuView menuView;
    PresenterFactory presenterFactory;

    @BeforeEach
    void setup(){
        menuView = mock(MenuView.class);
        presenterFactory = mock(PresenterFactory.class);
    }

    @Test
    void testClose(){
        new MenuPresenter(menuView,presenterFactory).close();
        verify(menuView).close();
    }

    @Test
    void testOpen(){
        new MenuPresenter(menuView,presenterFactory).open();
        verify(menuView).open();
    }

    @Test
    void openGameViewTest(){
        new MenuPresenter(menuView,presenterFactory).openGameView(DifficultyLevel.EASY);
        verify(presenterFactory).openGameView(DifficultyLevel.EASY);
        new MenuPresenter(menuView,presenterFactory).openGameView(DifficultyLevel.HARD);
        verify(presenterFactory).openGameView(DifficultyLevel.HARD);
    }

}
