package jp.co.futech.module.infra.dal.mysql.file;

import cn.hutool.core.collection.CollUtil;
import jp.co.futech.framework.file.core.client.db.DBFileContentFrameworkDAO;
import jp.co.futech.module.infra.dal.dataobject.file.FileContentDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class FileContentDAOImpl implements DBFileContentFrameworkDAO {

    @Resource
    private FileContentMapper fileContentMapper;

    @Override
    public void insert(Long configId, String path, byte[] content) {
        FileContentDO entity = new FileContentDO().setConfigId(configId)
                .setPath(path).setContent(content);
        fileContentMapper.insert(entity);
    }

    @Override
    public void delete(Long configId, String path) {
        fileContentMapper.delete(buildQuery(configId, path));
    }

    @Override
    public byte[] selectContent(Long configId, String path) {
        List<FileContentDO> list = fileContentMapper.selectList(
                buildQuery(configId, path).select(FileContentDO::getContent).orderByDesc(FileContentDO::getId));
        return Optional.ofNullable(CollUtil.getFirst(list))
                .map(FileContentDO::getContent)
                .orElse(null);
    }

    @Override
    public boolean isExist(Long configId, String path) {
        Long count = fileContentMapper.selectCount(buildQuery(configId, path));
        return Objects.nonNull(count) && count > 0;
    }

    private LambdaQueryWrapper<FileContentDO> buildQuery(Long configId, String path) {
        return new LambdaQueryWrapper<FileContentDO>()
                .eq(FileContentDO::getConfigId, configId)
                .eq(FileContentDO::getPath, path);
    }

}
