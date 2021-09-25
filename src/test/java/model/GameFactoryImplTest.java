package model;

import clock.Clock;
import model.fortress.Fortress;
import model.fortress.configs.DifficultyLevel;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GameFactoryImplTest {
    Clock clock = mock(Clock.class);
    private Fortress a,b;
    @Test
    void setupGame() {
        new GameFactoryImpl<Integer>(100, 1, 1).setupGame(clock, DifficultyLevel.EASY, this::consume );
        assertThat(a).isNotNull();
        assertThat(b).isNotNull();
    }

    private Integer consume(Fortress a,Fortress b){
        this.a = a;
        this.b = b;
        return 14;
    }
}