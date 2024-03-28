package chess.domain.board;

import chess.domain.piece.King;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.square.Square;
import chess.dto.GameStatus;
import chess.dto.PieceDrawing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

    public int getPawnCountOnSameFile(final Square square, final PieceColor color) {
        return (int) pieces.stream()
                .filter(piece -> square.isSameFile(piece.getSquare()))
                .filter(piece -> color == piece.getColor())
                .filter(piece -> piece instanceof Pawn)
                .count();
    }

    public boolean isKingCaptured() {
        return pieces.stream()
                .filter(piece -> piece instanceof King)
                .count() <= SINGLE_KING_COUNT;
    }

    public List<PieceDrawing> getPiecesStatus() {
        return pieces.stream()
                .map(PieceDrawing::from)
                .toList();
    }

    public GameStatus getGameStatus() {
        Map<String, Double> scoresByColor = calculateScores();
        List<String> winners = calculateWinners(scoresByColor);
        if (winners.size() == 1) {
            return new GameStatus(scoresByColor, winners.get(0));
        }
        return new GameStatus(scoresByColor, "");
    }

    private Map<String, Double> calculateScores() {
        Map<String, Double> scoresByColor = new HashMap<>();
        for (PieceColor color : PieceColor.values()) {
            scoresByColor.put(color.name(), calculateTotalScore(color));
        }
        return scoresByColor;
    }

    private double calculateTotalScore(final PieceColor color) {
        return pieces.stream()
                .filter(piece -> color == piece.getColor())
                .mapToDouble(piece -> piece.getScore(this))
                .sum();
    }

    private <T> List<T> calculateWinners(final Map<T, Double> scores) {
        double maxScore = calculateMaxScore(scores);
        return scores.entrySet().stream()
                .filter(entry -> entry.getValue() == maxScore)
                .map(Map.Entry::getKey)
                .toList();
    }

    private <T> double calculateMaxScore(final Map<T, Double> scores) {
        return scores.values().stream()
                .mapToDouble(v -> v)
                .max()
                .orElse(0);
    }
}
