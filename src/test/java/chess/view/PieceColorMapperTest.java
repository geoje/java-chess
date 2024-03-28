package chess.view;

import chess.domain.piece.PieceColor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("기물 색상명")
class PieceColorMapperTest {
    @Test
    @DisplayName("도메인의 모든 기물 색상이 변환될 수 있다.")
    void mapForAll() {
        // given & when & then
        for (var color : PieceColor.values()) {
            assertThatCode(() -> PieceColorMapper.map(color.name()))
                    .doesNotThrowAnyException();
        }
    }
}