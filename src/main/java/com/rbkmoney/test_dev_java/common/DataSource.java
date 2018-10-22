package com.rbkmoney.test_dev_java.common;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Класс, используемый для подключения к БД
 *
 * Примечание: в перспективе может быть несколько БД, поэтому может быть несколько DataSource к разным БД,
 *             но в рамках данной работы считаю что такой источник один. Если их будет несколько, то из данной
 *             реализации можно сделать шаблон и свободно использовать в проекте
 */
public class DataSource {

    /** Логгер */
    private final static Logger LOG = LoggerFactory.getLogger(DataSource.class);
    /** Экземпляр источника данных */
    private static DataSource dataSource = new DataSource();
    /** */
    private HikariDataSource hikariDataSource;

//    public static DataSource getInstance() {
//        return dataSource;
//    }

    /**
     * Получение источника данных
     *
     * Примечание: по чути можно было вынести весь метод на статику, но в таком случае если при подключении к БД
     *             будут проблемы, то они "выскочат" раньше чем надо как по мне. С другой стороны если не имеем
     *             подключения к БД с транзакциями, то и не нужно ничего делать (в рамках данного таска).
     *             Если же сервис в принципе решает задачу сравнения (например, дополнительно надо и мерчантов сравнить),
     *             то он просто схлопываться не должен, поэтому
     *
     * @return возвращает источник данных; в случае ошибки null
     */
    public HikariDataSource getDataSource() throws Exception {
        if (hikariDataSource == null) {
            loadDataSource();
        }
        return hikariDataSource;
    }

    private void loadDataSource() throws Exception {
        Properties props = new Properties();
        FileInputStream fis;
        try {
            fis = new FileInputStream("src/main/resources/META-INF/source_data.properties");
            props.load(fis);
            HikariConfig config = new HikariConfig();
            config.setDriverClassName(props.getProperty("dataSource.driverClassName"));
            config.setJdbcUrl(props.getProperty("dataSource.url"));
            config.setUsername(props.getProperty("dataSource.username"));
            config.setPassword(props.getProperty("dataSource.password"));
            hikariDataSource = new HikariDataSource(config);
        } catch (Exception e) {
            LOG.error("Возникла ошибка при получении источника данных:\n {}", e);
            throw new Exception("Возникла ошибка при получении источника данных: \n" + e);
        }
    }

    public void closeDataSource() {
        if (hikariDataSource != null) {
            hikariDataSource.close();
        }
    }

    //private DataSource() {}

}
