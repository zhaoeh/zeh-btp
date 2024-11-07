package jp.co.futech.module.system.service.help;

import ft.btp.i18n.core.I18nLocaleWrapper;
import jp.co.futech.framework.common.exception.util.ServiceExceptionUtil;
import jp.co.futech.framework.common.util.object.BeanUtils;
import jp.co.futech.module.system.controller.admin.help.vo.HelpReqVO;
import jp.co.futech.module.system.dal.dataobject.help.HelpDocumentDO;
import jp.co.futech.module.system.dal.mysql.help.HelpDocumentMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static jp.co.futech.module.infra.enums.ErrorCodeConstants.HELP_ACCEPT_LANGUAGE_ERROR;
import static jp.co.futech.module.infra.enums.ErrorCodeConstants.HELP_DOCUMENT_EXISTS;

/**
 * @description: 帮助文档中心service impl
 * @author: ErHu.Zhao
 * @create: 2024-06-07
 **/
@Service
public class HelpDocumentServiceImpl implements HelpDocumentService {

    @Autowired
    private HelpDocumentMapper documentMapper;

    @Autowired
    private I18nLocaleWrapper i18nLocaleWrapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long createHelpDoc(HelpReqVO createReqVO) {
        String locale = i18nLocaleWrapper.toString();
        ServiceExceptionUtil.isTrue(StringUtils.isNotBlank(locale), HELP_ACCEPT_LANGUAGE_ERROR);
        HelpDocumentDO helpDocumentDO = BeanUtils.toBean(createReqVO.setId(null).setLocale(locale), HelpDocumentDO.class);
        ServiceExceptionUtil.isTrue(!documentMapper.exists(createReqVO), HELP_DOCUMENT_EXISTS);
        documentMapper.insert(helpDocumentDO);
        return helpDocumentDO.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long editHelpDoc(HelpReqVO editReqVO) {
        HelpDocumentDO helpDocumentDO = BeanUtils.toBean(editReqVO.setLocale(i18nLocaleWrapper.toString()), HelpDocumentDO.class);
        documentMapper.insertOrUpdate(helpDocumentDO);
        return helpDocumentDO.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long deleteHelpDoc(HelpReqVO editReqVO) {
        HelpDocumentDO helpDocumentDO = BeanUtils.toBean(editReqVO, HelpDocumentDO.class);
        documentMapper.deleteById(helpDocumentDO);
        return helpDocumentDO.getId();
    }

    @Override
    public List<HelpDocumentDO> getDocumentList(HelpReqVO getReqVO) {
        return documentMapper.selectList(getReqVO.setLocale(i18nLocaleWrapper.toString()));
    }
}
