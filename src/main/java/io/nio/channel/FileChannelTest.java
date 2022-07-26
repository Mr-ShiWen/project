package io.nio.channel;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {
    @Test
    public void test_FileChannel() throws IOException {
/*        String dir = System.getProperty("user.dir");
        System.out.println("jvm启动路径："+dir);*/


        //1.创建一个RandomAccessFile（随机访问文件）对象，
        RandomAccessFile raf=new RandomAccessFile("src/main/resources/fileChannel.txt", "rw");
        //通过RandomAccessFile对象的getChannel()方法。FileChannel是抽象类。
        FileChannel inChannel=raf.getChannel();


        //2.创建一个写缓冲，从通道读
        ByteBuffer buf=ByteBuffer.allocate(2);
        int bytesRead = inChannel.read(buf);
        long position = inChannel.position();
        System.out.println("读后position:"+position);

        //3.创建一个读缓冲，写入通道
        ByteBuffer buf2=ByteBuffer.allocate(48);
        buf2.put("ggg".getBytes());
        buf2.flip();

        while (buf2.hasRemaining()){
            inChannel.write(buf2);
        }
        long position1 = inChannel.position();
        System.out.println("写后position:"+position1);

        while (bytesRead != -1) {

            System.out.println("Read " + bytesRead);
            //Buffer有两种模式，写模式和读模式。在写模式下调用flip()之后，Buffer从写模式变成读模式。
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }
            System.out.println();
            buf.flip();
            //清空缓存区
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        //关闭RandomAccessFile（随机访问文件）对象
        raf.close();
    }
}
