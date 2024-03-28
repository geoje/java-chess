package chess.controller;

import chess.domain.board.Board;
import chess.domain.board.BoardFactory;
import chess.domain.game.command.Command;
import chess.domain.game.command.CommandType;
import chess.domain.game.state.GameState;
import chess.domain.game.state.Ready;
import chess.view.InputView;
import chess.view.OutputView;

import java.util.Optional;
import java.util.function.Supplier;

public class ChessGame {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public void run() {
        outputView.printStartMessage();
        Board board = BoardFactory.createBoard();

        GameState state = new Ready(board);
        while (!state.isEnd()) {
            GameState currentState = state;
            Command command = requestUntilValid(() -> Command.from(inputView.readCommand()));
            state = tryGet(() -> playAndPrint(currentState, command, board)).orElse(state);
        }
    }

    private GameState playAndPrint(GameState state, Command command, Board board) {
        final GameState newState = state.play(command);
        if (command.isType(CommandType.STATUS)) {
            outputView.printScores(board.getGameStatus());
        }
        if (command.anyMatchType(CommandType.START, CommandType.MOVE)) {
            outputView.printBoard(board.getPiecesStatus());
        }
        return newState;
    }

    private <T> T requestUntilValid(final Supplier<T> supplier) {
        Optional<T> result = Optional.empty();
        while (result.isEmpty()) {
            result = tryGet(supplier);
        }
        return result.get();
    }

    private <T> Optional<T> tryGet(final Supplier<T> supplier) {
        try {
            return Optional.of(supplier.get());
        } catch (IllegalArgumentException | UnsupportedOperationException e) {
            outputView.printErrorMessage(e.getMessage());
            return Optional.empty();
        }
    }
}
