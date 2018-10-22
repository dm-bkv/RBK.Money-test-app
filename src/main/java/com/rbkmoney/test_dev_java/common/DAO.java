package com.rbkmoney.test_dev_java.common;

import com.zaxxer.hikari.HikariDataSource;

/** Абстрактный класс описания источника данных
 *
 *  Примечание: при проектировании предполагаю, что система может работать не только с транзакциями, но и с какими-то
 *              другими бизнес объектами (например, мерчантами)
 */
public abstract class DAO {

    /** Источник данных */
    private final HikariDataSource dataSource;

    public DAO(DataSource dataSource) throws Exception {
        this.dataSource = dataSource.getDataSource();
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

}
