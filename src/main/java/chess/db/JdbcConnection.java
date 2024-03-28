package chess.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection {
    private static final JdbcConnection INSTANCE = new JdbcConnection();

    private final Connection connection;

    private JdbcConnection() {
        try {
            connection = DriverManager.getConnection(
                    DatabaseConfiguration.getUrl(),
                    DatabaseConfiguration.getUsername(),
                    DatabaseConfiguration.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스에 연결할 수 없습니다.");
        }
    }

    public static Connection getConnection() {
        return INSTANCE.connection;
    }
}
