package chess.domain.square;

public record Move(int roomId, Square source, Square target) {

    public static Move from(int roomId, String source, String target) {
        return new Move(roomId, Square.from(source), Square.from(target));
    }
}
