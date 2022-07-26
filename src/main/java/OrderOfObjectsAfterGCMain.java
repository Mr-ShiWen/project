import sun.misc.Unsafe;
import sun.misc.VM;

import java.lang.reflect.Field;

public class OrderOfObjectsAfterGCMain {

    public static void main(String[] args) {
        new Object();
        new Object();
        Object obj = new Object();
        new Object();
        new Object();
        System.out.println(obj.hashCode());
        synchronized (obj){
            System.out.println("hahah");
            System.gc();
        }
        System.out.println(obj.hashCode());
    }


}
