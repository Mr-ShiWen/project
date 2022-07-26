package thread.callable;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(2000);
        return "I am callable "+Thread.currentThread().getName();
    }
}
