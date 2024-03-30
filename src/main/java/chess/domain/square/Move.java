package chess.domain.square;

public record Move(int roomId, Square source, Square target) {
    public static Move from(int roomId, String source, String target) {
        return new Move(roomId, chess.domain.square.Square.from(source), chess.domain.square.Square.from(target));
    }
}
