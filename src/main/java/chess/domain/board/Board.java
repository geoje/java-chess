package chess.domain.board;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.square.Square;
import chess.dto.PieceDrawing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {

    private final Set<Piece> pieces;
    private PieceColor turn = PieceColor.WHITE;

    public Board(final Set<Piece> pieces) {
        this.pieces = new HashSet<>(pieces);
    }

    public void move(final Square source, final Square target) {
        Piece sourcePiece = findPiece(source);
        validateTurn(sourcePiece);
        validateStay(source, target);
        sourcePiece.move(this, target);
        removeTargetPieceIfAttacked(sourcePiece, target);
        turn = turn.opposite();
    }

    private void validateTurn(final Piece sourcePiece) {
        if (sourcePiece.getColor() != turn) {
            throw new IllegalArgumentException("선택한 기물의 팀의 차례가 아닙니다.");
        }
    }

    private void validateStay(final Square source, final Square target) {
        if (source.equals(target)) {
            throw new IllegalArgumentException("제자리로 이동할 수 없습니다.");
        }
    }

    public boolean existOnSquare(final Square square) {
        return pieces.stream()
                .anyMatch(piece -> piece.isLocated(square));
    }

    public boolean existOnSquareWithColor(final Square square, final PieceColor pieceColor) {
        return pieces.stream()
                .anyMatch(piece -> piece.isLocated(square) && piece.getColor() == pieceColor);
    }

    private Piece findPiece(final Square square) {
        return pieces.stream()
                .filter(piece -> piece.isLocated(square))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다."));
    }

    private void removeTargetPieceIfAttacked(final Piece sourcePiece, final Square targetSquare) {
        pieces.removeIf(piece -> piece.isLocated(targetSquare)
                && piece.getColor() != sourcePiece.getColor());
    }

    public List<PieceDrawing> getPiecesStatus() {
        return pieces.stream()
                .map(PieceDrawing::from)
                .toList();
    }

    public int getPawnCountOnSameFile(Square square, PieceColor color) {
        return (int) pieces.stream()
                .filter(piece -> square.isSameFile(piece.getSquare()))
                .filter(piece -> color == piece.getColor())
                .count();
    }
}
