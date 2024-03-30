package chess.domain.game.state;

import chess.domain.board.Board;
import chess.domain.game.command.Command;
import chess.domain.game.command.CommandType;
import chess.domain.square.Move;
import chess.domain.square.Square;
import chess.repository.MoveDao;

public class Progress implements GameState {

    private static final int ARGUMENT_INDEX_SOURCE = 1;
    private static final int ARGUMENT_INDEX_TARGET = 2;
    private final Board board;
    private final MoveDao moveDao;

    public Progress(final Board board) {
        this.board = board;
        this.moveDao = new MoveDao();
    }

    @Override
    public GameState play(final Command command) {
        if (command.isType(CommandType.START)) {
            throw new UnsupportedOperationException("이미 게임이 시작되었습니다.");
        }
        if (command.isType(CommandType.MOVE)) {
            processMove(command);
        }
        if (command.isType(CommandType.END) || board.isKingCaptured()) {
            return new End();
        }
        return this;
    }

    private void processMove(final Command command) {
        String sourceArgument = command.getArgument(ARGUMENT_INDEX_SOURCE);
        String targetArgument = command.getArgument(ARGUMENT_INDEX_TARGET);
        Square source = Square.from(sourceArgument);
        Square target = Square.from(targetArgument);

        board.move(source, target);
        saveMoveToDb(source, target);
    }

    private void saveMoveToDb(Square source, Square target) {
        moveDao.save(new Move(0, source, target));
    }

    @Override
    public boolean isEnd() {
        return false;
    }
}
