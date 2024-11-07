package jp.co.futech.gateway.teet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: ErHu.Zhao
 * @create: 2024-09-10
 **/
@Configuration(proxyBeanMethods = false)
public class MyClassCo {

    @Bean
    public MyClass1 myClass1(MyClass2 myClass2){
        return new MyClass1();
    }
}
