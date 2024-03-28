package chess.domain.game.state;

import chess.domain.game.command.Command;

public interface GameState {
    GameState play(Command command);

    boolean isEnd();
}
