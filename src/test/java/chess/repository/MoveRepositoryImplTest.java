package chess.repository;

import chess.domain.game.room.Room;
import chess.domain.square.Move;
import chess.domain.square.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("움직임")
class MoveRepositoryImplTest {

    @Test
    @DisplayName("모든 데이터를 가져온다.")
    void findAllByRoomId() {
        // given
        MoveRepositoryImpl moveRepositoryImpl = new MoveRepositoryImpl();

        // when & then
        assertThatCode(() -> moveRepositoryImpl.findAllByRoomId(0))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("데이터를 저장하고 삭제한다.")
    void saveAndDeleteTest() {
        // given
        RoomRepositoryImpl roomRepositoryImpl = new RoomRepositoryImpl();
        MoveRepositoryImpl moveRepositoryImpl = new MoveRepositoryImpl();

        // when & then
        Room room = roomRepositoryImpl.save(Room.of("testWhite", "testBlack"));
        Move move = new Move(room.id(), Square.from("b2"), Square.from("b4"));

        final int saveCount = moveRepositoryImpl.save(move);
        assertThat(saveCount).isGreaterThan(0);

        final int deleteCount = moveRepositoryImpl.deleteAllByRoomId(room.getId());
        assertThat(deleteCount).isGreaterThan(0);

        roomRepositoryImpl.deleteAllById(room.getId());
    }
}

