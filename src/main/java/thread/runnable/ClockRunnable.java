package thread.runnable;



public class ClockRunnable implements Runnable {

    @Override
    public void run() {
        int count=0;
        while (count<10){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
            System.out.println(count+" sleep");
        }
    }
}
