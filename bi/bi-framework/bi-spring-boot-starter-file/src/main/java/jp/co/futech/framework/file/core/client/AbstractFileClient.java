package jp.co.futech.framework.file.core.client;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 文件客户端的抽象类，提供模板方法，减少子类的冗余代码
 *
 * @author futech.co.jp
 */
@Slf4j
public abstract class AbstractFileClient<Config extends FileClientConfig> implements FileClient {

    /**
     * 当前域名
     */
    private CurrentDomainGetter currentDomainGetter;

    /**
     * 配置编号
     */
    private final Long id;
    /**
     * 文件配置
     */
    protected Config config;

    public AbstractFileClient(Long id, Config config) {
        this.id = id;
        this.config = config;
    }

    /**
     * 初始化
     */
    public final void init() {
        doInit();
        log.debug("[init][配置({}) 初始化完成]", config);
    }

    /**
     * 自定义初始化
     */
    protected abstract void doInit();

    /**
     * 域名获取
     *
     * @return 域名
     */
    protected abstract String getDomain();

    public final void refresh(Config config) {
        // 判断是否更新
        if (config.equals(this.config)) {
            return;
        }
        log.info("[refresh][配置({})发生变化，重新初始化]", config);
        this.config = config;
        // 初始化
        this.init();
    }

    @Override
    public Long getId() {
        return id;
    }

    /**
     * 格式化文件的 URL 访问地址
     * 使用场景：local、ftp、db，通过 FileController 的 getFile 来获取文件内容
     *
     * @param domain 自定义域名
     * @param path 文件路径
     * @return URL 访问地址
     */
    protected String formatFileUrl(String domain, String path) {
        return StrUtil.format("{}/admin-api/infra/file/{}/get/{}", domain, getId(), path);
    }

    protected void initCurrentDomainGetter() {
        if (Objects.isNull(currentDomainGetter)) {
            try {
                currentDomainGetter = SpringUtil.getBean(CurrentDomainGetter.class);
            } catch (Exception e) {
                currentDomainGetter = new DefaultCurrentDomainGetter();
            }

        }
    }

    /**
     * 获取当前域名
     *
     * @return 当前域名
     */
    protected String getCurrentDomain() {
        initCurrentDomainGetter();
        String currentDomain = currentDomainGetter.getCurrentDomain();
        return Objects.nonNull(currentDomain) ? currentDomain : getDomain();
    }
}
