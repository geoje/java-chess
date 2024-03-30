package chess.domain.game.state;

import chess.domain.board.Board;
import chess.domain.game.command.Command;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("준비 상태")
class ReadyTest {

    @Test
    @DisplayName("시작 명령일 경우 진행 상태가 된다.")
    void playWithStartCommandTest() {
        // given
        final Board board = new Board(Set.of());
        GameState state = new Ready();
        Command command = Command.from(List.of("start", "a", "b"));

        // when
        GameState newState = state.play(command, board);

        // then
        assertThat(newState instanceof Progress).isTrue();
    }

    @Test
    @DisplayName("방 명령일 경우 진행 상태가 된다.")
    void playWithStartRoomTest() {
        // given
        final Board board = new Board(Set.of());
        GameState state = new Ready();
        Command command = Command.from(List.of("room"));

        // when
        GameState newState = state.play(command, board);

        // then
        assertThat(newState instanceof Ready).isTrue();
    }

    @Test
    @DisplayName("종료 명령일 경우 종료 상태가 된다.")
    void playWithEndCommandTest() {
        // given
        final Board board = new Board(Set.of());
        GameState state = new Ready();
        Command command = Command.from(List.of("end"));

        // when
        GameState newState = state.play(command, board);

        // then
        assertThat(newState instanceof End).isTrue();
    }

    @Test
    @DisplayName("움직임 명령일 경우 예외가 발생한다.")
    void exceptionOnMoveCommandTest() {
        // given
        final Board board = new Board(Set.of());
        GameState state = new Ready();
        Command command = Command.from(List.of("move", "b1", "b2"));

        // when & then
        assertThatCode(() -> state.play(command, board))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("아직 게임이 시작되지 않았습니다.");
    }

    @Test
    @DisplayName("끝난 상태가 아니다.")
    void isEndTest() {
        // given
        GameState state = new Ready();

        // when & then
        assertThat(state.isEnd()).isFalse();
    }
}