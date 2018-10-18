package com.rbkmoney.test_dev_java.get_data;

/** Класс, описывающий столбцы таблицы Transactions */
public enum SourceDbFormat {

    /** ID транзакции */
    ID("id"),
    /** Сумма транзакции */
    AMOUNT("amount"),
    /** Данные по транзакции */
    DATA("data");

    /** Наименование столбца */
    private String columnName;

    SourceDbFormat(String name) {
        this.columnName = name;
    }

    public String getColumnName() {
        return columnName;
    }
}
