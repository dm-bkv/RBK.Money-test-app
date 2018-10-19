package com.rbkmoney.test_dev_java.get_data;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DAO {

    /** Логгер */
    public static final Logger LOG = LoggerFactory.getLogger(DAO.class);

    /** Источник данных */
    private final HikariDataSource dataSource;

    public DAO(DataSource dataSource) {
        this.dataSource = dataSource.getDataSource();
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

}
