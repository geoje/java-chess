package chess.repository;

import chess.domain.square.Move;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("움직임")
class MoveDaoTest {

    @Test
    @DisplayName("모든 데이터를 가져온다.")
    void findAll() {
        // given
        MoveDao moveDao = new MoveDao();

        // when
        List<Move> list = moveDao.findAll();

        // then
        System.out.println("list = " + list);
    }
}
