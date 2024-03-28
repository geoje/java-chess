package chess.domain.game.state;

import chess.domain.game.command.Command;

public class End implements GameState {

    @Override
    public GameState play(Command command) {
        return null;
    }

    @Override
    public boolean isEnd() {
        return true;
    }
}
