package com.rbkmoney.test_dev_java.consumers;

/** Список потребителей */
public enum ConsumerActionType {

    /** Получение списка невалидный транзакций */
    GET_INVALID_TRANSACTIONS("getInvalidTransactions"),
    /** Полчение списка валидных транзакций */
    GET_VALID_TRANSACTIONS("getValidTransactions");

    /** Наименование действия, которое нужно будет выполнять */
    private final String actionName;

    ConsumerActionType(String actionName) {
        this.actionName = actionName;
    }

    public static ConsumerActionType getTypeByName(String actionName) throws Exception {
        for (ConsumerActionType actionType : values()) {
            if (actionType.getActionName().equalsIgnoreCase(actionName)) {
                return actionType;
            }
        }
        throw new Exception("Не найдено соответствия для команды " + actionName);
    }

    private String getActionName() {
        return actionName;
    }
}
