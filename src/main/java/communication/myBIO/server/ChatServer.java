package communication.myBIO.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private static final int DEFAULTPORT=8888;
    private Map<Integer, Writer> socketWriterMap=new HashMap<>();
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    /**
     * 增加socket
     * @param socket
     */
    public void addSocket(Socket socket){
        try {
            Writer writer=new OutputStreamWriter(socket.getOutputStream());
            int port = socket.getPort();
            socketWriterMap.put(port,writer);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 删除socket
     * @param socket
     */
    public void removeSocket(Socket socket){
        int port = socket.getPort();
        Writer writer = socketWriterMap.get(port);
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        socketWriterMap.remove(port);
    }

    /**
     * 给除了该socket外所有socket发送信息
     * @param socket
     * @param message
     */
    public void sendMessage(Socket socket, String message){
        int myPort = socket.getPort();
        for (Map.Entry<Integer, Writer> entry : socketWriterMap.entrySet()) {
            if(entry.getKey()==myPort) continue;
            try {
                Writer writer=entry.getValue();
                writer.write(message);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 启动ChatServer
     */
    private void start(){
        try {
            //创建ServerSocket
            ServerSocket serverSocket=new ServerSocket(DEFAULTPORT);
            System.out.println("ChatServer端口已经建立，运行在 " + DEFAULTPORT + " 端口");

            //不断获取连接
            while (true){
                Socket socket=serverSocket.accept();
                threadPool.execute(new ChatHandler(socket,this));
                System.out.println("客户端["+socket.getPort()+"]上线了");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ChatServer().start();
    }

}
