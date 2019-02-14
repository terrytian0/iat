package com.terry.iat.dao.common;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author houyin.tian
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSourceType();
    }
}