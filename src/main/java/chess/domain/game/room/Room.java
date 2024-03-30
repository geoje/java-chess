package chess.domain.game.room;

import chess.domain.game.User;

public record Room(RoomId id, User userWhite, User userBlack, User winner) {
    public static Room from(String userWhite, String userBlack) {
        return new Room(new RoomId(-1), new User(userWhite), new User(userBlack), new User(""));
    }

    public static Room from(int id, String userWhite, String userBlack, String winner) {
        return new Room(new RoomId(id), new User(userWhite), new User(userBlack), new User(winner));
    }
}
