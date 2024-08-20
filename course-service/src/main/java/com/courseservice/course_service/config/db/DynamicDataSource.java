package com.courseservice.course_service.config.db;

import com.courseservice.course_service.config.db.enums.DataSourceType;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Overridden default db to allow support for datasource routing
 */

public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected String determineCurrentLookupKey() {
        String dataSource = DynamicDataSourceHolder.getDataSource();
        if (DynamicDataSourceHolder.getDataSource() != null) {
            System.out.println("Currently using database: " + dataSource);
            return DynamicDataSourceHolder.getDataSource();
        }
        System.out.println("Currently using database: MASTER (default)");
        return DataSourceType.MASTER.getType();

    }
}

