package chess.domain.game.state;

import chess.domain.board.Board;
import chess.domain.game.command.Command;
import chess.domain.piece.King;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.square.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("진행 상태")
class ProgressTest {

    @Test
    @DisplayName("킹이 둘 다 살아 있으면 진행 상태로 유지 된다.")
    void playAndKeepStateTest() {
        // given
        Piece blackKing = new King(PieceColor.BLACK, Square.from("b1"));
        Piece whiteKing = new King(PieceColor.WHITE, Square.from("b2"));
        final Board board = new Board(Set.of(blackKing, whiteKing));
        GameState state = new Progress();
        Command command = Command.from(List.of("move", "b2", "b3"));

        // when
        GameState newState = state.play(command, board);

        // then
        assertThat(newState instanceof Progress).isTrue();
    }

    @Test
    @DisplayName("킹이 이 죽으면 종료 상태로 변환 된다.")
    void playAndEndStateTest() {
        // given
        Piece blackKing = new King(PieceColor.BLACK, Square.from("b1"));
        Piece whiteKing = new King(PieceColor.WHITE, Square.from("b2"));
        final Board board = new Board(Set.of(blackKing, whiteKing));
        GameState state = new Progress();
        Command command = Command.from(List.of("move", "b2", "b1"));

        // when
        GameState newState = state.play(command, board);

        // then
        assertThat(newState instanceof End).isTrue();
    }

    @Test
    @DisplayName("시작 명령일 경우 예외가 발생한다.")
    void exceptionOnStartCommandTest() {
        // given
        final Board board = new Board(Set.of());
        GameState state = new Progress();
        Command command = Command.from(List.of("start", "a", "b"));

        // when &
        assertThatCode(() -> state.play(command, board))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("이미 게임이 시작되었습니다.");
    }

    @Test
    @DisplayName("끝난 상태가 아니다.")
    void isEndTest() {
        // given
        GameState state = new Progress();

        // when & then
        assertThat(state.isEnd()).isFalse();
    }
}