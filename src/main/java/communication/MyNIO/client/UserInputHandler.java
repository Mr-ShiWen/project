package communication.MyNIO.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInputHandler implements Runnable {
    private ChatClient client;

    public UserInputHandler(ChatClient client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            while (true){
                //获取用户输入
                BufferedReader bfReader=new BufferedReader(new InputStreamReader(System.in));
                String msg=bfReader.readLine();
                client.sendMsg(msg);
                if("quit".equals(msg)) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
