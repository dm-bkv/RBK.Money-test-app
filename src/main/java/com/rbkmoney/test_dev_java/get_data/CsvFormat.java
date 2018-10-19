package com.rbkmoney.test_dev_java.get_data;

/** Класс, описывающий формат принимаемого CSV файла с транзакциями */
public enum CsvFormat {

    /** ID транзакции */
    PID(0),
    /** Сумма транзакции */
    PAMOUNT(1),
    /** Данные по транзакции */
    PDATA(2);

    /** Номер столбца в CSV файле */
    private int columnNumber;

    CsvFormat(int number) {
        this.columnNumber = number;
    }

    public int getColumnNumber() {
        return columnNumber;
    }
}
