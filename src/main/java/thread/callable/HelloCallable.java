package thread.callable;

import java.util.concurrent.Callable;

public class HelloCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
//        Thread.sleep(2000);
        return "Hello world, I am HelloCallable!";
    }
}
