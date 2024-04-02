package chess.view;

import chess.domain.piece.PieceColor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    @DisplayName("빈 색상일 경우 없음을 반환한다.")
    void mapToEmpty() {
        // given
        String mapped = PieceColorMapper.map("");

        // when & then
        assertThat(mapped).isEqualTo("없음");
    }
}
