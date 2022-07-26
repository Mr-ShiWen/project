package communication.myBIO.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatClient {
    private boolean shutDown=false;

    public void setShutDown(boolean shutDown) {
        this.shutDown = shutDown;
    }

    private void start(){
        try {
            //建立socket连接
            Socket socket=new Socket("127.0.0.1",8888);
            //创建一个线程执行（读取用户输入，写入socket）任务
            Thread writeThread=new Thread(new UserInputHandler(socket,this));
            writeThread.start();
            //本线程执行读任务
            BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg=null;
            while ((msg=reader.readLine())!=null){
                if(shutDown) {
                    reader.close();
                    break;
                }
                System.out.println(msg);
            }
            //关闭连接
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ChatClient().start();
    }

}
