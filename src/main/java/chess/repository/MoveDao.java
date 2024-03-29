package chess.repository;

import chess.db.JdbcConnection;
import chess.domain.square.Move;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MoveDao implements MoveRepository {
    @Override
    public List<Move> findAll() {
        final var query = "SELECT * FROM move";
        try (final var statement = JdbcConnection.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            List<Move> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(Move.from(
                        resultSet.getString("source"),
                        resultSet.getString("target")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(final Move move) {
        final var query = "INSERT INTO move (source, target) VALUES (?, ?)";
        try (final var statement = JdbcConnection.getConnection().prepareStatement(query)) {
            statement.setString(1, move.source().toInput());
            statement.setString(2, move.target().toInput());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
