package chess.domain.square;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("이동")
class MoveTest {

    @Test
    @DisplayName("문자열을 통해 생성한다.")
    void fromTest() {
        // given
        final Move move = Move.from(0, "b2", "b4");
        Move expected = new Move(0,
                Square.of(File.B, Rank.TWO),
                Square.of(File.B, Rank.FOUR));

        // when & then
        assertThat(move).isEqualTo(expected);
    }
}
