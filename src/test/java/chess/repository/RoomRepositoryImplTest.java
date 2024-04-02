package chess.repository;

import chess.domain.game.room.Room;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("방")
class RoomRepositoryImplTest {

    @Test
    @DisplayName("ID로 데이터를 가져온다.")
    void findAllByIdTest() {
        // given
        int roomId = 0;
        final RoomRepositoryImpl roomRepositoryImpl = new RoomRepositoryImpl();

        // when & then
        assertThatCode(() -> roomRepositoryImpl.findById(roomId))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("흰색 유저 이름으로 모든 데이터를 가져온다.")
    void findAllByUserWhiteTest() {
        // given
        String username = "seyang";
        final RoomRepositoryImpl roomRepositoryImpl = new RoomRepositoryImpl();

        // when & then
        assertThatCode(() -> roomRepositoryImpl.findAllByUserWhite(username))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("검은색 유저 이름으로 모든 데이터를 가져온다.")
    void findAllByUserBlackTest() {
        // given
        String username = "seyang";
        final RoomRepositoryImpl roomRepositoryImpl = new RoomRepositoryImpl();

        // when & then
        assertThatCode(() -> roomRepositoryImpl.findAllByUserBlack(username))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("데이터를 저장, 업데이트, 삭제한다.")
    void saveAndDeleteTest() {
        // given
        Room room = Room.of("whiteTester", "blackTester");
        final RoomRepositoryImpl roomRepositoryImpl = new RoomRepositoryImpl();

        // when & then
        final Room savedRoom = roomRepositoryImpl.save(room);
        assertThat(savedRoom.getId()).isGreaterThan(0);

        final int updatedCount = roomRepositoryImpl.updateWinnerById(savedRoom.getId(), "blackTester");
        assertThat(updatedCount).isGreaterThan(0);

        final int deletedCount = roomRepositoryImpl.deleteAllById(savedRoom.getId());
        assertThat(deletedCount).isGreaterThan(0);
    }
}
