package chess.domain.square;

import chess.domain.game.room.RoomId;

public record Move(RoomId roomId, Square source, Square target) {

    public static Move from(int roomId, String source, String target) {
        return new Move(new RoomId(roomId), Square.from(source), Square.from(target));
    }

    public int getRoomId() {
        return roomId.value();
    }

    public String getSourceKey() {
        return source.convertToKey();
    }

    public String getTargetKey() {
        return target.convertToKey();
    }
}
