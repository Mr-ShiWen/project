package thread.runnable;

public class HelloRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Hello world! I am HelloRunnable");
    }
}
