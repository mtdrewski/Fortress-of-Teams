package presentation;

import clock.Clock;
import model.GameFactory;
import model.fortress.configs.DifficultyLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.mockito.Mockito.*;

public class PresenterFactoryImplTest {

    @Mock ViewFactory views;
    @Mock GameFactory<GamePresenter> gameFactory;
    @Mock GamePresenter gamePresenter;
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testOpenMenuView(){
        MenuView menuView = mock(MenuView.class);
        when(views.createMenuView()).thenReturn(menuView);
        new PresenterFactoryImpl(views, gameFactory).openMenuView();
        verify(menuView).initialize(any());
    }

    @Test
    void testOpenGameView(){
        GameView gameView = mock(GameView.class);
        when(views.createGameView()).thenReturn(gameView);
        when(gameFactory.setupGame(any(), any(), any())).thenReturn(gamePresenter);
        new PresenterFactoryImpl(views, gameFactory).openGameView(DifficultyLevel.EASY);
        verify(gameFactory).setupGame(any(), eq(DifficultyLevel.EASY), any());
        verify(gameView).initialize(gamePresenter);
        verify(gamePresenter).open();
        verify(gamePresenter).runGame();
    }

}
