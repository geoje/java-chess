package chess.repository;

import chess.domain.square.Move;

import java.util.List;

public interface MoveRepository {
    List<Move> findAllByRoomId(int roomId);

    int save(Move move);

    int deleteAllByRoomId(int roomId);
}
