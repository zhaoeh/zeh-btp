package jp.co.futech.module.system.service.i18n;

import cn.hutool.core.collection.CollUtil;
import jp.co.futech.framework.common.util.json.JsonUtils;
import jp.co.futech.module.system.config.I18nMessagesResourceConfig;
import jp.co.futech.module.system.dal.dataobject.messages.MessagesDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: i18n准备类
 * @author: ErHu.Zhao
 * @create: 2024-07-22
 **/
@Component
public class I18nPrepare {

    @Autowired
    private I18nMessagesResourceConfig i18nMessagesResourceConfig;

    public List<MessagesDO> prepareMessages() {
        return refreshMessages();
    }

    /**
     * 刷新国际化资源并返回
     *
     * @return 组装好的国际化资源
     */
    private List<MessagesDO> refreshMessages() {
        String codeDesc = "code";
        String zhCnDesc = "zh-CN";
        String enUsDesc = "en-US";
        String jaJpDesc = "ja-JP";
        String content = obtainMessagesStr();
        if (!StringUtils.hasText(content)) {
            return null;
        }
        List<Map> list = JsonUtils.parseArray(content, "translations", Map.class);
        if (CollUtil.isEmpty(list)) {
            return null;
        }
        List<MessagesDO> dos = list.stream().map(m -> {
            List<MessagesDO> innerList = new ArrayList<>();
            Object code = m.get(codeDesc);
            Object zhCn = m.get(zhCnDesc);
            Object enUs = m.get(enUsDesc);
            Object jaJp = m.get(jaJpDesc);
            if (Objects.nonNull(code) && Objects.nonNull(zhCn)) {
                innerList.add(MessagesDO.builder().code(Objects.toString(code)).message(Objects.toString(zhCn)).locale(zhCnDesc).build());
            }
            if (Objects.nonNull(code) && Objects.nonNull(enUs)) {
                innerList.add(MessagesDO.builder().code(Objects.toString(code)).message(enUs + " ").locale(enUsDesc).build());
            }
            if (Objects.nonNull(code) && Objects.nonNull(jaJp)) {
                innerList.add(MessagesDO.builder().code(Objects.toString(code)).message(Objects.toString(jaJp)).locale(jaJpDesc).build());
            }
            return innerList;
        }).flatMap(Collection::stream).collect(Collectors.toList());
        return dos;
    }

    /**
     * 获取配置源字符串
     *
     * @return
     */
    private String obtainMessagesStr() {
        String content = i18nMessagesResourceConfig.getMessages();
        if (StringUtils.hasText(content)) {
            return content;
        }
        return null;
    }
}
