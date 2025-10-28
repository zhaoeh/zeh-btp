package com.snowflake.cache.view;

import com.snowflake.cache.view.construction.NodeDimensionCacheView;

/**
 * 缓存视图构建器
 */
public class ViewBuilder {

    /**
     * 构建节点全部玩家单个玩法投注累计限额缓存视图对象
     * @return 构建的对象
     */
    public static NodeDimensionCacheView buildAllPlayersSingleIssueLimitView(){
        return new NodeDimensionCacheView();
    }

    /**
     * 构建版本号视图
     * @return
     */
    public static NodeDimensionCacheView buildAllPlayersSingleIssueLimitVersionView(){
        return new NodeDimensionCacheView();
    }
}
