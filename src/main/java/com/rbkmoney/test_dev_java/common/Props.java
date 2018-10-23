package com.rbkmoney.test_dev_java.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Абстрактный класс получения настроек */
public abstract class Props {

        private static final Logger LOG = LoggerFactory.getLogger(Props.class);

        private final Properties PROPS;

        public Props(String sourcePath) throws IOException {
            LOG.info("Загрузка параметров из файла {}...", sourcePath);
            PROPS = new Properties();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            try(InputStream resourceStream = loader.getResourceAsStream(sourcePath)) {
                PROPS.load(resourceStream);
                LOG.info("Завершение загрузки входящих данных из файла {}", sourcePath);
            } catch (IOException e) {
                LOG.error("Ошибка при загрузке файла настроек: {}", e);
                throw new IOException(e);
            }
        }

        public Properties getProperties() {
            return PROPS;
        }

}
