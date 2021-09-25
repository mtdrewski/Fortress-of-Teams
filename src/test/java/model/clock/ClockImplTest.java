package model.clock;

import clock.Clock;
import clock.ClockImpl;
import clock.Tickable;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ClockImplTest {
    long secondNano = 1000000000; // nanoseconds in second

    @Test
    void addTest() throws Exception {
        double ticksPerSecond = 1;

        @SuppressWarnings("unchecked")
        Callable<Long> timeFunction = (Callable<Long>) mock(Callable.class);
        Clock.SleepFunction sleepFunction = mock(Clock.SleepFunction.class);
        ClockImpl clock = new ClockImpl(timeFunction, ticksPerSecond, sleepFunction);

        Tickable tickA = mock(Tickable.class);
        Tickable tickB = mock(Tickable.class);
        Tickable tickC = mock(Tickable.class);

        when(timeFunction.call()).then(invocation -> {
            clock.stopClock();
            return secondNano;
        });

        clock.addToTick(tickA);
        clock.startClock();
        verify(tickA, times(1)).tick();
        verify(tickB, times(0)).tick();
        verify(tickC, times(0)).tick();

        clock.addToTick(tickB);
        clock.startClock();
        verify(tickA, times(2)).tick();
        verify(tickB, times(1)).tick();
        verify(tickC, times(0)).tick();
    }

    @Test
    void repeatingAddTest() throws Exception {
        double ticksPerSecond = 1;

        @SuppressWarnings("unchecked")
        Callable<Long> timeFunction = (Callable<Long>) mock(Callable.class);
        ClockImpl.SleepFunction sleepFunction = mock(ClockImpl.SleepFunction.class);
        ClockImpl clock = new ClockImpl(timeFunction, ticksPerSecond, sleepFunction);

        Tickable tickA = mock(Tickable.class);

        when(timeFunction.call()).then(invocation -> {
            clock.stopClock();
            return secondNano;
        });

        clock.addToTick(tickA);
        clock.startClock();
        verify(tickA, times(1)).tick();

        clock.addToTick(tickA);
        clock.startClock();
        verify(tickA, times(2)).tick();
    }

    @Test
    void timeFunctionTest() throws Exception {
        double ticksPerSecond = 1;

        @SuppressWarnings("unchecked")
        Callable<Long> timeFunction = (Callable<Long>) mock(Callable.class);
        ClockImpl.SleepFunction sleepFunction = mock(ClockImpl.SleepFunction.class);
        ClockImpl clock = new ClockImpl(timeFunction, ticksPerSecond, sleepFunction);

        Tickable tickA = mock(Tickable.class);

        when(timeFunction.call()).thenReturn(secondNano).thenReturn(2 * secondNano).thenReturn((long) (2.5 * secondNano)).
                then(invocation -> {
                    clock.stopClock();
                    return 3 * secondNano;
                });

        clock.addToTick(tickA);
        clock.startClock();
        verify(tickA, times(3)).tick();
    }

    @Test
    void sleepTest() throws Exception {
        double ticksPerSecond = 1;

        @SuppressWarnings("unchecked")
        Callable<Long> timeFunction = (Callable<Long>) mock(Callable.class);
        ClockImpl.SleepFunction sleepFunction = mock(ClockImpl.SleepFunction.class);
        ClockImpl clock = new ClockImpl(timeFunction, ticksPerSecond, sleepFunction);

        Tickable tickA = mock(Tickable.class);

        when(timeFunction.call()).thenReturn(secondNano).thenReturn(2 * secondNano).thenReturn((3 * secondNano)).
                then(invocation -> {
                    clock.stopClock();
                    return 4 * secondNano;
                });
        doAnswer(invocation -> {
            assertEquals((long) (secondNano / ticksPerSecond), (long) invocation.getArgument(0) * 1000000 + (int) invocation.
                    getArgument(1));
            return null;
        }).when(sleepFunction).call(anyLong(), anyInt());

        clock.addToTick(tickA);
        clock.startClock();
        verify(tickA, times(4)).tick();
    }

    @Test
    void irregularSleepTest() throws Exception {
        double ticksPerSecond = 1;

        @SuppressWarnings("unchecked")
        Callable<Long> timeFunction = (Callable<Long>) mock(Callable.class);
        ClockImpl.SleepFunction sleepFunction = mock(ClockImpl.SleepFunction.class);
        ClockImpl clock = new ClockImpl(timeFunction, ticksPerSecond, sleepFunction);

        Tickable tickA = mock(Tickable.class);
        when(timeFunction.call()).thenReturn(secondNano).thenReturn((long) (1.5 * secondNano)).thenReturn((long) (2.5 * secondNano)).
                then(invocation -> {
                    clock.stopClock();
                    return 4 * secondNano;
                });

        clock.addToTick(tickA);
        clock.startClock();

        verify(tickA, times(3)).tick();
        verify(sleepFunction, times(2)).call(1000, 0);
        verify(sleepFunction, times(2)).call(500, 0);
    }

    @Test
    void getTimeExceptionTest() throws Exception {
        double ticksPerSecond = 1;

        @SuppressWarnings("unchecked")
        Callable<Long> timeFunction = (Callable<Long>) mock(Callable.class);
        Clock.SleepFunction sleepFunction = mock(Clock.SleepFunction.class);
        Clock clock = new ClockImpl(timeFunction, ticksPerSecond, sleepFunction);
        when(timeFunction.call()).thenThrow(Exception.class);

        Tickable tickA = mock(Tickable.class);

        clock.addToTick(tickA);
        clock.startClock();

        verify(tickA, times(0)).tick();
    }

    @Test
    void sleepFunctionExceptionTest() throws Exception {
        double ticksPerSecond = 1;

        @SuppressWarnings("unchecked")
        Callable<Long> timeFunction = (Callable<Long>) mock(Callable.class);
        Clock.SleepFunction sleepFunction = mock(Clock.SleepFunction.class);
        Clock clock = new ClockImpl(timeFunction, ticksPerSecond, sleepFunction);

        Tickable tickA = mock(Tickable.class);

        when(timeFunction.call()).thenReturn(secondNano).thenReturn(2 * secondNano).thenReturn((3 * secondNano)).
                then(invocation -> {
                    clock.stopClock();
                    return 4 * secondNano;
                });
        doAnswer(invocation -> {
            throw  new InterruptedException();
        }).when(sleepFunction).call(anyLong(), anyInt());

        clock.addToTick(tickA);
        clock.startClock();

        verify(tickA, times(4)).tick();
    }

    @Test
    void irregularSleepWithExceptionTest() throws Exception {
        double ticksPerSecond = 1;

        @SuppressWarnings("unchecked")
        Callable<Long> timeFunction = (Callable<Long>) mock(Callable.class);
        Clock.SleepFunction sleepFunction = mock(Clock.SleepFunction.class);
        Clock clock = new ClockImpl(timeFunction, ticksPerSecond, sleepFunction);

        Tickable tickA = mock(Tickable.class);

        when(timeFunction.call()).thenReturn(secondNano).thenReturn((long) (1.5 * secondNano)).thenReturn((long) (2.5 * secondNano)).
                then(invocation -> {
                    clock.stopClock();
                    return 4 * secondNano;
                });

        doAnswer(invocation -> {
            throw  new InterruptedException();
        }).when(sleepFunction).call(anyLong(), anyInt());

        clock.addToTick(tickA);
        clock.startClock();

        verify(tickA, times(3)).tick();
        verify(sleepFunction, times(2)).call(1000, 0);
        verify(sleepFunction, times(2)).call(500, 0);
    }

    @Test
    void noSleepTimeTest() throws Exception {
        double ticksPerSecond = 1;

        @SuppressWarnings("unchecked")
        Callable<Long> timeFunction = (Callable<Long>) mock(Callable.class);
        Clock.SleepFunction sleepFunction = mock(Clock.SleepFunction.class);
        Clock clock = new ClockImpl(timeFunction, ticksPerSecond, sleepFunction);

        Tickable tickA = mock(Tickable.class);

        when(timeFunction.call()).thenReturn(secondNano).thenReturn(3 * secondNano).
                then(invocation -> {
                    clock.stopClock();
                    return 5 * secondNano;
                });

        clock.addToTick(tickA);
        clock.startClock();

        verify(tickA, times(3)).tick();
        verify(sleepFunction, times(1)).call(1000, 0);
        verify(sleepFunction, times(1)).call(anyLong(), anyInt());
    }
}
