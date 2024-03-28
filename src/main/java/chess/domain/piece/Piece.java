package chess.domain.piece;

import chess.domain.board.Board;
import chess.domain.square.Square;

public abstract class Piece {

    protected final PieceColor color;
    protected Square square;

    public Piece(final PieceColor color, final Square square) {
        this.color = color;
        this.square = square;
    }

    public abstract void move(final Board board, final Square target);

    public abstract PieceType getType();

    public abstract double getScore(Board board);

    public boolean isLocated(final Square other) {
        return square.equals(other);
    }

    public PieceColor getColor() {
        return color;
    }

    public Square getSquare() {
        return square;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return color == piece.color && square.equals(piece.square);
    }

    @Override
    public int hashCode() {
        int result = color != null ? color.hashCode() : 0;
        result = 31 * result + (square != null ? square.hashCode() : 0);
        return result;
    }
}
