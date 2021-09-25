package model.player;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ChainExecutorImplTest {

    @SuppressWarnings("unchecked")
    private <T> Callable<T> mockCallable() {
        return mock(Callable.class);
    }

    @Test
    void allFalseTest() throws Exception {
        ChainExecutorImpl chainExecutor = new ChainExecutorImpl();
        Callable<Boolean> callable = mockCallable();
        when(callable.call()).thenReturn(false);

        boolean result = chainExecutor.firstDo(callable).elseDo(callable).elseDo(callable).execute();

        verify(callable, times(3)).call();
        assertFalse(result);
    }

    @Test
    void someFalseTest() throws Exception {
        ChainExecutorImpl chainExecutor = new ChainExecutorImpl();
        Callable<Boolean> falseCallable = mockCallable();
        when(falseCallable.call()).thenReturn(false);
        Callable<Boolean> trueCallable = mockCallable();
        when(trueCallable.call()).thenReturn(true);

        boolean result = chainExecutor.firstDo(falseCallable).elseDo(trueCallable).elseDo(trueCallable).execute();

        verify(falseCallable, times(1)).call();
        verify(trueCallable, times(1)).call();
        assertTrue(result);
    }

    @Test
    void exceptionTest() throws Exception {
        ChainExecutorImpl chainExecutor = new ChainExecutorImpl();
        Callable<Boolean> throwingCallable = mockCallable();
        when(throwingCallable.call()).thenThrow(Exception.class);

        boolean result = chainExecutor.firstDo(throwingCallable).elseDo(throwingCallable).elseDo(throwingCallable).execute();

        verify(throwingCallable, times(3)).call();
        assertFalse(result);
    }

    @Test
    void twoFirstDoTest() throws Exception {
        ChainExecutorImpl chainExecutor = new ChainExecutorImpl();
        Callable<Boolean> callable = mockCallable();
        when(callable.call()).thenReturn(false);

        chainExecutor.firstDo(callable).elseDo(callable).elseDo(callable);
        boolean result = chainExecutor.firstDo(callable).elseDo(callable).execute();

        verify(callable, times(2)).call();
        assertFalse(result);
    }
}