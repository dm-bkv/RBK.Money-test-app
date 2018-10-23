package com.rbkmoney.test_dev_java.producers;

import com.rbkmoney.test_dev_java.AppProperties;
import com.rbkmoney.test_dev_java.common.Transaction;
import com.rbkmoney.test_dev_java.common.TransportQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.rbkmoney.test_dev_java.producers.CsvFormat.*;

/** Класс, отвечающий за получение данных из CSV файла и занесение их в очередь для дальнейшей обработки */
public class TransactionsFromCsvProducer implements Producer {

    /** Логгер */
    private static final Logger LOG = LoggerFactory.getLogger(TransactionsFromCsvProducer.class);
    /** Очередь для заполнения */
    private final TransportQueue<Transaction> transportQueue;
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
    private static final int MAX_TRYING_COUNT = 30;
    /** Время ожидания до след. попытки записать данные в очередь в случае, когда она переполнена */
    private static final int QUEUE_TIMEOUT = 1000;

    public TransactionsFromCsvProducer(TransportQueue<Transaction> transportQueue, AppProperties appProperties) {
        this.transportQueue = transportQueue;
        transportQueue.startFilling();
        transportQueue.setName(transportQueue.getName() + "-CSV");
        Properties props = appProperties.getProperties();
        try {
            //TODO: стоит сделать проверки на параметры
            this.path = Paths.get(props.getProperty("csv.dir"));
            this.isHeaderLine = Boolean.parseBoolean(props.getProperty("csv.isHeaderLine"));
            this.isTotalLine = Boolean.parseBoolean(props.getProperty("csv.isTotalLine"));
            this.charset = Charset.forName(props.getProperty("csv.charset"));
            this.delimiter = props.getProperty("csv.delimiter");
            LOG.info("Из файла настроек получена следующая информация: путь до файла я '{}', кодировка {}, " +
                    "разделитель '{}', заголовок таблицы " + (this.isHeaderLine ? "присутствует" : "отсутствует") +
                    ", итоговая часть " + (this.isTotalLine ? "присутствует" : "отсутствует"), path, charset, delimiter);
        } catch (Exception e) {
            transportQueue.stopFilling();
            LOG.error("Ошибка при инициализации класса загрузки транзакций из CSV файла: {}", e);
        }
    }

    @Override
    public void produce() {
        LOG.info("Начало выполнения процедуры получения списка транзакций из CSV файла {}", path);
        if (Files.exists(path)) {
            try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
                LinkedBlockingQueue<Transaction> queue = transportQueue.getQueue();
                //String transaction = null;
                boolean isSuccessfulInsert = true;
                int tryingCount = 0;

                String currLine = null;
                if (isHeaderLine) {
                    currLine = reader.readLine();
                }

                String nextLine = reader.readLine();
                if (nextLine == null) {
                    LOG.warn("В представленном файле транзакций не обнаружено");
                    return;
                }

                do {
                    if (isSuccessfulInsert) {
                        tryingCount = 0;
                        currLine = nextLine;
                        nextLine = reader.readLine();
                        if (isTotalLine && nextLine == null) {
                            break;
                        }
                        Transaction tran = prepareTransactionFromCsvString(currLine, transportQueue.getName());
                        if (tran != null) {
                            LOG.trace("Получена транзакция: {}", tran);
                            isSuccessfulInsert = queue.offer(tran, QUEUE_TIMEOUT, TimeUnit.MILLISECONDS);
                        }
                    } else {
                        LOG.warn("Невозможно добавить новый элемент в очередь, так как она заполнена. Попытка номер {}",
                                ++tryingCount);
                        if (tryingCount > MAX_TRYING_COUNT) {
                            LOG.error("Допущено максимальное количество попыток. Обработка транзакций будет завершена");
                            break;
                        }
                    }
                } while (transportQueue.isFilling() && currLine != null);

            } catch (Exception e) {
                LOG.error("Во время выполнения процедуры получения списка транзакций из CSV файла произошла ошибка:\n {}", e);
            } finally {
                transportQueue.stopFilling();
            }
        } else {
            transportQueue.stopFilling();
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
    private Transaction prepareTransactionFromCsvString(String transaction, String transportQueueName) {
        if (transaction == null) {
            return null;
        }
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

    @Override
    public String getProducerName() {
        return "TransactionsFromCsvProducer";
    }

}
