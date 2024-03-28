package chess.domain.board;

import chess.domain.piece.Bishop;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class BoardFactory {

    private static final List<BiFunction<PieceColor, Square, Piece>> PIECES_ARRANGEMENT = List.of(
            Rook::new, Knight::new, Bishop::new, Queen::new,
            King::new, Bishop::new, Knight::new, Rook::new);

    private BoardFactory() {
    }

    public static Board createBoard() {
        Set<Piece> board = new HashSet<>();

        board.addAll(createPiecesWithoutPawn(Rank.EIGHT, PieceColor.BLACK));
        board.addAll(createPawns(Rank.SEVEN, PieceColor.BLACK));
        board.addAll(createPawns(Rank.TWO, PieceColor.WHITE));
        board.addAll(createPiecesWithoutPawn(Rank.ONE, PieceColor.WHITE));

        return new Board(board);
    }

    private static Set<Piece> createPiecesWithoutPawn(final Rank rank, final PieceColor pieceColor) {
        return Arrays.stream(File.values())
                .map(file -> createPiece(rank, pieceColor, file))
                .collect(Collectors.toSet());
    }

    private static Piece createPiece(Rank rank, PieceColor pieceColor, File file) {
        Square square = Square.of(file, rank);
        return PIECES_ARRANGEMENT.get(file.getIndex()).apply(pieceColor, square);
    }

    private static Set<Piece> createPawns(final Rank rank, final PieceColor pieceColor) {
        return Arrays.stream(File.values())
                .map(file -> new Pawn(pieceColor, Square.of(file, rank)))
                .collect(Collectors.toSet());
    }
}
