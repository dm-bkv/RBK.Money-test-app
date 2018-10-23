package com.rbkmoney.test_dev_java.commands;

import com.rbkmoney.test_dev_java.AppProperties;
import com.rbkmoney.test_dev_java.commands.reports.CreateCsvTransactionReportCommand;
import com.rbkmoney.test_dev_java.commands.reports.CreatePdfTransactionReportCommand;
import com.rbkmoney.test_dev_java.common.Transaction;

import java.util.List;

/**
 * Фабрика команд
 *
 * Примечание: она как раз обеспечивает удовлетворению условий того, что с полученными данными могут быть проведены
 *             различные манимуляции (сохранение в файл или БД, отправка на почту и т.п.)
 */
public class CommandsFactory {

    private final List<Transaction> transactions;

    private final AppProperties appProperties;

    public CommandsFactory(List<Transaction> transactions, AppProperties appProperties) {
        this.transactions = transactions;
        this.appProperties = appProperties;
    }

    public Command getCommand() throws Exception {
        switch (appProperties.getResultCommandActionType()) {
            case CREATE_CSV_TRANSACTION_REPORT:
                return new CreateCsvTransactionReportCommand(transactions, appProperties, "InvalidTransactionsCsvReport");
            case CREATE_PDF_TRANSACTION_REPORT:
                return new CreatePdfTransactionReportCommand();
            default:
                throw new Exception("Реализации комманлы для " + appProperties.getResultCommandActionType().name() + " не найдено");
        }
    }

}
