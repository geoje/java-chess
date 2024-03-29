package chess.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection {
    private static final JdbcConnection INSTANCE = new JdbcConnection();

    private final Connection connection;

    private JdbcConnection() {
        try (Connection connectionWithoutDb = createNewConnectionWithoutDb()) {
            DatabaseInitializer databaseInitializer = new DatabaseInitializer();
            databaseInitializer.initialize(connectionWithoutDb);
            connection = createNewConnection();
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스에 연결할 수 없습니다.");
        }
    }

    public static Connection getConnection() {
        return INSTANCE.connection;
    }

    private static Connection createNewConnectionWithoutDb() throws SQLException {
        return DriverManager.getConnection(
                DatabaseConfiguration.getUrlWithoutDb(),
                DatabaseConfiguration.getUsername(),
                DatabaseConfiguration.getPassword());
    }

    private static Connection createNewConnection() throws SQLException {
        return DriverManager.getConnection(
                DatabaseConfiguration.getUrl(),
                DatabaseConfiguration.getUsername(),
                DatabaseConfiguration.getPassword());
    }
}
