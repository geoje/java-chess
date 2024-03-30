package chess.repository;

import chess.domain.square.Move;

import java.util.List;

public interface MoveRepository {
    List<Move> findAllByRoomId(int roomId);

    void save(Move move);
}
