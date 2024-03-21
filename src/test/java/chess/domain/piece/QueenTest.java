package chess.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.PieceColor;
import chess.domain.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("퀸")
class QueenTest {

    @ParameterizedTest
    @ValueSource(strings = {"a8", "c8", "e8", "h6", "h1", "c1", "a4", "a6"})
    @DisplayName("에 대한 이동 루트가 상하좌우 대각선 중 하나인지 판단한다.")
    void canMove(String target) {
        Queen queen = new Queen(PieceColor.BLACK);

        boolean actual = queen.canMove(Position.from("c6"), Position.from(target));

        assertThat(actual).isTrue();
    }
}