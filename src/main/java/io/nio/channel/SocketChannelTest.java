package io.nio.channel;


import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class SocketChannelTest {

    public void test_ServerSocketChannel() throws IOException {
        //1.通过ServerSocketChannel 的open()方法创建一个ServerSocketChannel对象，open方法的作用：打开套接字通道
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //2.通过ServerSocketChannel绑定ip地址和port(端口号)
        ssc.socket().bind(new InetSocketAddress("127.0.0.1", 3333));

        //通过ServerSocketChannelImpl的accept()方法创建一个SocketChannel对象用户从客户端读/写数据
        SocketChannel socketChannel = ssc.accept();

        //3.创建写数据的缓存区对象
        ByteBuffer writeBuffer = ByteBuffer.allocate(128);
        writeBuffer.put("hello WebClient this is from WebServer".getBytes());
        writeBuffer.flip();
        socketChannel.write(writeBuffer);

        //4.创建读数据的缓存区对象
        ByteBuffer readBuffer = ByteBuffer.allocate(128);
        socketChannel.read(readBuffer);
        StringBuilder stringBuffer=new StringBuilder();

        //5.将Buffer从写模式变为可读模式
        readBuffer.flip();
        while (readBuffer.hasRemaining()) {
            stringBuffer.append((char) readBuffer.get());
        }
        System.out.println("从客户端接收到的数据："+stringBuffer);
        socketChannel.close();
        ssc.close();
    }

    public void test_SocketChannel() throws IOException {
        //1.通过SocketChannel的open()方法创建一个SocketChannel对象
        SocketChannel socketChannel = SocketChannel.open();
        //2.连接到远程服务器（连接此通道的socket）
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 3333));

        // 3.创建写数据缓存区对象
        ByteBuffer writeBuffer = ByteBuffer.allocate(128);
        writeBuffer.put("hello WebServer this is from WebClient".getBytes());
        writeBuffer.flip();
        socketChannel.write(writeBuffer);

        //4.创建读数据缓存区对象
        ByteBuffer readBuffer = ByteBuffer.allocate(128);
        socketChannel.read(readBuffer);

        //String 字符串常量，不可变；StringBuffer 字符串变量（线程安全），可变；StringBuilder 字符串变量（非线程安全），可变
        StringBuilder stringBuffer=new StringBuilder();
        //5.将Buffer从写模式变为可读模式
        readBuffer.flip();
        while (readBuffer.hasRemaining()) {
            stringBuffer.append((char) readBuffer.get());
        }
        System.out.println("从服务端接收到的数据："+stringBuffer);

        socketChannel.close();
    }

    @Test
    public void test_ServerSocketChannel1() throws IOException {
        int port=6668;

        //建立ServerSocketChannel
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress("localhost",port));

        //获取socketChannel
        SocketChannel socketChannel = serverSocketChannel.accept();

        //写
        ByteBuffer writeBuffer=ByteBuffer.allocate(100);
        writeBuffer.put("Hello,I am ServerChannel".getBytes());
        writeBuffer.flip();
        socketChannel.write(writeBuffer);

        //读
        ByteBuffer readBuffer=ByteBuffer.allocate(20);
        int rdNum = socketChannel.read(readBuffer);

        StringBuilder sb = new StringBuilder();
        while (rdNum!=-1){//输出readBuffer，并重新读取
            readBuffer.flip();//切换为读模式
            while (readBuffer.hasRemaining()){
                sb.append((char)readBuffer.get());
            }
            readBuffer.flip();//切换为写模式
            readBuffer.clear();//从头写
            rdNum=socketChannel.read(readBuffer);
        }
        System.out.println("rdNum："+rdNum);
        System.out.println("imformation from client: "+sb.toString());

    }

    @Test
    public void test_SocketChannel1() throws IOException {
        int port=6668;

        //创建SocketChannel
        SocketChannel socketChannel=SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",port));

        //写
        ByteBuffer writeBuffer=ByteBuffer.allocate(100);
        writeBuffer.put("Hello,I am client".getBytes());
        writeBuffer.flip();
        socketChannel.write(writeBuffer);

        //读
        ByteBuffer readBuffer=ByteBuffer.allocate(100);
        socketChannel.read(readBuffer);


        //输出读取内容
        readBuffer.flip();
        StringBuilder sb=new StringBuilder();
        while (readBuffer.hasRemaining()){
            sb.append((char) readBuffer.get());
        }
        System.out.println("imformation from server: "+sb.toString());

        socketChannel.close();
    }
}
