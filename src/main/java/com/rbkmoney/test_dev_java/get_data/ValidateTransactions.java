package com.rbkmoney.test_dev_java.get_data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ValidateTransactions {

    private TransactionsFacade transactionFacade;

    private ConcurrentLinkedQueue<Transaction> transactionQueue;

    private static final int THREAD_POOL_SIZE = 20;

    public ValidateTransactions(TransactionsFacade transactionFacade,
                                ConcurrentLinkedQueue<Transaction> transactionQueue) {
        this.transactionFacade = transactionFacade;
        this.transactionQueue = transactionQueue;
    }

    public List<Transaction> getInvalidTransactions() {
        List<Transaction> invalidTransactions = new ArrayList<>();

        ExecutorService service = Executors.newCachedThreadPool();
        List<Future<Transaction>> invalidTransactionsFuture = new ArrayList<>();
        while(transactionQueue.size() > 0) {
            for (int i = 0; i < THREAD_POOL_SIZE && transactionQueue.peek() != null; i++) {
                Future<Transaction> invalidTransactionFuture = service.submit(() -> validate(transactionQueue.poll()));
                invalidTransactionsFuture.add(invalidTransactionFuture);
            }
            for (Future<Transaction> transactionFuture : invalidTransactionsFuture) {
                try {
                    Transaction invalidTransaction = transactionFuture.get();
                    if (invalidTransaction != null) {
                        invalidTransactions.add(invalidTransaction);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        service.shutdown();
        return invalidTransactions;
    }


    public Transaction validate(Transaction transaction) {

        Transaction sourceTransaction = transactionFacade.getTransaction(transaction.getId());
        if (sourceTransaction == null) {
            transaction.setComment("Транзакция не найдена");
            return transaction;
        } else if (transaction.getAmount() != sourceTransaction.getAmount()) {
            transaction.setComment("Разница по сумме транзакции");
            return transaction;
        } else {
            return null;
        }
    }

}
/*

        //ExecutorService executor = new Ca
        while(transactionQueue.size() > 0) {
            Transaction tran = validate(transactionQueue.poll());
            if (tran != null) {
                invalidTransactions.add(tran);
            }
        }

 */
