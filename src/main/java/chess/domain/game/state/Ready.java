package chess.domain.game.state;

import chess.domain.board.Board;
import chess.domain.game.command.Command;
import chess.domain.game.command.CommandType;

public class Ready implements GameState {

    private final Board board;

    public Ready(final Board board) {
        this.board = board;
    }

    @Override
    public GameState play(final Command command) {
        if (command.isType(CommandType.START)) {
            return new Progress(board);
        }
        if (command.isType(CommandType.END)) {
            return new End();
        }
        throw new UnsupportedOperationException("아직 게임이 시작되지 않았습니다.");
    }

    @Override
    public boolean isEnd() {
        return false;
    }
}
