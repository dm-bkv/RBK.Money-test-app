package com.rbkmoney.test_dev_java.get_data;

public interface QueueFiller {

    /**
     * Метод заполняет переданную ему очередь данными для дальнейшей обработки
     *
     * @param transportQueue очередь
     */
    void fillQueue(TransportQueue transportQueue);

}
