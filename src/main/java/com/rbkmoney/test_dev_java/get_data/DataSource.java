package com.rbkmoney.test_dev_java.get_data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Класс, используемый для подключения к БД
 */
public class DataSource {

    /** Логгер */
    public static final Logger LOG = LoggerFactory.getLogger(DataSource.class);

    public static HikariDataSource getDataSource() {
        Properties props = new Properties();
        FileInputStream fis;
        try {
            fis = new FileInputStream("src/main/resources/META-INF/db.properties");
            props.load(fis);
            HikariConfig config = new HikariConfig();
            config.setDriverClassName(props.getProperty("dataSource.driverClassName"));
            config.setJdbcUrl(props.getProperty("dataSource.url"));
            config.setUsername(props.getProperty("dataSource.username"));
            config.setPassword(props.getProperty("dataSource.password"));
            return new HikariDataSource(config);
        } catch (Exception e) {
            LOG.error("Возникла ошибка при получении источника данных:\n {}", e);
            return null;
        }
    }

}
