package jp.co.futech.module.system.convert.messages;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @description: 消息转换器
 * @author: ErHu.Zhao
 * @create: 2024-07-05
 **/
@Mapper
public interface MessagesConvert {

    MessagesConvert INSTANCE = Mappers.getMapper(MessagesConvert.class);

}
