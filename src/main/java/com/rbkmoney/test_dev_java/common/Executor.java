package com.rbkmoney.test_dev_java.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Класс получает пул потоков
 *
 *  Примечание: если задач больше чем одна, то уже нужно создавать фабрику пулов под каждую задачу, но в текущий
 *              момент одной достаточно
 *  P.S.: Сейчас executor получается непосредственно в конфигурационном файле. Данный файл остался в качестве задела для
 *        фабрики или т.п.
 * */
public class Executor {

    private static final ExecutorService SERVICE = Executors.newCachedThreadPool();

    public static ExecutorService getExecutorService() {
        return SERVICE;
    }

    private Executor() {}

}
