package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PathTest {


    public static void main(String[] args) {
        try{
            String userDir = System.getProperty("user.dir");
            System.out.println("jvm启动位置"+userDir);

            File file=new File("src/main/resources/hello.txt");
            FileInputStream fileInputStream=new FileInputStream(file);
            FileOutputStream fileOutputStream=new FileOutputStream(file,true);
            fileOutputStream.write("\nHHHHH".getBytes());

            fileOutputStream.close();
            fileInputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
