package chess.repository;

import chess.domain.game.Room;

import java.util.List;

public interface RoomRepository {
    List<Room> findAllById(int id);

    List<Room> findAllByUserWhite(String userWhite);

    List<Room> findAllByUserBlack(String userBlack);

    Room save(Room room);

    int updateWinnerById(int id, String winner);

    int deleteAllById(int id);
}
