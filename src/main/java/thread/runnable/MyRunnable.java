package thread.runnable;

public class MyRunnable implements Runnable{
    private Object obj1=new Object();
    private Object obj2=new Object();
    private boolean first=true;

    @Override
    public void run() {
        synchronized (this){
            try {
                System.out.println("将在this这里 wait");
                notifyAll();
                this.wait();
                System.out.println(Thread.currentThread().getName()+" 被唤醒了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
