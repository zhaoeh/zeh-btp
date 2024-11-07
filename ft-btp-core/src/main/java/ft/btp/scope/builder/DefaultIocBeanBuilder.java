package ft.btp.scope.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.util.Assert;

/**
 * @description: 默认的bean构建器
 * @author: ErHu.Zhao
 * @create: 2024-08-23
 **/
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DefaultIocBeanBuilder implements IocBeanBuilder {

    private ObjectFactory<?> factory;

    @Override
    public Object buildBean() {
        Assert.notNull(factory, "ObjectFactory cannot be null");
        return factory.getObject();
    }
}
