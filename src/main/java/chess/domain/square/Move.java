package chess.domain.square;

public record Move(Square source, Square target) {
    public static Move from(String source, String target) {
        return new Move(Square.from(source), Square.from(target));
    }
}
