package com.rbkmoney.test_dev_java.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Properties;

/** Абстрактный класс получения настроек */
public abstract class Props {

        private static final Logger LOG = LoggerFactory.getLogger(Props.class);

        private final Properties PROPS;

        public Props(String sourcePath) {
            PROPS = new Properties();
            LOG.info("Загрузка параметров из файла {}...", sourcePath);
            FileInputStream fis;
            try {
                fis = new FileInputStream(sourcePath);
                PROPS.load(fis);
                LOG.info("Завершение загрузки входящих данных из файла {}", sourcePath);
            } catch (Exception e) {
                LOG.error("Ошибка при загрузке файла настроек: {}", e);
            }
        }

        public Properties getProperties() {
            return PROPS;
        }

}
