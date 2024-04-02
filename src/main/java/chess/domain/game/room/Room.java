package chess.domain.game.room;

import chess.domain.game.User;

public record Room(RoomId id, User userWhite, User userBlack, User winner) {
    
    public static Room of(String userWhite, String userBlack) {
        return new Room(new RoomId(-1), new User(userWhite), new User(userBlack), new User(""));
    }

    public static Room of(int id, String userWhite, String userBlack, String winner) {
        return new Room(new RoomId(id), new User(userWhite), new User(userBlack), new User(winner));
    }

    public int getId() {
        return id.value();
    }

    public String getUserWhiteName() {
        return userWhite.name();
    }

    public String getUserBlackName() {
        return userBlack.name();
    }

    public String getWinnerName() {
        return winner.name();
    }
}
