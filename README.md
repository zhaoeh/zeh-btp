# ZEH Backend Turbo Pack(zeh-btp)

## 简介
zeh-btp 组件包是一个通用的后端功能增强包，与业务解耦，采用springboot starter实现。
核心目的是为业务侧提供一些通用的能力。

## 主要特性
其内部提供如下核心能力：  
1.基于springboot提供后端通用的国际化方案   
2.基于springboot提供IOC bean生命周期管理策略   
3.方便业务侧集成google mfa二次认证   
4.扩展spring validation提供实体多字段关联校验能力   
5.基于redis提供分布式锁能力   
6.基于redis提供api限流能力   
7.提供html特殊字符转义能力，防止xss攻击   
8.提供本地配置覆盖远程nacos配置中心的能力     

## 兼容性
本组件基于springboot 3.x 版本开发，仅仅支持springboot 3.x 版本，不支持之前的版本。      
因为springboot 3.x版本使用了最新的JAVA EE，而java EE项目进行了命名空间的重大变更，比如原来的javax命名空间被废弃，变更为jakarta命名空间，因此影响了所有Servlet相关API。       
如果期望支持springboot 2.x版本，请自行修改源码（理论上只需要修改一部分命名空间和springboot自动配置的方式，仅此而已）。      

## 使用方式
1.业务侧通过pom文件引入组件依赖即可集成。   
如下：   
```xml
<dependency>
    <groupId>com.ft.btp</groupId>
    <artifactId>zeh-btp-common</artifactId>
    <version>3.0.3</version>
</dependency>
```
2.业务侧通过实现组件对应扩展接口，提供组件功能管理即可使用      
比如实现 zeh.btp.i18n.core.I18nMessagesProvider 接口，即可使用国际化能力。   
实现 zeh.btp.mfa.MfaProcess 接口，即可使用mfa二次认证能力。   
实现 zeh.btp.scope.hook.BeanScopeManager 接口，即可使用 bean 生命周期管理能力。   
