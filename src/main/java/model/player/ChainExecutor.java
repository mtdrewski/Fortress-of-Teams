package model.player;

import java.util.concurrent.Callable;

public interface ChainExecutor {
    boolean execute ();
    ChainExecutor firstDo (Callable<Boolean> call);
    ChainExecutor elseDo (Callable<Boolean> call);
}
