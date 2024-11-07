package jp.co.futech.module.system.service.i18n;

import cn.hutool.core.util.BooleanUtil;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ft.btp.i18n.info.I18nMessage;
import ft.btp.i18n.info.I18nMessages;
import jp.co.futech.framework.common.util.json.JsonUtils;
import jp.co.futech.framework.common.util.object.BeanUtils;
import jp.co.futech.module.system.dal.dataobject.messages.MessagesDO;
import jp.co.futech.module.system.dal.dataobject.messages.MessagesHistoryDO;
import jp.co.futech.module.system.dal.mysql.messages.BiMessagesHistoryI18nMapper;
import jp.co.futech.module.system.dal.mysql.messages.BiMessagesI18nMapper;
import jp.co.futech.module.system.dal.mysql.permission.MenuMapper;
import lombok.Data;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PropertyPlaceholderHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description: 国际化服务实现
 * @author: ErHu.Zhao
 * @create: 2024-07-18
 **/
@Service
@Data
public class I18nServiceImpl implements I18nService {

    PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper("{", "}");

    private I18nMessages snapshot;

    @Autowired
    private I18nPrepare prepare;

    @Autowired
    private BiMessagesI18nMapper biMessagesI18nMapper;

    @Autowired
    private BiMessagesHistoryI18nMapper biMessagesHistoryI18nMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public I18nMessages encapI18nMessages(boolean isSnapshot) {
        if (BooleanUtil.isTrue(isSnapshot) && Objects.nonNull(snapshot)) {
            return snapshot;
        }
        I18nMessages i18nMessages = new I18nMessages();
        List<MessagesDO> messages = prepare.prepareMessages();
        Map<String, List<MessagesDO>> mes = messages.stream().collect(Collectors.groupingBy(MessagesDO::getLocale));
        if (MapUtils.isNotEmpty(mes)) {
            List<I18nMessage> messageList = new ArrayList<>();
            mes.forEach((k, v) -> {
                messageList.add(I18nMessage.builder().locale(k).messages(v.stream().collect(Collectors.toMap(MessagesDO::getCode, MessagesDO::getMessage))).build());
                i18nMessages.addMessages(messageList);
            });
        }
        cacheSnapshot(i18nMessages);
        return i18nMessages;
    }

    @Override
    public ObjectNode obtainMessages() {
        List<MessagesDO> list = biMessagesI18nMapper.selectAllMessages();
        Map<String, List<MessagesDO>> sources = list.stream().collect(Collectors.groupingBy(MessagesDO::getCode));
        ObjectNode root = (ObjectNode) JsonUtils.creatEmptyObjectNode();
        ArrayNode arrayNode = (ArrayNode) JsonUtils.creatEmptyArrayNode();
        sources.forEach((k, v) -> {
            ObjectNode element = (ObjectNode) JsonUtils.creatEmptyObjectNode();
            element.put("code", k);
            v.stream().forEach(local -> element.put(local.getLocale(), local.getMessage()));
            arrayNode.addPOJO(element);
        });
        root.set("translations", arrayNode);
        return root;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateMessage2Empty(String code, boolean condition) {
        if (condition && StringUtils.isNotBlank(code)) {
            String needCode = placeholderHelper.replacePlaceholders(code, e -> e);
            List<MessagesDO> targets = biMessagesI18nMapper.selectList(MessagesDO::getCode, needCode);
            if (CollectionUtils.isEmpty(targets)) {
                return 0;
            }
            // 插入历史表
            List<MessagesHistoryDO> histories = BeanUtils.toBean(targets, MessagesHistoryDO.class, (t) -> t.setId(null));
            biMessagesHistoryI18nMapper.insertBatch(histories);
            return biMessagesI18nMapper.updateMessages2Empty(needCode);
        }
        return 0;
    }

    /**
     * 缓存快照
     *
     * @param snapshot
     */
    private void cacheSnapshot(I18nMessages snapshot) {
        setSnapshot(snapshot);
    }

}







