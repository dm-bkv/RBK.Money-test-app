package com.rbkmoney.test_dev_java.consumers.DAO;

import com.rbkmoney.test_dev_java.common.DataSource;
import com.rbkmoney.test_dev_java.common.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/** Класс, который отвечает за непосредственное взаимодействие с БД через JDBC */
public class TransactionsJdbcDAO extends TransactionsDAO {

    /** Логгер */
    private static final Logger LOG = LoggerFactory.getLogger(TransactionsJdbcDAO.class);

    public TransactionsJdbcDAO(DataSource dataSource) throws Exception {
        super(dataSource);
    }

    /** Запрос на получение транзакции из БД по ID */
    private static final String GET_TRANSACTION = "SELECT amount, data FROM rbk.transactions WHERE id = ? ";

    /**
     * Получение данных транзакции из БД по ID
     *
     * Примечание: здесь есть два подхода - либо загружать все данные в объект и по сути обеспечивать целостность
     *             бизнес логики получения транзакции, либо загружать то, что нужно тем самым уменьшая нагрузку на
     *             БД и сеть. Я выбрал второе, так как считаю что система все таки высоконагруженная
     * P.S.: в качестве решения, наверное, стоит создать что-то типа ShortTransaction и грузить туда усеченные данные,
     *       но в рамках данной задачи это не так критично
     *
     * @param id идентиикатор транзакции
     * @return возвращает данные по транзакции, либо null
     */
    public Transaction getTransaction(Integer id) throws SQLException {
        LOG.debug("Получение данных транзакции {}", id);
        try(Connection conn = getDataSource().getConnection();
            PreparedStatement stmt = conn.prepareStatement(GET_TRANSACTION)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                Transaction tran = new Transaction();
                if (rs.next()) {
                    tran.setId(id);
                    tran.setAmount(rs.getFloat("amount"));
                    //tran.setData(rs.getString("data"));
                    return tran;
                } else {
                    return null;
                }
            }
        }
    }
}
