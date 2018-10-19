package com.rbkmoney.test_dev_java.get_data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.rbkmoney.test_dev_java.get_data.CsvFormat.*;

/** Класс, отвечающий за получение данных из CSV файла и занесение их в очередь для дальнейшей обработки */
public class TransactionsQueueFromCsv implements QueueFiller {

    /** Логгер */
    public static final Logger LOG = LoggerFactory.getLogger(TransactionsQueueFromCsv.class);
    /** Путь до файла */
    private Path path = null;
    /** Признак присутствия в файле заголовка */
    private boolean isHeaderLine;
    /** Признак присутствия в файле строки с общей суммой */
    private boolean isTotalLine;
    /** Кодировка файла */
    private Charset charset = Charset.forName("UTF-8");
    /** Разделитель в файле */
    private String delimiter = ";";
    /** Максимальный размер очереди */
    private static final int QUEUE_MAX_SIZE = 100;
    /** Время ожидания до след. попытки записать данные в очередь в случае, когда она переполнена */
    private static final int QUEUE_TIMEOUT = 1000;

    public TransactionsQueueFromCsv() {
        Properties props = new Properties();
        FileInputStream fis;
        try {
            fis = new FileInputStream("src/main/resources/META-INF/csv.properties");
            props.load(fis);
            //TODO: возможно стоит сделать проверки на параметры. С другой стороны формат задается заранее и в случае ошибки об этом сразу станет ясно
            this.path = Paths.get(props.getProperty("csv.dir"));
            this.isHeaderLine = Boolean.parseBoolean(props.getProperty("csv.isHeaderLine"));
            this.isTotalLine = Boolean.parseBoolean(props.getProperty("csv.isTotalLine"));
            this.charset = Charset.forName(props.getProperty("csv.charset"));
            this.delimiter = props.getProperty("csv.delimiter");
            LOG.info("Из файла настроек получена следующая информация: путь до файла я '{}', кодировка {}, " +
                    "разделитель '{}', заголовок таблицы " + (this.isHeaderLine ? "присутствует" : "отсутствует") +
                    ", итоговая часть " + (this.isTotalLine ? "присутствует" : "отсутствует"), path, charset, delimiter);
        } catch (Exception e) {
            LOG.error("Ошибка при инициализации класса загрузки транзакций из CSV файла: {}", e);
        }

    }

    @Override
    public void fillQueue(TransportQueue transportQueue) {
        LOG.info("Начало выполнения процедуры получения списка транзакций из CSV файла {}", path);
        if (Files.exists(path)) {
            try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
                transportQueue.startFilling();
                ConcurrentLinkedQueue<Transaction> queue = transportQueue.getQueue();
                String transaction = reader.readLine();
                while (transportQueue.isFilling() && transaction != null) {
                    if (queue.size() < QUEUE_MAX_SIZE) {
                        Transaction tran = prepareTransactionFromString(transaction, transportQueue.getName());
                        if (tran != null) {
                            queue.offer(tran);
                        }
                        transaction = reader.readLine();
                    } else {
                        wait(QUEUE_TIMEOUT);
                    }
                }
            } catch (Exception e) {
                LOG.error("Во время выполнения процедуры получения списка транзакций из CSV файла произошла ошибка:\n {}", e);
            } finally {
                transportQueue.stopFilling();
            }
        } else {
            LOG.warn("В директории {} отсутствует целевой файл", path);
        }
        LOG.info("Процедура полученя информации из файла {} выполнениа", path);
    }

    /**
     * Метод получает объект {@Object Transaction} из считанной строки
     *
     * @param transaction строка, содержащая информацию о транзакции
     * @param transportQueueName наименование объекта очереди-конвеера                   
     * @return объект {@Object Transaction} или null
     */
    private Transaction prepareTransactionFromString(String transaction, String transportQueueName) {
        String[] transactionFields = transaction.split(delimiter);
        if (transactionFields.length == CsvFormat.values().length) {
            try {
                Transaction transactionResult = new Transaction();
                transactionResult.setId(Integer.parseInt(transactionFields[PID.getColumnNumber()]));
                transactionResult.setAmount(Float.parseFloat(transactionFields[PAMOUNT.getColumnNumber()]));
                transactionResult.setData(transactionFields[PDATA.getColumnNumber()]);
                return transactionResult;
            } catch(Exception ex) {
                //TODO: по-хорошему данную информацию нужно передавать в отдельный файл, 
                //TODO: но т.к. формат ТЗ свободный пусть будет в обычном логе со спец. пометкой
                LOG.error("Загрузка '{}' | Ошибка при создании объекта транзакции из строки {}:\n {}",
                        transportQueueName, transaction, ex);
                return null;
            }
        } else {
            LOG.error("Загрузка '{}' | Ошибка в формате описания транзакции '{}'. Неверное количество столбцов", 
                    transportQueueName, transaction);
            return null;
        }
    }

}
