package jp.co.futech.module.insight.service.risk;

import jp.co.futech.framework.common.pojo.PageResult;
import jp.co.futech.module.insight.entity.bh.risk.AccountRiskListEntity;
import jp.co.futech.module.insight.entity.pg.risk.PgAccountRiskListEntity;
import jp.co.futech.module.insight.po.req.risk.AccountRiskBarChartQueryReq;
import jp.co.futech.module.insight.po.req.risk.AccountRiskListQueryReq;
import jp.co.futech.module.insight.po.req.risk.AccountRiskPieChartQueryReq;
import jp.co.futech.module.insight.po.res.risk.AccountRiskBarChartQueryRes;
import jp.co.futech.module.insight.po.res.risk.AccountRiskPieChart;

import java.util.List;

/**
 * @description: 账号风控service
 * @author: ErHu.Zhao
 * @create: 2024-06-24
 **/
public interface AccountRiskService {

    /**
     * 分页查询账号风控列表
     *
     * @param req 请求
     * @return 分页结果
     */
    PageResult<PgAccountRiskListEntity> selectRiskListByPage(AccountRiskListQueryReq req);

    /**
     * 查询柱状图数据
     *
     * @param req 请求
     * @return 柱状图数据
     */
    List<AccountRiskBarChartQueryRes> selectBarChartData(AccountRiskBarChartQueryReq req);

    /**
     * 查询饼图数据
     *
     * @param req 请求
     * @return 柱状图数据
     */
    List<AccountRiskPieChart> selectPieChartData(AccountRiskPieChartQueryReq req);
}
