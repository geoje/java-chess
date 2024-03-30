package chess.domain.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("유저")
class UserTest {

    @Test
    @DisplayName("이름이 20자가 넘으면 예외가 발생한다.")
    void validateLength() {
        // given
        String name = "a".repeat(21);

        // when & then
        assertThatCode(() -> new User(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유저 이름은 20자를 넘을 수 없습니다.");
    }
}