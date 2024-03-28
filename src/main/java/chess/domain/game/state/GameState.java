package chess.domain.game.state;

import chess.domain.game.command.Command;

public interface GameState {
    GameState play(final Command command);

    boolean isEnd();
}
