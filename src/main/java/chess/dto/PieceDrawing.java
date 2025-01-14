package chess.dto;

import chess.domain.piece.Piece;
import chess.domain.square.Square;

public record PieceDrawing(int fileIndex, int rankIndex, String colorName, String typeName) {

    public static PieceDrawing from(final Piece piece) {
        Square square = piece.getSquare();
        int fileIndex = square.getFileIndex();
        int rankIndex = square.getRankIndex();
        String colorName = piece.getColorName();
        String typeName = piece.getTypeName();
        return new PieceDrawing(fileIndex, rankIndex, colorName, typeName);
    }
}
