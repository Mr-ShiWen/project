package io.nio.selector;

import communication.simple.Server;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class MySelectorTest {

    @Test
    public void test_server() throws IOException {
        //创建selector
        Selector selector=Selector.open();

        //创建socketChannel，并设为非阻塞模式
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost",8000));
        serverSocketChannel.configureBlocking(false);

        //注册socketChannel到selector，设置关心的操作是accept
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);

        //创建读写buffer
        ByteBuffer readBuffer=ByteBuffer.allocate(3);//处于写模式，并为空
        ByteBuffer writeBuffer=ByteBuffer.allocate(100);//处于读模式，并处于内容开头
        writeBuffer.put("Hello, I am socketChannel from server".getBytes());
        writeBuffer.flip();

        //开始循环监控
        while (true){
            //执行select操作
            selector.select();
            //获取selectionKey
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            System.out.println("开始selectedKeys:"+selectionKeys.size());
            //迭代selectionKey，处理每个就绪通道并移出已选集
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();

                if(key.isAcceptable()){//可accept
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                }

                if(key.isReadable()){//可读
                    SocketChannel socketChannel = (SocketChannel)key.channel();
                    int rdNum = socketChannel.read(readBuffer);//readerBuffer保留上一次读取的内容

                    StringBuilder sb=new StringBuilder();
                    while (rdNum!=0&&rdNum!=-1){//输出内容，并重新读取
                        readBuffer.flip();//切为读模式
                        while (readBuffer.hasRemaining()){
                            sb.append((char) readBuffer.get());
                        }
                        readBuffer.flip();//切位写模式
                        readBuffer.clear();//清空重写
                        rdNum=socketChannel.read(readBuffer);//重新读
                    }
                    System.out.println("  接受消息："+sb.toString());

                    readBuffer.clear();
                    if(rdNum==-1){//对方关闭连接，那就取消key
                        key.cancel();
                        continue;
                    }
                }

                if(key.isWritable()){//可写
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    socketChannel.write(writeBuffer);
                    writeBuffer.clear();//恢复重新读
                }
            }

            System.out.println("结束selectionKeys:"+selectionKeys.size());
            System.out.println();
        }

    }



    @Test
    public void test_client() throws IOException {
        SocketChannel socketChannel=SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8000));

        ByteBuffer writeBuffer=ByteBuffer.allocate(100);
        writeBuffer.put("hello world, I am Jack from client".getBytes());
        writeBuffer.flip();//切换为读模式

        socketChannel.write(writeBuffer);
        socketChannel.close();
    }

}
