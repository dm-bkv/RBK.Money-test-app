package com.rbkmoney.test_dev_java;

import com.rbkmoney.test_dev_java.commands.ResultCommandActionType;
import com.rbkmoney.test_dev_java.common.Props;
import com.rbkmoney.test_dev_java.consumers.ConsumerActionType;
import com.rbkmoney.test_dev_java.producers.ProducerSourceDataType;

/** Класс, загружающий параметры для входных данных */
public class AppProperties extends Props {

    /** Путь до файла настроек к входным данным */
    public static final String APP_PROPS_PATH = "src/main/resources/META-INF/app.properties";

    public AppProperties() {
        super(APP_PROPS_PATH);
    }

    public ProducerSourceDataType getProducerSourceDataType() throws Exception {
        return ProducerSourceDataType.getTypeByName(getProperties().getProperty("producer.sourceDataType"));
    }

    public ConsumerActionType getConsumerActionType() throws Exception {
        return ConsumerActionType.getTypeByName(getProperties().getProperty("consumer.action"));
    }

    public ResultCommandActionType getResultCommandActionType() throws Exception {
        return ResultCommandActionType.getTypeByName(getProperties().getProperty("resultCommand.action"));
    }

}
