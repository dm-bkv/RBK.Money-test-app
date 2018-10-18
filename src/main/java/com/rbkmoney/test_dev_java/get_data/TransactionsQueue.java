package com.rbkmoney.test_dev_java.get_data;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

public interface TransactionsQueue {

    void fillQueue(ConcurrentLinkedQueue<Transaction> queue) throws IOException;

}
