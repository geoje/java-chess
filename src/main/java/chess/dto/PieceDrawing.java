package chess.dto;

import chess.domain.piece.Piece;
import chess.domain.square.Square;

public record PieceDrawing(int fileIndex, int rankIndex, String colorName, String typeName) {

    public static PieceDrawing from(final Piece piece) {
        Square square = piece.getSquare();
        int fileIndex = square.getFileIndex();
        int rankIndex = square.getRankIndex();
        String colorName = piece.getColor().name();
        String typeName = piece.getType().name();
        return new PieceDrawing(fileIndex, rankIndex, colorName, typeName);
    }
}
