package com.dynamic.model.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.dynamic.model.core.DynamicRouteBeanPostProcessor;
import com.dynamic.model.core.DynamicTableNameHandler;
import com.dynamic.model.core.MayBeBlendModelAround;
import com.dynamic.model.core.ModelContextFilter;
import com.dynamic.model.util.MyBatisUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AutoConfiguration
// 允许使用 model.config.enable 配置项修改为false来关闭全局自动配置
@ConditionalOnProperty(prefix = "model.config", value = "enable", matchIfMissing = true)
@EnableConfigurationProperties(ModelProperties.class)
public class ModelAutoConfiguration {

    /**
     * 注册mp拦截器；实现sql拦截，根据上下文标记动态修改表名称
     */
    @ConditionalOnClass(name = "com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor")
    @Configuration
    static class DynamicTableNameAutoConfiguration {
        @Bean
        public DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor(ModelProperties properties,
                                                                                 MybatisPlusInterceptor interceptor) {
            DynamicTableNameInnerInterceptor inner = new DynamicTableNameInnerInterceptor(new DynamicTableNameHandler(properties));
            // 添加到 interceptor 中
            // 需要加在首个，主要是为了在分页插件前面。这个是 MyBatis Plus 的规定
            MyBatisUtils.addInterceptor(interceptor, inner, 0);

            return inner;
        }
    }

    /**
     * 注册动态路由bean post processor，用于在早期代理符合条件的bean
     * @return bean post processor实例
     */
    @Bean
    public DynamicRouteBeanPostProcessor dynamicRouteBeanPostProcessor(){
        return new DynamicRouteBeanPostProcessor();
    }


    /**
     * 注册过滤器，从header获取模式标记并设置到上下文中
     * @param properties 属性对象
     * @return filter实例
     */
    @Bean
    public ModelContextFilter modelContextFilter(ModelProperties properties) {
        return new ModelContextFilter(properties);
    }

    /**
     * 注册并行模式请求aop
     * @return aop实例
     */
    @Bean
    public MayBeBlendModelAround mayBeBlendModelAround() {
        return new MayBeBlendModelAround();
    }

}
