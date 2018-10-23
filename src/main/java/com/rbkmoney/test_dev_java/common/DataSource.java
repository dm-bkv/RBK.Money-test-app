package com.rbkmoney.test_dev_java.common;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
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
    /** Пул соединений */
    private HikariDataSource hikariDataSource;

    /**
     * Получение пула соединений
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

    /**
     * Загрузка пула соединений
     */
    private void loadDataSource() throws Exception {
        LOG.info("Инициализация пула соединений Hikari...");
        Properties props = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try(InputStream resourceStream = loader.getResourceAsStream("source_data.properties")) {
            props.load(resourceStream);
            HikariConfig config = new HikariConfig();
            config.setDriverClassName(props.getProperty("dataSource.driverClassName"));
            config.setJdbcUrl(props.getProperty("dataSource.url"));
            config.setUsername(props.getProperty("dataSource.username"));
            config.setPassword(props.getProperty("dataSource.password"));
            hikariDataSource = new HikariDataSource(config);
        } catch (IOException e) {
            LOG.error("Возникла ошибка при получении источника данных:\n {}", e);
            throw new Exception("Возникла ошибка при получении источника данных: \n" + e);
        }
        LOG.info("Пул соединений Hikari проинициализирован!");
    }

    /** Закрытие пула соединений */
    public void closeDataSource() {
        if (hikariDataSource != null) {
            hikariDataSource.close();
        }
    }

}
