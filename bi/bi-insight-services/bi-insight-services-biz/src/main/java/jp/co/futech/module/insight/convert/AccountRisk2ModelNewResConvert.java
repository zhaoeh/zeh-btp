package jp.co.futech.module.insight.convert;

import jp.co.futech.module.insight.po.res.risk.AccountRisk2ModelNewRes;
import jp.co.futech.module.insight.po.res.risk.AccountRisk2ModelNewShowRes;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountRisk2ModelNewResConvert {

    AccountRisk2ModelNewResConvert INSTANCE = Mappers.getMapper(AccountRisk2ModelNewResConvert.class);

    AccountRisk2ModelNewShowRes convert(AccountRisk2ModelNewRes res);

}
