package jp.co.futech.framework.common.pojo;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 排序字段 DTO
 *
 * 类名加了 ing 的原因是，避免和 ES SortField 重名。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortingField<T> implements Serializable {

    /**
     * 顺序 - 升序
     */
    public static final String ORDER_ASC = "asc";
    /**
     * 顺序 - 降序
     */
    public static final String ORDER_DESC = "desc";

    /**
     * 排序对象
     */
    private SFunction<T, ?> sFunction;

    /**
     * 字段
     */
    private String field;
    /**
     * 顺序
     */
    private String order;

}
