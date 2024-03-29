package chess.repository;

import chess.db.JdbcConnection;
import chess.domain.square.Move;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MoveDao implements MoveRepository {
    @Override
    public List<Move> findAll() {
        final var query = "SELECT * FROM move";
        try (final Statement statement = JdbcConnection.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            List<Move> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(Move.from(
                        resultSet.getString("source"),
                        resultSet.getString("target")
                ));
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void save() {

    }
}
