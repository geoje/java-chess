package chess.repository;

import chess.db.JdbcConnection;
import chess.domain.square.Move;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MoveDao implements MoveRepository {

    @Override
    public List<Move> findAllByRoomId(int roomId) {
        final var query = "SELECT * FROM move WHERE room_id = ?";
        try (final var statement = JdbcConnection.getConnection().prepareStatement(query)) {
            statement.setInt(1, roomId);
            ResultSet resultSet = statement.executeQuery();
            List<Move> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(Move.from(
                        resultSet.getInt("room_id"),
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
    public int save(final Move move) {
        final var query = "INSERT INTO move (room_id, source, target) VALUES (?, ?, ?)";
        try (final var statement = JdbcConnection.getConnection().prepareStatement(query)) {
            statement.setInt(1, move.getRoomId());
            statement.setString(2, move.getSourceKey());
            statement.setString(3, move.getTargetKey());
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteAllByRoomId(int roomId) {
        final var query = "DELETE FROM move WHERE room_id = ?";
        try (final var statement = JdbcConnection.getConnection().prepareStatement(query)) {
            statement.setInt(1, roomId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
