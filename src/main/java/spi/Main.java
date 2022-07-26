package spi;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.compiler.Compiler;
import spi.dubbo.services.Robot;
import spi.services.User;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ServiceLoader;

public class Main {

    public static void main(String[] args) throws IOException {
//        ServiceLoader<User> serviceLoader=ServiceLoader.load(User.class);
//        for (User user : serviceLoader) {
//            System.out.println(user.getName());
//        }

        ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
        Robot optimusPrime = extensionLoader.getExtension("optimusPrime");
        optimusPrime.sayHello();
        Robot bumblebee = extensionLoader.getExtension("bumblebee");
        bumblebee.sayHello();

//        Compiler compiler = ExtensionLoader.getExtensionLoader(Compiler.class).getAdaptiveExtension();
//        System.out.println(compiler.getClass());
    }

}
