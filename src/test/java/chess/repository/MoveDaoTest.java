package chess.repository;

import chess.domain.square.Move;
import chess.domain.square.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("움직임")
class MoveDaoTest {

    @Test
    @DisplayName("모든 데이터를 가져온다.")
    void findAllTest() {
        // given
        MoveDao moveDao = new MoveDao();

        // when
        List<Move> list = moveDao.findAll();

        // then
        System.out.println("list = " + list);
    }

    @Test
    @DisplayName("데이터를 저장한다.")
    void saveTest() {
        // given
        Move move = new Move(Square.from("b2"), Square.from("b4"));
        MoveDao moveDao = new MoveDao();
        int size = moveDao.findAll().size();

        // when
        moveDao.save(move);
        int newSize = moveDao.findAll().size();

        // then
        assertThat(newSize).isEqualTo(size + 1);
    }
}
