package model.player.ai;

import model.fortress.Fortress;
import model.path.FortressPath;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class AIPlayerTest {
    @Test
    void tickTest() {
        Fortress fortress = mock(Fortress.class);
        FortressPath path = mock(FortressPath.class);
        when(fortress.getPath()).thenReturn(path);
        PathRater pathRater = mock(PathRater.class);
        when(pathRater.ratePath(path)).thenReturn(PathRating.ADVANTAGE);
        AIStrategy strategy = mock(AIStrategy.class);
        AIPlayer player = new AIPlayer(fortress, pathRater, strategy);

        player.tick();

        verify(pathRater).ratePath(path);
        verify(strategy).decide(PathRating.ADVANTAGE);
    }
}