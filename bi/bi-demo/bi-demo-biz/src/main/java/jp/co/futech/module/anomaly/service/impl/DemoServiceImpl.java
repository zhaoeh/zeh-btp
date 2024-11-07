package jp.co.futech.module.anomaly.service.impl;

import jp.co.futech.framework.common.util.object.BeanUtils;
import jp.co.futech.module.anomaly.dto.DemoDO;
import jp.co.futech.module.anomaly.service.DemoService;
import jp.co.futech.module.anomaly.vo.DemoReqVO;
import org.springframework.stereotype.Service;

/**
 * @description: demo service impl
 * @author: ErHu.Zhao
 * @create: 2024-06-07
 **/
@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public Long testDemo(DemoReqVO testVO) {
        testVO.setId(null);
        DemoDO demoDO = BeanUtils.toBean(testVO, DemoDO.class);
        // 只做demo演示
        return demoDO.getId();
    }
}
