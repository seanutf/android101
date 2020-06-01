package com.seanutf.android.commonutil.util;

import java.util.List;
import java.util.Map;

/**
 * 集合工具类
 * 包含对List、Map的处理
 */
public class CollectionUtils {

    public static boolean isNotEmptyList(List list) {
        return list != null && list.size() > 0;
    }

    public static boolean isEmptyList(List list) {
        return list == null || list.size() <= 0;
    }

    public static boolean isListHasIndex(List list, int index) {
        if (!isNotEmptyList(list)) {
            return false;
        }
        int size = list.size();
        if (index < 0 || index >= size) {
            return false;
        }
        return list.get(index) != null;
    }

    /**
     * 获取列表中的某一个位置的元素
     */
    public static <T> T objectAtIndex(List<T> list, int index) {
        if (isNotEmptyList(list)) {
            if (index >= 0 && index < list.size()) {
                return list.get(index);
            }
        }
        return null;
    }

    public static boolean isEmptyMap(Map map) {
        return map == null || map.size() <= 0;
    }

    public static boolean isNotEmptyMap(Map map) {
        return map != null && map.size() > 0;
    }
}
