package com.rbkmoney.test_dev_java;

import com.rbkmoney.test_dev_java.commands.ProducerConsumerModelCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/** Основной класс приложения.
 *
 * Примечания:
 *           - при разработке я считаю, что количество постепающих данных может быть достоточно большим, поэтому
 *             использовал producer-consumer паттерн
 *           - я прекрасно понимаю, что в рамках данной задачи вышел за YAGNI и KISS принципы, но мне кажется
 *             проектировать систему иначе было бы неправильно. Плюс в ТЗ или очно не говорилось, что это должно быть MVP
 *           - есть определенный набор элементов, который можно так же улучшить, но реализовывать их не стал, так как
 *             это займет дополнительное время и выходит за рамки исходного ТЗ
 *           - учитывая, что путь к файлу с транзакциями задается в файле *.properties, то считаю логичным и остальные
 *             параметры задавать там же.
 *           - в принципе в качестве источника исходных данных могут выступать разные БД, но в рамках данной работы
 *             считаю, что все данные по транзакциям хранятся в рамках одной БД
 *
 * */
public class Application {

    /** Логгер */
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        LOG.info("Начало работы приложения...");
        ApplicationContext context =
                new FileSystemXmlApplicationContext("src/main/resources/META-INF/context.xml");
        try {
            context.getBean(ProducerConsumerModelCommand.class).execute();
        } catch (Throwable e) {
            LOG.error("Во время работы приложения произошла ошибка: {}", e);
        }
        LOG.info("Приложение заверщено!");
    }

}

// Thread.currentThread().setName("TransactionsFromCsvProducer-" + Thread.currentThread().getId());
//SpringApplication.run(DemoApplication.class, args);