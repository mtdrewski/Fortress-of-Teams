package clock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ClockImpl implements Clock{
    private final List<Tickable> tickableList = new ArrayList<>();

    private final SleepFunction sleepFunction;
    private final Callable<Long> getTime; // get time in nanoseconds
    private final double ticksPerSecond;
    private volatile boolean stop;

    public ClockImpl(Callable<Long> getTime, double ticksPerSecond, SleepFunction sleepFunction) {
        this.getTime = getTime;
        this.ticksPerSecond = ticksPerSecond;
        this.sleepFunction = sleepFunction;
    }

    public void addToTick(Tickable tickable) {
        if (!tickableList.contains(tickable)) {
            tickableList.add(tickable);
        }
    }

    public void startClock() {
        stop = false;
        long secondNano = 1000000000; // nanoseconds in second
        long miliNano = 1000000; // nanoseconds in milisecond
        long tickTime = (long) (secondNano / ticksPerSecond); // time for one tick
        long startTime = -1; // starting time of last tick
        long waitTime; // waiting time from last tick
        long nowTime;
        try {
            while (!stop) {
                nowTime = getTime.call();
                waitTime = nowTime - startTime;
                // at the start
                if (startTime < 0) {
                    startTime = nowTime;
                    waitTime = tickTime;
                }

                // if it is time to tick everybody
                if (waitTime >= tickTime) {
                    for (Tickable tickable : tickableList) {
                        tickable.tick();
                    }
                    startTime = nowTime - tickTime + waitTime;
                    long sleepTime = 2 * tickTime - waitTime;
                    if (sleepTime > 0) {
                        try {
                            sleepFunction.call(sleepTime / miliNano, (int) (sleepTime % miliNano));
                        } catch (InterruptedException ignored) {
                        }
                    } else {
                        startTime = nowTime;
                    }
                }

                // put to sleep
                else {
                    long sleepTime = tickTime - waitTime;
                    if (sleepTime > 0) {
                        try {
                            sleepFunction.call(sleepTime / miliNano, (int) (sleepTime % miliNano));
                        } catch (InterruptedException ignored) {
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopClock() {
        stop = true;
    }


}
