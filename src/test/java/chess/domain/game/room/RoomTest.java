package chess.domain.game.room;

import chess.domain.game.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("방")
class RoomTest {

    @Test
    @DisplayName("흰색 검은색 유저 이름으로만 변환한다.")
    void fromTest() {
        // given
        Room room = Room.of("whiteTester", "blackTester");
        Room expected = new Room(new RoomId(-1), new User("whiteTester"), new User("blackTester"), new User(""));

        // when & then
        assertThat(room).isEqualTo(expected);
    }

    @Test
    @DisplayName("흰색 검은색 유저 이름으로만 변환한다.")
    void fromAllTest() {
        // given
        Room room = Room.of(-1, "whiteTester", "blackTester", "");
        Room expected = new Room(new RoomId(-1), new User("whiteTester"), new User("blackTester"), new User(""));

        // when & then
        assertThat(room).isEqualTo(expected);
    }
}
