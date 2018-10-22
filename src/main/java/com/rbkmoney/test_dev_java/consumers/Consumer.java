package com.rbkmoney.test_dev_java.consumers;

/** Интерфейс потребителя данных */
public interface Consumer {

    /** Метод обрабатывает полученные данные */
    void consume();

    /** Наименование потребителя */
    String getConsumerName();
}
