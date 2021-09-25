package model.player;

import model.fortress.Fortress;
import model.player.ai.*;

public class AIPlayerFactoryImpl implements AIPlayerFactory {
    private final Fortress fortress;

    public AIPlayerFactoryImpl(Fortress fortress) {
        this.fortress = fortress;
    }

    public AIPlayer createAIPlayer() {
        ChainExecutor chainExecutor = new ChainExecutorImpl();
        PathRater pathRater = new PathRaterImpl(fortress.getTeam());
        AIStrategy aiStrategy = new AIStrategyImpl(fortress, chainExecutor);
        return new AIPlayer(fortress, pathRater, aiStrategy);
    }
}
