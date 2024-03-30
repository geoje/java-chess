package chess.repository;

import chess.domain.game.Room;
import chess.domain.square.Move;
import chess.domain.square.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("움직임")
class MoveDaoTest {

    @Test
    @DisplayName("모든 데이터를 가져온다.")
    void findAllByRoomId() {
        // given
        MoveDao moveDao = new MoveDao();

        // when & then
        assertThatCode(() -> moveDao.findAllByRoomId(0))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("데이터를 저장하고 삭제한다.")
    void saveAndDeleteTest() {
        // given
        RoomDao roomDao = new RoomDao();
        MoveDao moveDao = new MoveDao();

        // when & then
        Room room = roomDao.save(Room.from("testWhite", "testBlack"));
        Move move = new Move(room.id(), Square.from("b2"), Square.from("b4"));

        final int saveCount = moveDao.save(move);
        assertThat(saveCount).isGreaterThan(0);

        final int deleteCount = moveDao.deleteAllByRoomId(room.id());
        assertThat(deleteCount).isGreaterThan(0);

        roomDao.deleteAllById(room.id());
    }
}
