package thread;

import thread.callable.HelloCallable;
import thread.runnable.ClockRunnable;
import thread.runnable.HelloRunnable;

import java.util.concurrent.*;

public class ThreadMain {
    private static int core_thread_count=5;
    private static int max_thread_count=10;
    private static long keep_alive_time=1l;
    private static int queue_capacity=100;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
/*        ThreadPoolExecutor executor=new ThreadPoolExecutor(
                core_thread_count,
                max_thread_count,
                keep_alive_time,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queue_capacity),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        ReentrantLock lock=new ReentrantLock();
        BlockingQueue blockingQueue;
        AtomicInteger integer=null;

        Collection collection=new HashSet<String>();

        TreeMap<String,String> treeMap=new TreeMap<>();


        RunnableFuture runnableFuture=null;
        FutureTask futureTask=null;

        Runnable myRunnable = new MyRunnable();
        Thread thread1=new Thread(myRunnable);
        thread1.start();

        Thread.sleep(200);

        Thread thread2=new Thread(myRunnable);
        thread2.start();

        Thread.sleep(200);
        System.out.println("主线程执行完了");*/


        /*Runnable runnable=new HelloRunnable();
        Callable<String> callable=new HelloCallable();
        FutureTask<String> futureTask=new FutureTask<>(callable);
        Thread thread=new Thread(futureTask);
        thread.start();
        String s=futureTask.get();
        System.out.println("main线程执行完毕");
        System.out.println("futureTask结果："+s);*/

        System.out.println("main准备创建线程");
        createThread();
        System.out.println("main创建线程完毕");
    }

    private static void createThread(){
        ClockRunnable clockRunnable = new ClockRunnable();

        Thread thread=new Thread(clockRunnable);

        thread.start();
    }

}
