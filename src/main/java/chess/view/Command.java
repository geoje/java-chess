package chess.view;

import java.util.List;

public record Command(CommandType type, List<String> arguments) {


    public Command {
        validateArgumentCount(type, arguments);
    }

    public static Command from(final List<String> command) {
        return new Command(CommandType.from(command.get(0)), command);
    }

    private void validateArgumentCount(final CommandType type, final List<String> arguments) {
        int argumentCount = type.getArgumentCount();
        if (arguments.size() - 1 != argumentCount) {
            String commandName = arguments.get(0);
            throw new IllegalArgumentException("%s 명령어는 인자가 %d개 필요합니다.".formatted(commandName, argumentCount));
        }
    }

    public boolean isType(final CommandType type) {
        return this.type == type;
    }

    public String getArgument(final int index) {
        return arguments.get(index);
    }
}
