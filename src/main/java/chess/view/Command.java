package chess.view;

import java.util.Arrays;

public enum Command {
    START("start"),
    MOVE("move"),
    END("end");

    private static final String ERROR_INVALID_COMMAND = " 은(는) 존재하지 않는 명령어 입니다.";
    private final String value;


    Command(String value) {
        this.value = value;
    }

    public static Command from(String input) {
        return Arrays.stream(values())
                .filter(command -> command.value.equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(input + ERROR_INVALID_COMMAND));
    }
}
