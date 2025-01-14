package chess.repository;

import chess.db.JdbcConnection;
import chess.domain.game.room.Room;
import chess.domain.game.room.RoomId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomRepositoryImpl implements RoomRepository {

    @Override
    public Optional<Room> findById(int id) {
        final var query = "SELECT * FROM room WHERE id = ?";
        try (final var statement = JdbcConnection.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return getFirstRoomFromResult(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<Room> getFirstRoomFromResult(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return Optional.of(Room.of(
                    resultSet.getInt("id"),
                    resultSet.getString("user_white"),
                    resultSet.getString("user_black"),
                    resultSet.getString("winner")
            ));
        }
        return Optional.empty();
    }

    @Override
    public List<Room> findAllByUserWhite(String userWhite) {
        final var query = "SELECT * FROM room WHERE user_white = ?";
        try (final var statement = JdbcConnection.getConnection().prepareStatement(query)) {
            statement.setString(1, userWhite);
            ResultSet resultSet = statement.executeQuery();
            List<Room> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(Room.of(
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
                list.add(Room.of(
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
    public List<Room> findAllInProgress() {
        final var query = "SELECT * FROM room WHERE winner = ''";
        try (final var statement = JdbcConnection.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            List<Room> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(Room.of(
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
            statement.setString(1, room.getUserWhiteName());
            statement.setString(2, room.getUserBlackName());
            statement.executeUpdate();
            final ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return new Room(new RoomId(generatedKeys.getInt(1)), room.userWhite(), room.userBlack(), room.winner());
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
