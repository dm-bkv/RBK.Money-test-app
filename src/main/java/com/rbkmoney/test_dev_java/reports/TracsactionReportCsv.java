package com.rbkmoney.test_dev_java.reports;

import com.rbkmoney.test_dev_java.get_data.Transaction;

import java.nio.file.Path;
import java.util.List;

public class TracsactionReportCsv implements Report{

    private List<Transaction> transactions;

    private Path path;

    private static final String HEADER = "ID;AMOUNT;DATA;COMMENT;\n";

    private static final String DELIMITER = ";";

    private StringBuilder csvReport;

    public TracsactionReportCsv(List<Transaction> transactions, Path path) {
        this.transactions = transactions;
        this.path = path;
    }

    @Override
    public void generateReport() {
        csvReport = new StringBuilder(HEADER);
        for (Transaction transaction : transactions) {
            csvReport.append(transaction.getId()).append(DELIMITER)
                    .append(transaction.getAmount()).append(DELIMITER)
                    .append(transaction.getData()).append(DELIMITER)
                    .append(transaction.getComment()).append(DELIMITER + "\n");
        }
        int total = transactions.size();
        csvReport.append("TOTAL:;").append(total).append(";;;\n");
    }

    private boolean saveCsvReport(StringBuilder csvReport) {

        return false;
    }

}
