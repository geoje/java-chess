package chess.view;

import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("기물 출력명")
class PieceMapperTest {

    @Test
    @DisplayName("검은색 기물일 때 대문자로 변환한다.")
    void mapToUpperCaseWhenBlackPieceTest() {
        Character actual = PieceMapper.map("KNIGHT", "BLACK");
        assertThat(actual).isEqualTo('N');
    }

    @Test
    @DisplayName("흰색 기물일 때 소문자로 변환한다.")
    void mapToUpperCaseWhenWhitePieceTest() {
        Character actual = PieceMapper.map("KING", "WHITE");
        assertThat(actual).isEqualTo('k');
    }

    @Test
    @DisplayName("도메인의 모든 기물 타입이 변환될 수 있다.")
    void mapForAll() {
        // given
        String colorName = PieceColor.WHITE.name();

        // when & then
        for (var type : PieceType.values()) {
            assertThatCode(() -> PieceMapper.map(type.name(), colorName))
                    .doesNotThrowAnyException();
        }
    }
}
