package model.player;

import java.util.ArrayDeque;
import java.util.concurrent.Callable;

public class ChainExecutorImpl implements ChainExecutor {
    private ArrayDeque<Callable<Boolean>> calls = new ArrayDeque<>();

    public boolean execute()  {
        for (Callable<Boolean> call : calls) {
            try {
                if (call.call()) {
                    return true;
                }
            }
            catch (Exception ignored) {
            }
        }
        return false;
    }

    public ChainExecutor firstDo(Callable<Boolean> call) {
        reset();
        calls.addLast(call);
        return this;
    }

    public ChainExecutor elseDo(Callable<Boolean> call) {
        calls.addLast(call);
        return this;
    }

    private void reset() {
        calls = new ArrayDeque<>();
    }
}
