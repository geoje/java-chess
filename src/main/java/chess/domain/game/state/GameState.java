package chess.domain.game.state;

import chess.domain.board.Board;
import chess.domain.game.command.Command;

public interface GameState {
    GameState play(final Command command, final Board board);

    boolean isEnd();
}
