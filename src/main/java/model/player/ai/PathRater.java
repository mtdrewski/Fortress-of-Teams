package model.player.ai;

import model.path.FortressPath;

public interface PathRater {
    PathRating ratePath(FortressPath path);
}
