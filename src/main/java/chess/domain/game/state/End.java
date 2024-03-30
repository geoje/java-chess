package chess.domain.game.state;

import chess.domain.board.Board;
import chess.domain.game.command.Command;

public class End implements GameState {

    @Override
    public GameState play(final Command command, final Board board) {
        throw new UnsupportedOperationException("게임이 종료 되었습니다.");
    }

    @Override
    public boolean isEnd() {
        return true;
    }
}
