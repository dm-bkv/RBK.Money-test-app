package com.rbkmoney.test_dev_java.commands.reports;

import com.rbkmoney.test_dev_java.commands.Command;
import com.rbkmoney.test_dev_java.common.Props;
import com.rbkmoney.test_dev_java.common.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/** Класс, формирующий CSV отчет из входных данных */
public class CreateCsvTransactionReportCommand implements Command {

    /** Логгер */
    private static final Logger LOG = LoggerFactory.getLogger(CreateCsvTransactionReportCommand.class);
    /** Список транзакций */
    private List<Transaction> transactions;
    /** Параметры приложения */
    private Path path;
    /** Наименование отчета */
    private String reportName;
    /** Заголовок отчета */
    private static final String HEADER = "ID;AMOUNT;DATA;COMMENT;\n";
    /** Разделитель данных в отчете */
    private static final String DELIMITER = ";";

    public CreateCsvTransactionReportCommand(List<Transaction> transactions, Props props, String reportName) {
        this.transactions = transactions;
        this.path = Paths.get(props.getProperties().getProperty("csv.dir")).getParent();
        this.reportName = reportName;
    }

    @Override
    public void execute() {
        StringBuilder csvReport = new StringBuilder(HEADER);
        for (Transaction transaction : transactions) {
            csvReport.append(transaction.getId()).append(DELIMITER)
                    .append(transaction.getAmount()).append(DELIMITER)
                    .append(transaction.getData()).append(DELIMITER)
                    .append(transaction.getComment()).append(DELIMITER + "\n");
        }
        int total = transactions.size();
        csvReport.append("TOTAL:;").append(total).append(";;;\n");
        LOG.error("result:\n" + csvReport);
        saveCsvReport(csvReport);
    }

    /**
     * Сохранение CSV отчета в файл
     *
     * @param csvReport сформированные данные
     */
    private void saveCsvReport(StringBuilder csvReport) {
        Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("_yyyy-MM-dd_hh-mm-ss-S");
        String csvReportName = reportName + formatForDateNow.format(date) + ".csv";
        Path reportPath = Paths.get(path.toString(), csvReportName);
        try(FileWriter writer = new FileWriter(reportPath.toString(), true)) {
            writer.write(csvReport.toString());
            writer.flush();
        } catch (Exception ex) {
            LOG.error("Ошибка при сохранении отчета: \n{}", ex);
        }
    }

}
