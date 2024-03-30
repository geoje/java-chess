package chess.domain.game.room;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("방번호")
class RoomIdTest {

    @Test
    @DisplayName("문자열을 변환한다.")
    void fromTest() {
        // given
        RoomId roomId = RoomId.from("4");
        RoomId expected = new RoomId(4);

        // when & then
        assertThat(roomId).isEqualTo(expected);
    }

    @Test
    @DisplayName("숫자 형식이 아닐 경우 예외가 발생한다.")
    void validateNumeric() {
        // given & when & then
        assertThatCode(() -> RoomId.from("three"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("방 번호는 숫자 형식 이여야 합니다.");
    }
}