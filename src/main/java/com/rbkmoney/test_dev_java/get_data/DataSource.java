package com.rbkmoney.test_dev_java.get_data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DataSource {

    public static HikariDataSource configureDataSource() {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
