package jp.co.futech.framework.file.core.client.db;

/**
 * 文件内容 Framework DAO 接口
 *
 * @author futech.co.jp
 */
public interface DBFileContentFrameworkDAO {

    /**
     * 插入文件内容
     *
     * @param configId 配置编号
     * @param path 路径
     * @param content 内容
     */
    void insert(Long configId, String path, byte[] content);

    /**
     * 删除文件内容
     *
     * @param configId 配置编号
     * @param path 路径
     */
    void delete(Long configId, String path);

    /**
     * 获得文件内容
     *
     * @param configId 配置编号
     * @param path 路径
     * @return 内容
     */
    byte[] selectContent(Long configId, String path);

    /**
     * 根据configId和path判断文件是否已经存在
     *
     * @param configId 配置编号
     * @param path     路径
     * @return 文件是否已经存在 true：已经存在 false:不存在
     */
    boolean isExist(Long configId, String path);

}
