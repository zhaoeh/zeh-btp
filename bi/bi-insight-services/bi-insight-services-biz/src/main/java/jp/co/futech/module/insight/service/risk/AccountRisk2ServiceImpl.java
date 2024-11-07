package jp.co.futech.module.insight.service.risk;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jp.co.futech.framework.common.pojo.PageObjectResult;
import jp.co.futech.framework.common.util.json.JsonUtils;
import jp.co.futech.framework.mybatis.core.util.MyBatisUtils;
import jp.co.futech.module.insight.convert.AccountRisk2ModelNewResConvert;
import jp.co.futech.module.insight.mapper.pg.risk.PgAccountRisk2ModelMapper;
import jp.co.futech.module.insight.po.req.risk.AccountRisk2ModelNewReq;
import jp.co.futech.module.insight.po.res.risk.AccountRisk2ModelNewRes;
import jp.co.futech.module.insight.po.res.risk.AccountRisk2ModelNewShowRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @description: 账号风控2service impl
 * @author: ErHu.Zhao
 * @create: 2024-06-24
 **/
@Service
public class AccountRisk2ServiceImpl implements AccountRisk2Service {

    @Autowired
    private PgAccountRisk2ModelMapper pgAccountRisk2ModelMapper;

//    @Override
//    public AccountRisk2ModelRes selectRisk2(AccountRisk2ModelReq params) {
//        List<AccountRisk2ModelRes.UserAction> result = accountRisk2ModelMapper.selectRisk2(params);
//        return refactor(result);
//    }

    @Override
    public AccountRisk2ModelNewShowRes selectRisk2New(AccountRisk2ModelNewReq params) {
        AccountRisk2ModelNewRes result = pgAccountRisk2ModelMapper.selectRisk2Features(params);
        List<AccountRisk2ModelNewRes.UserAction> userActions = pgAccountRisk2ModelMapper.selectRisk2Actions(params);
        return AccountRisk2ModelNewResConvert.INSTANCE.convert(refactor(result, userActions));
    }

    @Override
    public PageObjectResult<AccountRisk2ModelNewShowRes> selectRisk2NewPage(AccountRisk2ModelNewReq params) {
        AccountRisk2ModelNewRes result = pgAccountRisk2ModelMapper.selectRisk2Features(params);
        Page<AccountRisk2ModelNewRes.UserAction> page = MyBatisUtils.buildPage(params);
        pgAccountRisk2ModelMapper.selectRisk2Actions(page, params);
        return new PageObjectResult<>(AccountRisk2ModelNewResConvert.INSTANCE.convert(refactor(result, page.getRecords())), page.getTotal());
    }

//    @Override
//    public PageObjectResult<AccountRisk2ModelRes> selectRisk2Page(AccountRisk2ModelReq params) {
//        Page<AccountRisk2ModelRes.UserAction> page = MyBatisUtils.buildPage(params);
//        accountRisk2ModelMapper.selectRisk2(page, params);
//        return new PageObjectResult<>(refactor(page.getRecords()), page.getTotal());
//    }

//    private AccountRisk2ModelRes refactor(List<AccountRisk2ModelRes.UserAction> list) {
//        if (CollUtil.isEmpty(list)) {
//            return AccountRisk2ModelRes.builder().build();
//        }
//        JsonNode features = list.stream().findFirst().map(e -> JsonUtils.parseTree(e.getUsersFeaturesStr())).orElse(JsonUtils.creatEmptyObjectNode());
//        return AccountRisk2ModelRes.builder().features(features).userActions(list.stream().map(action -> {
//            JsonNode userActions = JsonUtils.parseTree(action.getUsersActionsStr());
//            if (userActions.isObject()) {
//                ObjectNode object = (ObjectNode) userActions;
//                object.put("dt", action.getDt());
//                object.put("login_name", action.getLoginName());
//                object.put("score", action.getScore());
//            }
//            return userActions;
//        }).collect(Collectors.toList())).build();
//    }

    private AccountRisk2ModelNewRes refactor(AccountRisk2ModelNewRes result, List<AccountRisk2ModelNewRes.UserAction> userActions) {
        if (Objects.isNull(result)) {
            result = AccountRisk2ModelNewRes.builder().build();
        }
        AccountRisk2ModelNewRes finalResult = result;
        JsonNode features = Optional.ofNullable(result.getUsersFeaturesStr()).map(JsonUtils::parseTree).map(node -> {
            if (node.isObject()) {
                JsonNode dd = JsonUtils.parseTree(JsonUtils.toJsonStringWithView(finalResult, AccountRisk2ModelNewRes.JsonViews.MyView.class));
                ObjectNode fea = (ObjectNode) node;
                dd.fields().forEachRemaining(entry -> {
                    fea.set(entry.getKey(), entry.getValue());
                });
            }
            return node;
        }).orElse(JsonUtils.creatEmptyObjectNode());
        result.setFeatures(features);
        result.setUserActions(userActions);
        return result;
    }
}
