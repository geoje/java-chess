package chess.domain.board;

import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.PieceColor;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import chess.domain.square.Square;
import chess.dto.GameStatus;
import chess.dto.PieceDrawing;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("보드")
class BoardTest {

    @Test
    @DisplayName("빈 스퀘어로 기물을 정상적으로 움직일 수 있다.")
    void moveToEmptySquareTest() {
        // given
        Square source = Square.from("b3");
        Square target = Square.from("b4");
        Rook piece = new Rook(PieceColor.WHITE, source);
        Board board = new Board(Set.of(piece));

        // when
        board.move(source, target);

        // then
        assertThat(board.existOnSquare(target)).isTrue();
    }

    @Test
    @DisplayName("차례가 아닌 팀의 기물을 움직일 경우 예외가 발생한다.")
    void validateTurnTest() {
        // given
        Square source = Square.from("b3");
        Square target = Square.from("b4");
        Rook piece = new Rook(PieceColor.BLACK, source);
        Board board = new Board(Set.of(piece));

        // when & then
        assertThatCode(() -> board.move(source, target))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("선택한 기물의 팀의 차례가 아닙니다.");

    }

    @Test
    @DisplayName("제자리로 이동할 경우 예외가 발생한다.")
    void validateStayTest() {
        // given
        Square source = Square.from("b3");
        Square target = Square.from("b3");
        Rook piece = new Rook(PieceColor.WHITE, source);
        Board board = new Board(Set.of(piece));

        // when & then
        assertThatCode(() -> board.move(source, target))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("제자리로 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("출발지 스퀘어에 기물이 존재하지 않을 경우 예외가 발생한다.")
    void exceptionOnMoveWhenNoPieceOnSourceSquareTest() {
        // given
        Board board = new Board(Set.of());
        Square source = Square.from("b3");
        Square target = Square.from("b4");

        // when & then
        assertThatCode(() -> board.move(source, target))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치에 기물이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("해당 위치에 기물이 존재하는지 않는지 확인한다.")
    void notExistOnSquareTest() {
        // given
        Square source = Square.from("b3");
        Board board = new Board(Set.of());

        // when
        boolean exist = board.existOnSquare(source);

        // then
        assertThat(exist).isFalse();
    }

    @Test
    @DisplayName("해당 위치에 기물이 존재하는지 확인한다.")
    void existOnSquareTest() {
        // given
        Square source = Square.from("b3");
        Rook piece = new Rook(PieceColor.BLACK, source);
        Board board = new Board(Set.of(piece));

        // when
        boolean exist = board.existOnSquare(source);

        // then
        assertThat(exist).isTrue();
    }

    @Test
    @DisplayName("해당 위치가 비어 있을 때 검은색 기물이 존재하지 않은지 확인한다.")
    void notExistBlackOnEmptySquareTest() {
        // given
        Square source = Square.from("b3");
        Board board = new Board(Set.of());

        // when
        boolean exist = board.existOnSquareWithColor(source, PieceColor.BLACK);

        // then
        assertThat(exist).isFalse();
    }

    @Test
    @DisplayName("해당 위치에 흰색 기물이 있을 때 검은색 기물이 존재하지 않은지 확인한다.")
    void notExistBlackOnSquareHaveWhiteTest() {
        // given
        Square source = Square.from("b3");
        Board board = new Board(Set.of(new Rook(PieceColor.WHITE, source)));

        // when
        boolean exist = board.existOnSquareWithColor(source, PieceColor.BLACK);

        // then
        assertThat(exist).isFalse();
    }

    @Test
    @DisplayName("해당 위치에 검은색 기물이 있을 때 검은색 기물이 존재하는지 확인한다.")
    void existBlackOnSquareTest() {
        // given
        Square source = Square.from("b3");
        Board board = new Board(Set.of(new Rook(PieceColor.BLACK, source)));

        // when
        boolean exist = board.existOnSquareWithColor(source, PieceColor.BLACK);

        // then
        assertThat(exist).isTrue();
    }

    @Test
    @DisplayName("목적지에 적 기물이 존재하면 해당 기물을 제거한다.")
    void removeTargetPieceIfAttackedTest() {
        // given
        Square source = Square.from("b3");
        Square target = Square.from("b4");
        Rook piece = new Rook(PieceColor.WHITE, source);
        Rook enemy = new Rook(PieceColor.BLACK, target);
        Board board = new Board(Set.of(piece, enemy));

        // when
        board.move(source, target);
        boolean targetColor = board.existOnSquareWithColor(target, piece.getColor());

        // then
        assertThat(targetColor).isTrue();
    }

    @Test
    @DisplayName("현재 존재하는 기물들에 대해 그리는 데이터를 생성한다.")
    void generatePieceDrawingsTest() {
        // given
        Square source = Square.from("b3");
        Square target = Square.from("b4");
        Rook piece = new Rook(PieceColor.BLACK, source);
        Knight enemy = new Knight(PieceColor.WHITE, target);
        Board board = new Board(Set.of(piece, enemy));
        List<PieceDrawing> expected = List.of(
                new PieceDrawing(1, 5, "BLACK", "ROOK"),
                new PieceDrawing(1, 4, "WHITE", "KNIGHT")
        );

        // when
        List<PieceDrawing> pieceDrawings = board.getPiecesStatus();

        // then
        assertThat(pieceDrawings).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("특정한 파일에 검은색 폰이 2개 존재 한다.")
    void getBlackPawnCountOnSameFile() {
        // given
        Pawn blackPawn = new Pawn(PieceColor.BLACK, Square.from("b2"));
        Board board = new Board(Set.of(
                blackPawn,
                new Pawn(PieceColor.BLACK, Square.from("b4")),
                new Pawn(PieceColor.WHITE, Square.from("e3")),
                new Pawn(PieceColor.WHITE, Square.from("e5"))
        ));

        // when
        int pawnCount = board.getPawnCountOnSameFile(blackPawn.getSquare(), blackPawn.getColor());

        // then
        assertThat(pawnCount).isEqualTo(2);
    }

    @Test
    @DisplayName("특정한 파일에 흰색 폰이 1개 존재 한다.")
    void getWhitePawnCountOnSameFile() {
        // given
        Pawn whitePawn = new Pawn(PieceColor.WHITE, Square.from("e3"));
        Board board = new Board(Set.of(
                new Pawn(PieceColor.BLACK, Square.from("b2")),
                new Pawn(PieceColor.BLACK, Square.from("b4")),
                new Pawn(PieceColor.WHITE, Square.from("d3")),
                whitePawn
        ));

        // when
        int pawnCount = board.getPawnCountOnSameFile(whitePawn.getSquare(), whitePawn.getColor());

        // then
        assertThat(pawnCount).isEqualTo(1);
    }

    @Test
    @DisplayName("초기 진영은 점수가 같아 승자가 존재 하지 않는다.")
    void getGameStatusSameTest() {
        // given
        Board board = BoardFactory.createBoard();

        // when
        GameStatus gameStatus = board.getGameStatus();

        // then
        assertThat(gameStatus).isEqualTo(GameStatus.from(38, 38));
    }

    @Test
    @DisplayName("현재 게임의 점수와 승자 상태를 계산 한다.")
    void getGameStatusDiffTest() {
        // given
        Board board = new Board(Set.of(
                new Queen(PieceColor.BLACK, Square.from("a1")),
                new Rook(PieceColor.BLACK, Square.from("a2")),
                new Pawn(PieceColor.BLACK, Square.from("b2")),
                new Pawn(PieceColor.BLACK, Square.from("b4")),
                new Queen(PieceColor.WHITE, Square.from("f1")),
                new Pawn(PieceColor.WHITE, Square.from("e3")),
                new Pawn(PieceColor.WHITE, Square.from("f5"))
        ));

        // when
        GameStatus gameStatus = board.getGameStatus();

        // then
        assertThat(gameStatus).isEqualTo(GameStatus.from(11, 15));
    }

    @Test
    @DisplayName("승자를 판별한다.")
    void getWinnerColor() {
        // given
        Board board = new Board(Set.of(
                new King(PieceColor.BLACK, Square.from("a1"))
        ));

        // when
        PieceColor pieceColor = board.getWinnerColor();

        // then
        assertThat(pieceColor).isEqualTo(PieceColor.BLACK);
    }
}
