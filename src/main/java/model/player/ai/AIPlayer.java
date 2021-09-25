package model.player.ai;

import clock.Tickable;
import model.fortress.Fortress;
import model.path.FortressPath;

public class AIPlayer implements Tickable {
    private final Fortress fortress;
    private final PathRater pathRater;
    private final AIStrategy strategy;

    public AIPlayer(Fortress fortress, PathRater pathRater, AIStrategy strategy){
        this.fortress = fortress;
        this.pathRater = pathRater;
        this.strategy = strategy;
    }

    public void tick() {
        FortressPath path = fortress.getPath();
        PathRating pathRating = pathRater.ratePath(path);
        strategy.decide(pathRating);
    }
}
