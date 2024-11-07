package jp.co.futech.module.system.api.dict;

import jp.co.futech.framework.common.pojo.CommonResult;
import jp.co.futech.framework.common.util.object.BeanUtils;
import jp.co.futech.module.system.api.dict.dto.DictDataRespDTO;
import jp.co.futech.module.system.dal.dataobject.dict.DictDataDO;
import jp.co.futech.module.system.service.dict.DictDataService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.Collection;

import static jp.co.futech.framework.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class DictDataApiImpl implements DictDataApi {

    @Resource
    private DictDataService dictDataService;

    @Override
    public CommonResult<Boolean> validateDictDataList(String dictType, Collection<String> values) {
        dictDataService.validateDictDataList(dictType, values);
        return success(true);
    }

    @Override
    public CommonResult<DictDataRespDTO> getDictData(String dictType, String value) {
        DictDataDO dictData = dictDataService.getDictData(dictType, value);
        return success(BeanUtils.toBean(dictData, DictDataRespDTO.class));
    }

    @Override
    public CommonResult<DictDataRespDTO> parseDictData(String dictType, String label) {
        DictDataDO dictData = dictDataService.parseDictData(dictType, label);
        return success(BeanUtils.toBean(dictData, DictDataRespDTO.class));
    }

}
