package jp.co.futech.module.system.service.help;

import jp.co.futech.module.system.controller.admin.help.vo.HelpReqVO;
import jp.co.futech.module.system.dal.dataobject.help.HelpDocumentDO;

import java.util.List;

/**
 * @description: 帮助文档中心service
 * @author: ErHu.Zhao
 * @create: 2024-06-07
 **/
public interface HelpDocumentService {

    /**
     * 创建帮助文档
     *
     * @param createReqVO help信息
     * @return 文档编号
     */
    Long createHelpDoc(HelpReqVO createReqVO);


    /**
     * 编辑帮助文档
     *
     * @param editReqVO help信息
     * @return 文档编号
     */
    Long editHelpDoc(HelpReqVO editReqVO);


    /**
     * 删除帮助文档
     *
     * @param deleteReqVO help信息
     * @return 文档编号
     */
    Long deleteHelpDoc(HelpReqVO deleteReqVO);


    /**
     * 获取文档列表
     *
     * @param getReqVO help信息
     * @return help文章列表
     */
    List<HelpDocumentDO> getDocumentList(HelpReqVO getReqVO);


}
