package jp.co.futech.module.infra.dal.mysql.file;

import jp.co.futech.module.infra.dal.dataobject.file.FileContentDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileContentMapper extends BaseMapper<FileContentDO> {
}
