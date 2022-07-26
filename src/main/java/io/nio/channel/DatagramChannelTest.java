package io.nio.channel;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DatagramChannelTest {

    @Test
    public void test_DatagramChannel_send() throws IOException {
        //1.通过DatagramChannel的open()方法创建一个DatagramChannel对象
        DatagramChannel datagramChannel = DatagramChannel.open();
        //绑定一个port（端口）
        datagramChannel.bind(new InetSocketAddress(1234));

        //写
        ByteBuffer writeBuffer =ByteBuffer.allocate(100);
        writeBuffer.put("Hello, I am dataGramChannel_1234".getBytes());
        writeBuffer.flip();
        datagramChannel.send(writeBuffer,new InetSocketAddress("localhost",4321));

        System.in.read();
    }

    @Test
    public void test_DatagramChannel_recieve() throws IOException {
        //1.通过DatagramChannel的open()方法创建一个DatagramChannel对象
        DatagramChannel datagramChannel = DatagramChannel.open();
        //绑定一个port（端口）
        datagramChannel.bind(new InetSocketAddress(4321));

        //读
        ByteBuffer readBuffer =ByteBuffer.allocate(100);
        SocketAddress receive = datagramChannel.receive(readBuffer);

        StringBuilder sb=new StringBuilder();
        readBuffer.flip();
        while (readBuffer.hasRemaining()){
            sb.append((char)readBuffer.get());
        }
        System.out.println(sb.toString());
        System.in.read();
    }
}
