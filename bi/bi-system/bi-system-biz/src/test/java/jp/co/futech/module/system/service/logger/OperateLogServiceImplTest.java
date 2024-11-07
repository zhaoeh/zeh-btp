package jp.co.futech.module.system.service.logger;

import cn.hutool.core.map.MapUtil;
import jp.co.futech.framework.common.enums.CommonStatusEnum;
import jp.co.futech.framework.common.enums.UserTypeEnum;
import jp.co.futech.framework.common.exception.enums.GlobalErrorCodeConstants;
import jp.co.futech.framework.common.pojo.PageResult;
import jp.co.futech.framework.operatelog.core.enums.OperateTypeEnum;
import jp.co.futech.framework.test.core.ut.BaseDbUnitTest;
import jp.co.futech.framework.test.core.util.RandomUtils;
import jp.co.futech.module.system.api.logger.dto.OperateLogCreateReqDTO;
import jp.co.futech.module.system.controller.admin.logger.vo.operatelog.OperateLogPageReqVO;
import jp.co.futech.module.system.dal.dataobject.logger.OperateLogDO;
import jp.co.futech.module.system.dal.dataobject.user.AdminUserDO;
import jp.co.futech.module.system.dal.mysql.logger.OperateLogMapper;
import jp.co.futech.module.system.service.user.AdminUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import jakarta.annotation.Resource;
import java.util.Collections;

import static cn.hutool.core.util.RandomUtil.randomEle;
import static jp.co.futech.framework.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;
import static jp.co.futech.framework.common.util.date.LocalDateTimeUtils.buildBetweenTime;
import static jp.co.futech.framework.common.util.date.LocalDateTimeUtils.buildTime;
import static jp.co.futech.framework.common.util.object.ObjectUtils.cloneIgnoreId;
import static jp.co.futech.framework.test.core.util.AssertUtils.assertPojoEquals;
import static jp.co.futech.framework.test.core.util.RandomUtils.randomLongId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Import({OperateLogServiceImpl.class})
public class OperateLogServiceImplTest extends BaseDbUnitTest {

    @Resource
    private OperateLogService operateLogServiceImpl;

    @Resource
    private OperateLogMapper operateLogMapper;

    @MockBean
    private AdminUserService userService;

    @Test
    public void testCreateOperateLog() {
        OperateLogCreateReqDTO reqVO = RandomUtils.randomPojo(OperateLogCreateReqDTO.class,
                o -> o.setExts(MapUtil.<String, Object>builder("orderId", randomLongId()).build()));

        // 调研
        operateLogServiceImpl.createOperateLog(reqVO);
        // 断言
        OperateLogDO operateLogDO = operateLogMapper.selectOne(null);
        assertPojoEquals(reqVO, operateLogDO);
    }

    @Test
    public void testGetOperateLogPage() {
        // mock（用户信息）
        AdminUserDO user = RandomUtils.randomPojo(AdminUserDO.class, o -> {
            o.setNickname("wang");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        });
        when(userService.getUserListByNickname("wang")).thenReturn(Collections.singletonList(user));
        Long userId = user.getId();

        // 构造操作日志
        OperateLogDO operateLogDO = RandomUtils.randomPojo(OperateLogDO.class, o -> {
            o.setUserId(userId);
            o.setUserType(randomEle(UserTypeEnum.values()).getValue());
            o.setModule("order");
            o.setType(OperateTypeEnum.CREATE.getType());
            o.setStartTime(buildTime(2021, 3, 6));
            o.setResultCode(GlobalErrorCodeConstants.SUCCESS.getCode());
            o.setExts(MapUtil.<String, Object>builder("orderId", randomLongId()).build());
        });
        operateLogMapper.insert(operateLogDO);
        // 测试 userId 不匹配
        operateLogMapper.insert(cloneIgnoreId(operateLogDO, o -> o.setUserId(userId + 1)));
        // 测试 module 不匹配
        operateLogMapper.insert(cloneIgnoreId(operateLogDO, o -> o.setModule("user")));
        // 测试 type 不匹配
        operateLogMapper.insert(cloneIgnoreId(operateLogDO, o -> o.setType(OperateTypeEnum.IMPORT.getType())));
        // 测试 createTime 不匹配
        operateLogMapper.insert(cloneIgnoreId(operateLogDO, o -> o.setStartTime(buildTime(2021, 2, 6))));
        // 测试 resultCode 不匹配
        operateLogMapper.insert(cloneIgnoreId(operateLogDO, o -> o.setResultCode(BAD_REQUEST.getCode())));

        // 构造调用参数
        OperateLogPageReqVO reqVO = new OperateLogPageReqVO();
        reqVO.setUserNickname("wang");
        reqVO.setModule("order");
        reqVO.setType(OperateTypeEnum.CREATE.getType());
        reqVO.setStartTime(buildBetweenTime(2021, 3, 5, 2021, 3, 7));
        reqVO.setSuccess(true);

        // 调用
        PageResult<OperateLogDO> pageResult = operateLogServiceImpl.getOperateLogPage(reqVO);
        // 断言，只查到了一条符合条件的
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(operateLogDO, pageResult.getList().get(0));
    }

}
