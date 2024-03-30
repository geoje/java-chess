package chess.domain.game;

public record Room(int id, User userWhite, User userBlack, User winner) {
    public static Room from(String userWhite, String userBlack) {
        return new Room(-1, new User(userWhite), new User(userBlack), new User(""));
    }

    public static Room from(int id, String userWhite, String userBlack, String winner) {
        return new Room(id, new User(userWhite), new User(userBlack), new User(winner));
    }
}
