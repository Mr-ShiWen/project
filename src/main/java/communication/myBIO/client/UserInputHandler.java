package communication.myBIO.client;

import java.io.*;
import java.net.Socket;

public class UserInputHandler implements Runnable{
    private Socket socket;
    private BufferedWriter writer;
    private ChatClient client;

    public UserInputHandler(Socket socket,ChatClient client) {
        this.socket = socket;
        this.client=client;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    /**
     * 读取用户输入，写socket
     */
    public void run() {
        try {
            String msg=null;
            BufferedReader userReader=new BufferedReader(new InputStreamReader(System.in));
            while ((msg=userReader.readLine())!=null){
                writer.write(msg+"\n");
                writer.flush();
                if(msg.equals("quit")){
                    break;
                }
            }
            client.setShutDown(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
