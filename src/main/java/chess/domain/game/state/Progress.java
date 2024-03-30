package chess.domain.game.state;

import chess.domain.board.Board;
import chess.domain.game.command.Command;
import chess.domain.game.command.CommandType;
import chess.domain.square.Square;

public class Progress implements GameState {

    private static final int ARGUMENT_INDEX_SOURCE = 1;
    private static final int ARGUMENT_INDEX_TARGET = 2;

    @Override
    public GameState play(final Command command, final Board board) {
        if (command.anyMatchType(CommandType.START, CommandType.RESUME)) {
            throw new UnsupportedOperationException("이미 게임이 시작되었습니다.");
        }
        if (command.isType(CommandType.MOVE)) {
            processMove(command, board);
        }
        if (command.isType(CommandType.END) || board.isKingCaptured()) {
            return new End();
        }
        return this;
    }

    private void processMove(final Command command, final Board board) {
        String sourceArgument = command.getArgument(ARGUMENT_INDEX_SOURCE);
        String targetArgument = command.getArgument(ARGUMENT_INDEX_TARGET);
        Square source = Square.from(sourceArgument);
        Square target = Square.from(targetArgument);

        board.move(source, target);
    }

    @Override
    public boolean isEnd() {
        return false;
    }
}
