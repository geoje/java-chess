package chess.domain.game;

public record User(String name) {
    public User {
        validateLength(name);
    }

    private void validateLength(String name) {
        if (name.length() > 20) {
            throw new IllegalArgumentException("유저 이름의 길이는 20자를 넘을 수 없습니다.");
        }
    }
}
