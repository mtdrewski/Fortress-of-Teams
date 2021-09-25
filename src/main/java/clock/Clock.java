package clock;

public interface Clock {
     void addToTick(Tickable tickable);
     void startClock();
     void stopClock();

     interface SleepFunction {
        void call(long mili, int nano) throws InterruptedException;
    }
}
