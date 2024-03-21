package chess.domain.board;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.square.Square;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    static final String ERROR_NOT_EXIST_PIECE = "해당 위치에 기물이 존재하지 않습니다.";
    static final String ERROR_MOVE_NOT_AVAILABLE = "해당 위치로 기물을 이동할 수 없습니다.";
    private final Map<Square, Piece> values;

    public Board(Map<Square, Piece> values) {
        this.values = new HashMap<>(values);
    }

    public void move(final Square source, final Square target) {
        Piece sourcePiece = values.get(source);

        if (isNotExistPiece(source)) {
            throw new IllegalArgumentException(ERROR_NOT_EXIST_PIECE);
        }

        if (!sourcePiece.canMove(source, target)) {
            throw new IllegalArgumentException(ERROR_MOVE_NOT_AVAILABLE);
        }

        if (sourcePiece.getType() != PieceType.KNIGHT && existObstacleOnPath(source, target)) {
            throw new IllegalArgumentException(ERROR_MOVE_NOT_AVAILABLE);
        }

        values.remove(source);
        values.put(target, sourcePiece);
    }

    private boolean existObstacleOnPath(final Square source, final Square target) {
        List<Square> path = source.generatePath(target);
        return path.stream().anyMatch(values::containsKey);
    }

    public boolean isNotExistPiece(Square square) {
        return !values.containsKey(square);
    }

    public Map<Square, Piece> get() {
        return values;
    }
}