package ru.netology.web.data;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BdHelper {
    private static final QueryRunner runner = new QueryRunner();

    public static Connection getConnection() throws SQLException {
        String dbUrl = System.getProperty("db.url");
        String user = System.getProperty("db.user");
        String password = System.getProperty("db.password");
        final Connection connection = DriverManager.getConnection(dbUrl, user, password);
        return connection;
    }

    @SneakyThrows
    public String getPaymentStatus() {
        val status = "SELECT status FROM payment_entity";
        return runner.query(getConnection(), status, new ScalarHandler<>());
    }

    @SneakyThrows
    public Integer getPaymentAmount() {
        val amount = "SELECT amount FROM payment_entity";
        return runner.query(getConnection(), amount, new ScalarHandler<>());
    }

    @SneakyThrows
    public String getCreditRequestStatus() {
        val status = "SELECT status FROM credit_request_entity";
        return runner.query(getConnection(), status, new ScalarHandler<>());
    }

    @SneakyThrows
    public String getCreditId() {
        val id = "SELECT credit_id FROM order_entity";
        return runner.query(getConnection(), id, new ScalarHandler<>());
    }
}
