package chess.domain.board;

import chess.domain.piece.King;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.domain.square.Square;
import chess.dto.GameStatus;
import chess.dto.PieceDrawing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {

    private static final int SINGLE_KING_COUNT = 1;

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
        if (!sourcePiece.isColor(turn)) {
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
                .anyMatch(piece -> piece.isLocated(square) && piece.isColor(pieceColor));
    }

    private Piece findPiece(final Square square) {
        return pieces.stream()
                .filter(piece -> piece.isLocated(square))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다."));
    }

    private void removeTargetPieceIfAttacked(final Piece sourcePiece, final Square targetSquare) {
        pieces.removeIf(piece -> piece.isLocated(targetSquare)
                && !piece.isColor(sourcePiece.getColor()));
    }

    public int getPawnCountOnSameFile(final Square square, final PieceColor color) {
        return (int) pieces.stream()
                .filter(piece -> square.isSameFile(piece.getSquare()))
                .filter(piece -> piece.isColor(color))
                .filter(piece -> piece.getType() == PieceType.PAWN)
                .count();
    }

    public boolean isKingCaptured() {
        return pieces.stream()
                .filter(piece -> piece.getType() == PieceType.KING)
                .count() <= SINGLE_KING_COUNT;
    }

    public List<PieceDrawing> getPiecesStatus() {
        return pieces.stream()
                .map(PieceDrawing::from)
                .toList();
    }

    public GameStatus getGameStatus() {
        double whiteScore = calculateTotalScore(PieceColor.WHITE);
        double blackScore = calculateTotalScore(PieceColor.BLACK);
        return GameStatus.from(whiteScore, blackScore);
    }

    private double calculateTotalScore(final PieceColor color) {
        return pieces.stream()
                .filter(piece -> piece.isColor(color))
                .mapToDouble(piece -> piece.getScore(this))
                .sum();
    }

    public PieceColor getWinnerColor() {
        return pieces.stream()
                .filter(piece -> piece instanceof King)
                .findFirst()
                .map(Piece::getColor)
                .orElse(null);
    }
}
