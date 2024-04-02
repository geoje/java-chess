package chess.repository;

import chess.domain.game.room.Room;

import java.util.List;

public interface RoomRepository {
    
    Room findById(int id);

    List<Room> findAllByUserWhite(String userWhite);

    List<Room> findAllByUserBlack(String userBlack);

    List<Room> findAllInProgress();

    Room save(Room room);

    int updateWinnerById(int id, String winner);

    int deleteAllById(int id);
}
