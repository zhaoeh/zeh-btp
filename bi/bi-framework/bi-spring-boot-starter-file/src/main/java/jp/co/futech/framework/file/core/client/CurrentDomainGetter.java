package jp.co.futech.framework.file.core.client;

/**
 * @description: 自动获取当前服务器domain
 * @author: ErHu.Zhao
 * @create: 2024-06-28
 **/
public interface CurrentDomainGetter {

    /**
     * 获取当前域名
     *
     * @return 当前域名
     */
    String getCurrentDomain();
}
