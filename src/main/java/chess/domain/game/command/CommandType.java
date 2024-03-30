package chess.domain.game.command;

import java.util.Arrays;

public enum CommandType {

    START("start", 0),
    RESUME("resume", 1),
    ROOM("room", 0),
    USER("user", 1),
    MOVE("move", 2),
    STATUS("status", 0),
    END("end", 0);

    private final String value;
    private final int argumentCount;

    CommandType(final String value, final int argumentCount) {
        this.value = value;
        this.argumentCount = argumentCount;
    }

    public static CommandType from(final String input) {
        return Arrays.stream(values())
                .filter(commandType -> commandType.value.equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(input + " 은(는) 존재하지 않는 명령어 입니다."));
    }

    public int getArgumentCount() {
        return argumentCount;
    }
}
