package chess.domain.piece;

import chess.domain.square.Square;

public class Knight extends Piece {

    public Knight(PieceColor color) {
        super(PieceType.KNIGHT, color);
    }

    @Override
    public boolean canMove(Square source, Square target) {
        return (source.calculateFileDiff(target.file()) == 1
                && source.calculateRankDiff(target.rank()) == 2) ||
                (source.calculateFileDiff(target.file()) == 2
                        && source.calculateRankDiff(target.rank()) == 1);
    }
}
