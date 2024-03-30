package chess.repository;

import chess.domain.game.room.Room;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("방")
class RoomDaoTest {

    @Test
    @DisplayName("ID로 모든 데이터를 가져온다.")
    void findAllByIdTest() {
        // given
        int roomId = 0;
        final RoomDao roomDao = new RoomDao();

        // when & then
        assertThatCode(() -> roomDao.findAllById(roomId))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("흰색 유저 이름으로 모든 데이터를 가져온다.")
    void findAllByUserWhiteTest() {
        // given
        String username = "seyang";
        final RoomDao roomDao = new RoomDao();

        // when & then
        assertThatCode(() -> roomDao.findAllByUserWhite(username))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("검은색 유저 이름으로 모든 데이터를 가져온다.")
    void findAllByUserBlackTest() {
        // given
        String username = "seyang";
        final RoomDao roomDao = new RoomDao();

        // when & then
        assertThatCode(() -> roomDao.findAllByUserBlack(username))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("데이터를 저장, 업데이트, 삭제한다.")
    void saveAndDeleteTest() {
        // given
        Room room = Room.from("whiteTester", "blackTester");
        final RoomDao roomDao = new RoomDao();

        // when & then
        final Room savedRoom = roomDao.save(room);
        assertThat(savedRoom.id()).isGreaterThan(0);

        final int updatedCount = roomDao.updateWinnerById(savedRoom.id(), "blackTester");
        assertThat(updatedCount).isGreaterThan(0);

        final int deletedCount = roomDao.deleteAllById(savedRoom.id());
        assertThat(deletedCount).isGreaterThan(0);
    }
}