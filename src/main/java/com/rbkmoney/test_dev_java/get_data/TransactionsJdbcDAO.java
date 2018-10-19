package com.rbkmoney.test_dev_java.get_data;

import java.sql.*;

/** Класс, который отвечает за непосредственное взаимодействие с БД через JDBC */
public class TransactionsJdbcDAO extends TransactionsDAO {

    public TransactionsJdbcDAO(DataSource dataSource) {
        super(dataSource);
    }

    /** Запрос на получение транзакции из БД по ID */
    private static final String GET_TRANSACTION = "SELECT amount, data FROM rbk.transactions WHERE id = ? ";

    public Transaction getTransaction(Integer id) throws SQLException {
        LOG.debug("Получение данных транзакции {}", id);
        try(Connection conn = getDataSource().getConnection();
            PreparedStatement stmt = conn.prepareStatement(GET_TRANSACTION)) {
            stmt.setInt(0, id);
            try (ResultSet rs = stmt.executeQuery()) {
                Transaction tran = new Transaction();
                if (rs.next()) {
                    tran.setId(id);
                    tran.setAmount(rs.getFloat("amount"));
                    tran.setData(rs.getString("data"));
                    return tran;
                } else {
                    return null;
                }
            }
        }
    }
}
