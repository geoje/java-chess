package chess.db;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String CHESS_DDL_FILENAME = "chess.sql";

    public void initialize() {
        try (Statement statement = JdbcConnection.getConnection().createStatement()) {
            for (String query : getDdlQueries()) {
                statement.execute(query);
            }
        } catch (SQLException | URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String[] getDdlQueries() throws URISyntaxException, IOException {
        URL resource = this.getClass().getClassLoader().getResource(CHESS_DDL_FILENAME);
        if (resource == null) {
            throw new RuntimeException("체스 DDL 파일이 존재하지 않습니다.");
        }
        String queries = Files.readString(Paths.get(resource.toURI()));
        return queries.split(";");
    }
}
