package reflection;

import org.junit.Test;
import reflection.impl.Cat;
import reflection.impl.Dog;
import reflection.interf.Pet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InterfaceMethodTest {

    @Test
    public void test_InterfaceMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method sayHello_Method = Pet.class.getDeclaredMethod("sayHello", String.class, int.class);
        Cat cat = new Cat();
        Dog dog = new Dog();

        sayHello_Method.invoke(cat,"小猫咪",13);
        sayHello_Method.invoke(dog,"旺财",15);

        Method sleep_Method = Cat.class.getDeclaredMethod("sleep", int.class);
        sleep_Method.invoke(null,8);
    }

}
