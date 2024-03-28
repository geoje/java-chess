package chess.domain.piece;

import chess.domain.board.Board;
import chess.domain.square.Square;

public class Knight extends Piece {

    private static final int SIDE_STEP = 1;
    private static final int STRAIGHT_STEP = 2;

    public Knight(final PieceColor color, final Square square) {
        super(color, square);
    }

    @Override
    public void move(final Board board, final Square target) {
        validateStraightSide(target);
        validateFriendly(board, target);
        square = target;
    }

    private void validateStraightSide(final Square target) {
        if (!isStraightRankSideFile(target) && !isStraightFileSideRank(target)) {
            throw new IllegalArgumentException("나이트의 이동 방법으로 갈 수 없는 곳입니다.");
        }
    }

    private void validateFriendly(final Board board, final Square target) {
        if (board.existOnSquareWithColor(target, getColor())) {
            throw new IllegalArgumentException("나이트의 목적지에 같은 색 기물이 존재합니다.");
        }
    }

    private boolean isStraightRankSideFile(final Square target) {
        return square.calculateRankDistance(target) == STRAIGHT_STEP
                && square.calculateFileDistance(target) == SIDE_STEP;
    }

    private boolean isStraightFileSideRank(final Square target) {
        return square.calculateFileDistance(target) == STRAIGHT_STEP
                && square.calculateRankDistance(target) == SIDE_STEP;
    }

    @Override
    public PieceType getType() {
        return PieceType.KNIGHT;
    }
}
