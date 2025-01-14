package chess.domain.piece;

import chess.domain.board.Board;
import chess.domain.square.Rank;
import chess.domain.square.Square;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    private static final int FIRST_STEP_LIMIT = 2;
    private static final int STEP_LIMIT = 1;
    private static final Rank FIRST_RANK_BLACK = Rank.SEVEN;
    private static final Rank FIRST_RANK_WHITE = Rank.TWO;
    private static final String MESSAGE_CANNOT_REACH = "폰의 이동 방법으로 갈 수 없는 곳입니다.";
    private static final double NORMAL_SCORE = 1;
    private static final double SPECIAL_SCORE = 0.5;
    private static final int PAWN_COUNT_FOR_SPECIAL = 2;

    public Pawn(final PieceColor color, final Square square) {
        super(color, square);
    }

    @Override
    public void move(final Board board, final Square target) {
        if (isForwardDiagonal(target) && existEnemyOnTarget(board, target)) {
            validateAttackStepLimit(target);
            square = target;
            return;
        }
        validateMove(board, target);
        square = target;
    }

    private void validateAttackStepLimit(final Square target) {
        if (square.calculateRankDistance(target) > STEP_LIMIT) {
            throw new IllegalArgumentException(MESSAGE_CANNOT_REACH);
        }
    }

    private void validateMove(final Board board, final Square target) {
        validateMoveDirection(target);
        validateMoveStepLimit(target);
        validateMoveObstacle(board, target);
    }

    private void validateMoveDirection(final Square target) {
        if (!isForward(target) || !square.isSameFile(target)) {
            throw new IllegalArgumentException(MESSAGE_CANNOT_REACH);
        }
    }

    private void validateMoveStepLimit(final Square target) {
        if ((!isFirstStep() && square.calculateRankDistance(target) > STEP_LIMIT) ||
                square.calculateRankDistance(target) > FIRST_STEP_LIMIT) {
            throw new IllegalArgumentException(MESSAGE_CANNOT_REACH);
        }
    }

    private void validateMoveObstacle(final Board board, final Square target) {
        List<Square> path = new ArrayList<>(square.generatePath(target));
        path.add(target);
        if (path.stream().anyMatch(board::existOnSquare)) {
            throw new IllegalArgumentException("폰의 이동 경로 중 장애물이 존재합니다.");
        }
    }

    private boolean isForward(final Square target) {
        if (color == PieceColor.BLACK) {
            return square.isUpperThan(target);
        }
        return square.isLowerThan(target);
    }

    private boolean isFirstStep() {
        if (color == PieceColor.BLACK) {
            return square.isSameRank(FIRST_RANK_BLACK);
        }
        return square.isSameRank(FIRST_RANK_WHITE);
    }

    private boolean isForwardDiagonal(Square target) {
        return isForward(target) && square.isSameDiagonal(target);
    }

    private boolean existEnemyOnTarget(Board board, Square target) {
        return board.existOnSquareWithColor(target, color.opposite());
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }

    @Override
    public double getScore(Board board) {
        if (board.getPawnCountOnSameFile(square, color) >= PAWN_COUNT_FOR_SPECIAL) {
            return SPECIAL_SCORE;
        }
        return NORMAL_SCORE;
    }
}
