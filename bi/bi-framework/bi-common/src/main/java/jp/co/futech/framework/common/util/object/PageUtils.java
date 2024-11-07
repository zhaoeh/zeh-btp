package jp.co.futech.framework.common.util.object;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.func.LambdaUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import jp.co.futech.framework.common.pojo.PageParam;
import jp.co.futech.framework.common.pojo.SortablePageParam;
import jp.co.futech.framework.common.pojo.SortingField;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import java.util.Objects;

import static java.util.Collections.singletonList;

/**
 * {@link jp.co.futech.framework.common.pojo.PageParam} 工具类
 *
 * @author futech.co.jp
 */
public class PageUtils {

    private static final Object[] ORDER_TYPES = new String[]{SortingField.ORDER_ASC, SortingField.ORDER_DESC};

    public static int getStart(PageParam pageParam) {
        return (pageParam.getPageNo() - 1) * pageParam.getPageSize();
    }

    /**
     * 构建排序字段（默认倒序）
     *
     * @param func 排序字段的 Lambda 表达式
     * @param <T>  排序字段所属的类型
     * @return 排序字段
     */
    public static <T> SortingField buildSortingField(Func1<T, ?> func, SFunction<T, ?> sFunction) {
        return buildSortingField(func, sFunction, SortingField.ORDER_DESC);
    }

    /**
     * 构建排序字段（默认倒序）
     *
     * @param func 排序字段的 Lambda 表达式
     * @param <T>  排序字段所属的类型
     * @return 排序字段
     */
    public static <T> SortingField buildSortingFieldOfTableField(Func1<T, ?> func, SFunction<T, ?> sFunction) {
        return buildSortingFieldOfTableField(func, sFunction, SortingField.ORDER_DESC);
    }

    /**
     * 构建排序字段
     *
     * @param func  排序字段的 Lambda 表达式
     * @param order 排序类型 {@link SortingField#ORDER_ASC} {@link SortingField#ORDER_DESC}
     * @param <T>   排序字段所属的类型
     * @return 排序字段
     */
    public static <T> SortingField buildSortingField(Func1<T, ?> func, String order) {
        Assert.isTrue(ArrayUtil.contains(ORDER_TYPES, order), String.format("字段的排序类型只能是 %s/%s", ORDER_TYPES));
        String fieldName = LambdaUtil.getFieldName(func);
        return new SortingField(null, fieldName, order);
    }

    /**
     * 构建排序字段
     *
     * @param func  排序字段的 Lambda 表达式
     * @param order 排序类型 {@link SortingField#ORDER_ASC} {@link SortingField#ORDER_DESC}
     * @param <T>   排序字段所属的类型
     * @return 排序字段
     */
    public static <T> SortingField buildSortingField(Func1<T, ?> func, SFunction<T, ?> sFunction, String order) {
        Assert.isTrue(ArrayUtil.contains(ORDER_TYPES, order), String.format("字段的排序类型只能是 %s/%s", ORDER_TYPES));
        String fieldName = LambdaUtil.getFieldName(func);
        return new SortingField(sFunction, fieldName, order);
    }

    /**
     * 构建排序字段(针对排序字段名称和TableField注解中指定的名称不一致的情况，以TableField注解中的value为准)
     *
     * @param func  排序字段的 Lambda 表达式
     * @param order 排序类型 {@link SortingField#ORDER_ASC} {@link SortingField#ORDER_DESC}
     * @param <T>   排序字段所属的类型
     * @return 排序字段
     */
    public static <T> SortingField buildSortingFieldOfTableField(Func1<T, ?> func, String order) {
        Assert.isTrue(ArrayUtil.contains(ORDER_TYPES, order), String.format("字段的排序类型只能是 %s/%s", ORDER_TYPES));
        String fieldName = LambdaUtil.getFieldName(func);
        TableField field;
        try {
            field = AnnotationUtils.findAnnotation(LambdaUtil.getRealClass(func).getDeclaredField(fieldName), TableField.class);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException(String.format("获取 %s 失败", fieldName));
        }
        return new SortingField(null, Objects.isNull(field.value()) ? fieldName : field.value(), order);
    }

    /**
     * 构建排序字段(针对排序字段名称和TableField注解中指定的名称不一致的情况，以TableField注解中的value为准)
     *
     * @param func  排序字段的 Lambda 表达式
     * @param order 排序类型 {@link SortingField#ORDER_ASC} {@link SortingField#ORDER_DESC}
     * @param <T>   排序字段所属的类型
     * @return 排序字段
     */
    public static <T> SortingField buildSortingFieldOfTableField(Func1<T, ?> func, SFunction<T, ?> sFunction, String order) {
        Assert.isTrue(ArrayUtil.contains(ORDER_TYPES, order), String.format("字段的排序类型只能是 %s/%s", ORDER_TYPES));
        String fieldName = LambdaUtil.getFieldName(func);
        TableField field;
        try {
            field = AnnotationUtils.findAnnotation(LambdaUtil.getRealClass(func).getDeclaredField(fieldName), TableField.class);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException(String.format("获取 %s 失败", fieldName));
        }
        return new SortingField(sFunction, Objects.isNull(field.value()) ? fieldName : field.value(), order);
    }

    /**
     * 构建默认的排序字段
     * 如果排序字段为空，则设置排序字段；否则忽略
     *
     * @param sortablePageParam 排序分页查询参数
     * @param func              排序字段的 Lambda 表达式
     * @param <T>               排序字段所属的类型
     */
    public static <T> void buildDefaultSortingField(SortablePageParam sortablePageParam, Func1<T, ?> func, SFunction<T, ?> sFunction) {
        if (sortablePageParam != null && CollUtil.isEmpty(sortablePageParam.getSortingFields())) {
            sortablePageParam.setSortingFields(singletonList(buildSortingField(func, sFunction)));
        }
    }

    /**
     * 构建默认的排序字段
     * 如果排序字段为空，则设置排序字段；否则忽略
     *
     * @param sortablePageParam 排序分页查询参数
     * @param func              排序字段的 Lambda 表达式
     * @param <T>               排序字段所属的类型
     */
    public static <T> void buildDefaultSortingFieldOfTableField(SortablePageParam sortablePageParam, Func1<T, ?> func, SFunction<T, ?> sFunction) {
        if (sortablePageParam != null && CollUtil.isEmpty(sortablePageParam.getSortingFields())) {
            sortablePageParam.setSortingFields(singletonList(buildSortingFieldOfTableField(func, sFunction)));
        }
    }
}
