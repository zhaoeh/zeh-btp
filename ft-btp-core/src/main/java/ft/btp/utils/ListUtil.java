package ft.btp.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 列表工具类
 */
@Slf4j
public class ListUtil {
 
    /**
     * 将list以某个符号分隔成字符串
     *
     * @param list   字符串列表
     * @param symbol 符号
     * @return
     */
    public static String getStringByList(List<String> list, String symbol) {
        if (CollectionUtils.isEmpty(list)) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i != list.size() - 1) {
                stringBuilder.append(list.get(i)).append(symbol);
            } else {
                stringBuilder.append(list.get(i));
            }
        }
        return stringBuilder.toString();
    }
 
    /**
     * 将set以某个符号分隔成字符串
     *
     * @param set    set
     * @param symbol 符号
     * @return
     */
    public static String getStringBySet(Set<String> set, String symbol) {
        List<String> stringList = new ArrayList<>(set);
        return getStringByList(stringList, symbol);
    }
 
    public static List<String> getListByString(String s) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isEmpty(s)) {
            return list;
        }
        String[] array = s.split(",");
        list = Arrays.asList(array);
        return list;
    }
 
    public static List<String> getListByString(String s, String symbol) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isEmpty(s)) {
            return list;
        }
        String[] array = s.split(symbol);
        list = Arrays.asList(array);
        return list;
    }
 
    public static <S, T> void copyList(Collection<S> sCollection, Collection<T> tCollection, Class<T> tClass) {
        if (sCollection == null || tCollection == null || sCollection.size() == 0) {
            return;
        }
        try {
            for (S s : sCollection) {
                T t = tClass.newInstance();
                BeanUtils.copyProperties(s, t);
                tCollection.add(t);
            }
        } catch (Exception e) {
            log.error("列表转换失败", e);
        }
    }
 
    public static <S, T> List<T> copyList(Collection<S> sCollection, Class<T> tClass) {
        List<T> list = new ArrayList<>();
        if (sCollection == null || sCollection.size() == 0) {
            return list;
        }
        try {
            for (S s : sCollection) {
                T t = tClass.newInstance();
                BeanUtils.copyProperties(s, t);
                list.add(t);
            }
        } catch (Exception e) {
            log.error("列表转换失败", e);
        }
        return list;
    }
 
}
 