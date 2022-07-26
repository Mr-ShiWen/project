package communication.MyNIO.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ChatClient {
    private final int serverPort=8888;
    private Charset charset=StandardCharsets.UTF_8;

    private final int BufferCapacity=1024;
    private ByteBuffer readBuffer;//写模式
    private ByteBuffer writeBuffer;//读模式

    private Selector selector;
    private SocketChannel socketChannel;


    public static void main(String[] args) {
        new ChatClient().start();
    }

    public ChatClient() {
        readBuffer=ByteBuffer.allocate(BufferCapacity);
        writeBuffer=ByteBuffer.allocate(BufferCapacity);
        writeBuffer.flip();
    }

    private void start()  {
        try {
            selector=Selector.open();
            socketChannel=SocketChannel.open();
            socketChannel.configureBlocking(false);//设为非阻塞模式

            socketChannel.register(selector, SelectionKey.OP_CONNECT);//关注读
            socketChannel.connect(new InetSocketAddress("localhost",serverPort));

            //本线程负责读取本channel并输出到终端
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
        }catch (ClosedSelectorException e){

        }

    }

    private void handleKey(SelectionKey key) throws IOException {
        if(key.isConnectable()){
            SocketChannel client = (SocketChannel) key.channel();
            //finishConnect()返回true，说明和服务器已经建立连接。如果是false，说明还在连接中，还没完全连接完成
            if(client.finishConnect()){
                //新建一个新线程去等待用户输入
                new Thread(new UserInputHandler(this)).start();
                //连接建立完成后，注册read事件，开始监听服务器转发的消息
                key.interestOps(SelectionKey.OP_READ);
            }
        }

        if(key.isReadable()){
            String msg = receiveMsg();
            System.out.println(msg);
        }
    }

    private String receiveMsg() throws IOException {
        List<Byte> list=new ArrayList<>();
        while (socketChannel.read(readBuffer)>0){
            //输出信息，清空
            readBuffer.flip();//读模式
            while (readBuffer.hasRemaining()) list.add(readBuffer.get());
            readBuffer.flip();//写模式
            readBuffer.clear();
        }
        byte[] bytes=new byte[list.size()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i]=list.get(i);
        }
        return new String(bytes,charset);
    }

    public void sendMsg(String msg) throws IOException {
        writeBuffer.flip();//写模式
        writeBuffer.clear();
        writeBuffer.put(msg.getBytes(charset));
        writeBuffer.flip();//读模式
        while (writeBuffer.hasRemaining()) socketChannel.write(writeBuffer);

        if("quit".equals(msg)){
            //关闭通道，key跟着取消
            selector.close();
        }
    }
}
