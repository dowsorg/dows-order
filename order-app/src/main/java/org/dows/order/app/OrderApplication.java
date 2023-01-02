package org.dows.order.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* @description
*
* @author 
* @date 2023年1月2日 下午2:04:57
*/
@SpringBootApplication(scanBasePackages = {"org.dows.framework","org.dows.order"})
public class OrderApplication{
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}

