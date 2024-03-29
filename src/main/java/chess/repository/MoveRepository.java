package chess.repository;

import chess.domain.square.Move;

import java.util.List;

public interface MoveRepository {
    List<Move> findAll();

    void save(Move move);
}
