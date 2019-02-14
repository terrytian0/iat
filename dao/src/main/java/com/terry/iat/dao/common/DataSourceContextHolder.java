package com.terry.iat.dao.common;

/**
 * @author houyin.tian
 */
public class DataSourceContextHolder {
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<String>();
    /**
     * @Description: 设置数据源类型
     * @param dataSourceType  数据库类型
     * @return void
     * @throws
     */
    public static void setDataSourceType(String dataSourceType) {
        CONTEXT_HOLDER.set(dataSourceType);
    }

    /**
     * @Description: 获取数据源类型
     * @param
     * @return String
     * @throws
     */
    public static String getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * @Description: 清除数据源类型
     * @param
     * @return void
     * @throws
     */
    public static void clearDataSourceType() {
        CONTEXT_HOLDER.remove();
    }
}