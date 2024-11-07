package jp.co.futech.module.insight.service.demo;

import jp.co.futech.framework.common.util.object.BeanUtils;
import jp.co.futech.module.insight.entity.ms.DemoDO;
import jp.co.futech.module.insight.mapper.ms.DemoMapper;
import jp.co.futech.module.insight.po.req.demo.DemoShowReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: demo service impl
 * @author: ErHu.Zhao
 * @create: 2024-06-07
 **/
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoMapper demoMapper;

    @Override
    public Long testDemo(DemoShowReq testVO) {
        testVO.setId(null);
        DemoDO demoDO = BeanUtils.toBean(testVO, DemoDO.class);
        return demoDO.getId();
    }
}
