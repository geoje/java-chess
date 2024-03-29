package chess.db;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("데이터베이스 초기화")
class DatabaseInitializerTest {

    @Test
    @DisplayName("초기 데이터베이스를 생성한다.")
    void initializeTest() throws SQLException {
        // given
        Connection connection = DriverManager.getConnection(
                DatabaseConfiguration.getUrlWithoutDb(),
                DatabaseConfiguration.getUsername(),
                DatabaseConfiguration.getPassword());
        DatabaseInitializer initializer = new DatabaseInitializer();

        // when & then
        assertThatCode(() -> initializer.initialize(connection))
                .doesNotThrowAnyException();
    }
}
