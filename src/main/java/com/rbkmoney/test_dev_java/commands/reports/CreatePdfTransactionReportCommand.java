package com.rbkmoney.test_dev_java.commands.reports;

import com.rbkmoney.test_dev_java.commands.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreatePdfTransactionReportCommand implements Command {

    /** Логгер */
    private static final Logger LOG = LoggerFactory.getLogger(CreatePdfTransactionReportCommand.class);

    @Override
    public void execute() {
        LOG.error("Создание транзакционного отчета в формате PDF не реализовано");
    }
}
