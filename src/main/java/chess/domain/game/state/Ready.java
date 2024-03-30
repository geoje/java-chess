package chess.domain.game.state;

import chess.domain.board.Board;
import chess.domain.game.command.Command;
import chess.domain.game.command.CommandType;

public class Ready implements GameState {

    @Override
    public GameState play(final Command command, final Board board) {
        if (command.anyMatchType(CommandType.START, CommandType.RESUME)) {
            return new Progress();
        }
        if (command.anyMatchType(CommandType.MOVE, CommandType.STATUS)) {
            throw new UnsupportedOperationException("아직 게임이 시작되지 않았습니다.");
        }
        if (command.isType(CommandType.END)) {
            return new End();
        }
        return this;
    }

    @Override
    public boolean isEnd() {
        return false;
    }
}
