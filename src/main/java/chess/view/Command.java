package chess.view;

import chess.domain.Board;
import chess.domain.BoardFactory;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

public enum Command {
    START("start"),
    MOVE("move"),
    END("end");

    public static final String ERROR_INVALID_COMMAND = "존재하지 않는 명령어 입니다.";
    private final String value;


    Command(String value) {
        this.value = value;
    }

    public static Command from(String input) {
        return Arrays.stream(values())
                .filter(command -> command.value.equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(ERROR_INVALID_COMMAND));
    }
}
