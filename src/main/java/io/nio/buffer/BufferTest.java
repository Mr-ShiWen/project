package io.nio.buffer;

import org.junit.Test;

import java.nio.IntBuffer;
import java.security.SecureRandom;

public class BufferTest {


    @Test
    /**
     * 测试pos、limit、capacity这三个buffer里的核心参数
     */
    public void test_threeCoreParams(){
        IntBuffer intBuffer = IntBuffer.allocate(10);
        System.out.println("初始的Buffer：" + intBuffer);
        for (int i = 0; i < 5; ++i){
            int randomNum = new SecureRandom().nextInt(20);
            intBuffer.put(randomNum);
        }

        System.out.println("flip之前（写）：limit = "+ intBuffer);
        intBuffer.flip();
        System.out.println("flip之后（读）：limit = "+ intBuffer);

        System.out.println("进入读取");
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer);
            System.out.println(intBuffer.get());
        }
    }

    @Test
    /**
     * 测试clear()方法
     */
    public void test_clearMethod(){
        //正常写满
        IntBuffer intBuffer = IntBuffer.allocate(10);
        for (int i = 0; i < intBuffer.capacity(); ++i){
            int randomNum = new SecureRandom().nextInt(20);
            intBuffer.put(randomNum);
        }
        intBuffer.flip();
        System.out.print("正常写满：");
        while (intBuffer.hasRemaining()){
            System.out.print(intBuffer.get() + ",");
        }
        System.out.println();
        intBuffer.flip();

        //clear后再写
        intBuffer.clear();
        for (int i = 0; i < intBuffer.capacity()/2; i++) {
            intBuffer.put(i);
        }
        System.out.println("clear后写完成");

        System.out.println("flip前："+intBuffer);
        intBuffer.flip();
        System.out.println("flip后："+intBuffer);

        System.out.print("clear后再写：");
        while (intBuffer.hasRemaining()){
            System.out.print(intBuffer.get() + ",");
        }
        intBuffer.flip();

    }

}
