package chess.repository;

import chess.db.JdbcConnection;
import chess.domain.game.Room;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoomDao implements RoomRepository {

    @Override
    public List<Room> findAllById(int id) {
        final var query = "SELECT * FROM room WHERE id = ?";
        try (final var statement = JdbcConnection.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Room> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(Room.from(
                        resultSet.getInt("id"),
                        resultSet.getString("user_white"),
                        resultSet.getString("user_black"),
                        resultSet.getString("winner")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Room> findAllByUserWhite(String userWhite) {
        final var query = "SELECT * FROM room WHERE user_white = ?";
        try (final var statement = JdbcConnection.getConnection().prepareStatement(query)) {
            statement.setString(1, userWhite);
            ResultSet resultSet = statement.executeQuery();
            List<Room> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(Room.from(
                        resultSet.getInt("id"),
                        resultSet.getString("user_white"),
                        resultSet.getString("user_black"),
                        resultSet.getString("winner")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Room> findAllByUserBlack(String userBlack) {
        final var query = "SELECT * FROM room WHERE user_black = ?";
        try (final var statement = JdbcConnection.getConnection().prepareStatement(query)) {
            statement.setString(1, userBlack);
            ResultSet resultSet = statement.executeQuery();
            List<Room> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(Room.from(
                        resultSet.getInt("id"),
                        resultSet.getString("user_white"),
                        resultSet.getString("user_black"),
                        resultSet.getString("winner")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Room save(Room room) {
        final var query = "INSERT INTO room (user_white, user_black) VALUES (?, ?)";
        try (final var statement = JdbcConnection.getConnection()
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, room.userWhite().name());
            statement.setString(2, room.userBlack().name());
            statement.executeUpdate();
            final ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return new Room(generatedKeys.getInt(1), room.userWhite(), room.userBlack(), room.winner());
            }
            return room;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateWinnerById(int id, String winner) {
        final var query = "UPDATE room SET winner = ? WHERE id = ?";
        try (final var statement = JdbcConnection.getConnection()
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, winner);
            statement.setInt(2, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteAllById(int id) {
        final var query = "DELETE FROM room WHERE id = ?";
        try (final var statement = JdbcConnection.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
