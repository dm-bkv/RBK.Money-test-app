package com.rbkmoney.test_dev_java;

import com.rbkmoney.test_dev_java.commands.CommandsFactory;
import com.rbkmoney.test_dev_java.commands.ProducerConsumerModelCommand;
import com.rbkmoney.test_dev_java.common.DataSource;
import com.rbkmoney.test_dev_java.common.Transaction;
import com.rbkmoney.test_dev_java.common.TransactionTransportQueue;
import com.rbkmoney.test_dev_java.consumers.ConsumersFactory;
import com.rbkmoney.test_dev_java.consumers.helpers.TransactionsJdbcHelper;
import com.rbkmoney.test_dev_java.producers.ProducersFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Java конфигурация Spring */
@Configuration
public class SpringConfiguration {
/*
    @Bean
    public ProducerConsumerModelCommand ProducerConsumerModelCommand() throws Exception {
        return new ProducerConsumerModelCommand(ProducersFactory(), ConsumersFactory(), CashedExecutorService());
    }

    @Bean
    public ProducersFactory ProducersFactory() {
        return new ProducersFactory(TransactionTransportQueue(), AppProps());
    }

    @Bean
    public ConsumersFactory ConsumersFactory() throws Exception {
        return new ConsumersFactory(TransactionsJdbcHelper(),
                TransactionTransportQueue(),
                InvalidTransactionsList(),
                CommandsFactory(),
                CashedExecutorService(),
                AppProps());
    }

    @Bean
    public CommandsFactory CommandsFactory() {
        return new CommandsFactory(InvalidTransactionsList(), AppProps());
    }

    @Bean
    public List<Transaction> InvalidTransactionsList() {
        return new CopyOnWriteArrayList<>();
    }

    @Bean
    public TransactionsJdbcHelper TransactionsJdbcHelper() {
        return new TransactionsJdbcHelper(DataSource());
    }

    @Bean
    public TransactionTransportQueue TransactionTransportQueue() {
        return new TransactionTransportQueue("TransactionTransportQueue");
    }

    @Bean
    public ExecutorService CashedExecutorService() {
        return Executors.newCachedThreadPool();
    }

    @Bean
    public DataSource DataSource() {
        return DataSource.getInstance();
    }

    @Bean
    public AppProperties AppProps() {
        return new AppProperties();
    }
*/
}
