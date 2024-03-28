package chess.domain.piece;

import chess.domain.board.Board;
import chess.domain.square.Square;

import java.util.List;

public class Queen extends Piece {

    private static final double SCORE = 9;

    public Queen(final PieceColor color, final Square square) {
        super(color, square);
    }

    @Override
    public void move(final Board board, final Square target) {
        validateDirection(target);
        validateFriendly(board, target);
        validateObstacle(board, target);
        square = target;
    }

    private void validateDirection(final Square target) {
        if (!isSameFileOrRankOrDiagonal(target)) {
            throw new IllegalArgumentException("퀸의 이동 방법으로 갈 수 없는 곳입니다.");
        }
    }

    private void validateFriendly(final Board board, final Square target) {
        if (board.existOnSquareWithColor(target, getColor())) {
            throw new IllegalArgumentException("퀸의 목적지에 같은 색 기물이 존재합니다.");
        }
    }

    private void validateObstacle(final Board board, final Square target) {
        List<Square> path = square.generatePath(target);
        if (path.stream().anyMatch(board::existOnSquare)) {
            throw new IllegalArgumentException("퀸의 이동 경로 중 장애물이 존재합니다.");
        }
    }

    private boolean isSameFileOrRankOrDiagonal(final Square target) {
        return square.isSameFile(target) ||
                square.isSameRank(target) ||
                square.isSameDiagonal(target);
    }

    @Override
    public PieceType getType() {
        return PieceType.QUEEN;
    }

    @Override
    public double getScore(Board board) {
        return SCORE;
    }
}
