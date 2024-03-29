package chess.db;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JDBC 연결")
class JdbcConnectionTest {

    @Test
    @DisplayName("데이터베이스에 접속한다.")
    void getConnectionTest() {
        // given
        Connection connection = JdbcConnection.getConnection();

        // when & then
        assertThat(connection).isNotNull();
    }
}
