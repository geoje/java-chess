package chess.domain.game.room;

public record RoomId(int value) {

    private static final String NUMERIC_PATTERN = "-?\\d+";

    public static RoomId from(String value) {
        validateNumeric(value);
        return new RoomId(Integer.parseInt(value));
    }

    private static void validateNumeric(String value) {
        if (!value.matches(NUMERIC_PATTERN)) {
            throw new IllegalArgumentException("방 번호는 숫자 형식 이여야 합니다.");
        }
    }
}
