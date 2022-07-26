package communication.myBIO.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatHandler implements Runnable{
    private Socket socket;
    private ChatServer server;

    public ChatHandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    /**
     * 读取输入，写入所有的其他socket中
     */
    public void run() {
        try {
            //加入map
            server.addSocket(socket);
            //开始信息接送与发送
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String msg=null;
            while ((msg=reader.readLine())!=null){
                String message="client["+socket.getPort()+"]: "+msg+"\n";
                server.sendMessage(socket,message);
                if(msg.equals("quit")){
                    server.removeSocket(socket);
                    reader.close();
                    break;
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
