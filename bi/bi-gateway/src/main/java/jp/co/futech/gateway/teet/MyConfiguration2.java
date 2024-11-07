package jp.co.futech.gateway.teet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @description:
 * @author: ErHu.Zhao
 * @create: 2024-09-10
 **/
@Configuration(proxyBeanMethods = false)
@Import(MyClassCo.class)
public class MyConfiguration2 {

    @Bean
    public MyClass2 myClass2(){
        return new MyClass2();
    }
}
