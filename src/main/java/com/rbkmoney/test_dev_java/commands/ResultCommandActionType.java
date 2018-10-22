package com.rbkmoney.test_dev_java.commands;

/** Список доступных для выполнения команд от внешней системы или файла настроек */
public enum ResultCommandActionType {

    /** Команда создания CSV отчета */
    CREATE_CSV_TRANSACTION_REPORT("createCsvTransactionReport"),
    /** Команда создания PDF отчета */
    CREATE_PDF_TRANSACTION_REPORT("createPdfTransactionReport");

    /** Наименование команды, поступившей из файла properties */
    private final String actionName;

    ResultCommandActionType(String actionName) {
        this.actionName = actionName;
    }

    /**
     * Получение экземпляра по наименованию команды
     *
     * @param actionName наименование команды
     * @return
     */
    public static ResultCommandActionType getTypeByName(String actionName) throws Exception {
        for (ResultCommandActionType actionType : values()) {
            if (actionType.getActionName().equalsIgnoreCase(actionName)) {
                return actionType;
            }
        }
        throw new Exception("Не найдено подходящего типа для команды " + actionName);
    }

    private String getActionName() {
        return actionName;
    }
}
