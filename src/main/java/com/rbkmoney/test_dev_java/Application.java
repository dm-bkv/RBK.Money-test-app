package com.rbkmoney.test_dev_java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


public class Application {

    /** Логгер */
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        LOG.info("Application start...");
        ApplicationContext context =
                new FileSystemXmlApplicationContext("src/main/resources/META-INF/context.xml");

        LOG.info("Application finished!");
    }

}
