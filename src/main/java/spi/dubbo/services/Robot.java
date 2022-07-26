package spi.dubbo.services;

import org.apache.dubbo.common.extension.SPI;

@SPI
public interface Robot {
    public void sayHello();
}
