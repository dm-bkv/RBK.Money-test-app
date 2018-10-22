package com.rbkmoney.test_dev_java.producers;

/** Класс, описывающий список возможных исходных данных */
public enum ProducerSourceDataType {

    /** Получение данных из CSV файла */
    CSV,
    /** Получение данных из JSON сообщения */
    JSON,
    /** Получение данных из SOAP сообщения */
    SOAP,
    /** Получение данных из текстового файла */
    TEXT;

    /**
     * Получение типа исходных данный
     *
     * @param sourceType строка с наиманование типа исходных данных
     * @return тип, либо (если представленное наименование не было представлено) - ошибку
     */
    public static ProducerSourceDataType getTypeByName(String sourceType) throws Exception {
        for (ProducerSourceDataType value : values()) {
            if (value.name().equalsIgnoreCase(sourceType)) {
                return value;
            }
        }
        throw new Exception("Неподдерживаемый тип исходных данных " + sourceType);
    }

}
