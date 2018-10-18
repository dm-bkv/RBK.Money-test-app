package com.rbkmoney.test_dev_java.get_data;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.rbkmoney.test_dev_java.get_data.CsvFormat.*;

public class GetTransactionFromCsv implements TransactionsQueue {

    /** Путь до файла */
    private final Path path;
    /** Признак присутствия в файле заголовка */
    //private final boolean isHeaderLine;
    /** Признак присутствия в файле строки с общей суммой */
    //private final boolean isTotalLine;
    //Здесь я предполагаю, что кодировка и разделитель согласованы для всех подобных файлов в едином формате.
    //Если это не так, то само собой это должно выноситься в properties
    //TODO: вынести эти параметры в properties
    /** Кодировка файла */
    private static final Charset CHARSET = Charset.forName("UTF-8");
    /** Разделитель в файле */
    private static final String DELIMITER = ";";

    public GetTransactionFromCsv(Path path) {
        this.path = path;
    }

    @Override
    public void fillQueue(ConcurrentLinkedQueue<Transaction> queue) throws IOException {
        if (Files.exists(path)) {
            try (BufferedReader reader = Files.newBufferedReader(path, CHARSET)) {
                String transaction;
                while ((transaction = reader.readLine()) != null) {
                    Transaction tran = getTransactionFromString(transaction);
                    if (tran != null) {
                        queue.offer(tran);
                    }
                }
            }
        }
    }

    private static Transaction getTransactionFromString(String transaction) {
        String[] transactionFields = transaction.split(DELIMITER);
        if (transactionFields.length == CsvFormat.values().length) {
            try {
                Transaction transactionResult = new Transaction();
                transactionResult.setId(new BigInteger(transactionFields[PID.columnNumber]));
                transactionResult.setAmount(Float.parseFloat(transactionFields[PAMOUNT.columnNumber]));
                transactionResult.setData(transactionFields[PDATA.columnNumber]);
                return transactionResult;
            } catch(Exception ex){
                saveErrorTransaction(transaction, "Ошибка при создании объекта транзакции: \n" + ex.toString());
                return null;
            }
        } else {
            saveErrorTransaction(transaction, "Ошибка в формате описания транзакции. Неверное количество столбцов");
            return null;
        }
    }

    private static void saveErrorTransaction(String transaction, String comment) {
        //TODO: реализовать логирование ошибки
        System.err.println("Ошибка при загрузке транзакции (" + transaction + "): " + comment);
    }

}



/*

        List<String> transactionStringList = Files.readAllLines(path, CHARSET);
        for (String transactionString : transactionStringList) {
            Transaction tran = getTransactionFromString(transactionString);
            if (tran != null) {
                resultTransactionList.add(tran);
            }
        }
        return resultTransactionList;

 */