package communication.MyNIO.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ChatServer {
    private int port;//不写死，由构造函数传入
    private ByteBuffer readBuffer;//处于写模式，空
    private ByteBuffer writeBuffer;//处于读模式，空
    private Charset charset= StandardCharsets.UTF_8;


    public static void main(String[] args) {
        new ChatServer(8888).start();
    }

    public ChatServer(int port) {
        this.port = port;
        readBuffer=ByteBuffer.allocate(1024);
        writeBuffer=ByteBuffer.allocate(1024);
        writeBuffer.flip();
    }

    public void start() {
        try (Selector selector=Selector.open(); ServerSocketChannel ssChanel=ServerSocketChannel.open();){

            ssChanel.socket().bind(new InetSocketAddress("localhost",port));
            ssChanel.configureBlocking(false);//设置为非阻塞模式

            ssChanel.register(selector, SelectionKey.OP_ACCEPT);

            while (true){
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey key : selectionKeys) {
                    handleKey(key);
                }
                selectionKeys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleKey(SelectionKey key) throws IOException {
        Selector selector = key.selector();

        if(key.isAcceptable()){
            ServerSocketChannel ssChanel = (ServerSocketChannel) key.channel();
            SocketChannel sChanel = ssChanel.accept();
            sChanel.configureBlocking(false);
            sChanel.register(selector,SelectionKey.OP_READ);
            System.out.println("客户端["+sChanel.socket().getPort()+"] 上线了");
        }

        if(key.isReadable()){
            SocketChannel sChannel = (SocketChannel) key.channel();
            //接受消息
            String msg=null;
            try {
                msg = receiveMsg(sChannel);
            }catch (IOException e){
                //远程主机强迫关闭连接
                e.printStackTrace();
                key.cancel();
                return;
            }
            System.out.println("客户端["+sChannel.socket().getPort()+"]:"+msg);
            //发送消息
            sendMes(sChannel,msg,selector);
            //判断用户是否退出
            if ("quit".equals(msg)){
                //取消key
                key.cancel();
                System.out.println("客户端[" + sChannel.socket().getPort() + "]下线了！");
            }
        }
    }

    private String receiveMsg(SocketChannel sChanel) throws IOException {
        List<Byte> list=new ArrayList<>();

        while (sChanel.read(readBuffer)>0){
            //输出数据，并清空
            readBuffer.flip();//置为读
            while (readBuffer.hasRemaining()){
                list.add(readBuffer.get());
            }
            readBuffer.flip();//置为写
            readBuffer.clear();
        }

        byte[] bytes=new byte[list.size()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i]=list.get(i);
        }
        return new String(bytes,charset);
    }

    private void sendMes(SocketChannel sChannel, String msg, Selector selector) throws IOException {
        String sendMsg="客户端["+sChannel.socket().getPort()+"]:"+msg;

        Set<SelectionKey> keys = selector.keys();
        for (SelectionKey key : keys) {
            SelectableChannel channel = key.channel();
            if(!(channel instanceof ServerSocketChannel)&&channel!=sChannel&&key.isValid()){
                SocketChannel otherChannel=(SocketChannel)channel;
                writeBuffer.flip();//切为写模式
                writeBuffer.clear();
                writeBuffer.put(sendMsg.getBytes(charset));
                writeBuffer.flip();//切为读模式
                while (writeBuffer.hasRemaining()){
                    otherChannel.write(writeBuffer);
                }
            }
        }
    }
}
