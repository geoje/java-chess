package chess.domain.game.state;

import chess.domain.game.command.Command;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("종료 상태")
class EndTest {
    @ParameterizedTest
    @ValueSource(strings = {"start", "move b1 b2", "status"})
    @DisplayName("모든 명령에 대해 예외가 발생한다.")
    void exceptionOnPlayTest(String InputCommand) {
        // given
        GameState state = new End();
        Command command = Command.from(Arrays.stream(InputCommand.split(" ")).toList());

        // when & then
        assertThatCode(() -> state.play(command))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("게임이 종료 되었습니다.");
    }

    @Test
    @DisplayName("끝난 상태 이다.")
    void inEndTest() {
        // given
        GameState state = new End();

        // when & then
        assertThat(state.isEnd()).isTrue();
    }
}