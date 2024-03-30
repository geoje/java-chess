package chess.controller;

import chess.domain.board.Board;
import chess.domain.board.BoardFactory;
import chess.domain.game.command.Command;
import chess.domain.game.command.CommandType;
import chess.domain.game.state.End;
import chess.domain.game.state.GameState;
import chess.domain.game.state.Ready;
import chess.repository.MoveDao;
import chess.view.InputView;
import chess.view.OutputView;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ChessGame {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final MoveDao moveDao = new MoveDao();
    private final Map<CommandType, BiConsumer<Command, Board>> invokers;

    public ChessGame() {
        invokers = Map.of(
                CommandType.START, this::printBoard,
                CommandType.MOVE, this::printBoard,
                CommandType.STATUS, this::printScores
        );
    }

    public void run() {
        outputView.printStartMessage();
        Board board = prepareBoard();
        GameState state = measureStateFromBoard(board);

        while (!state.isEnd()) {
            GameState currentState = state;
            Command command = requestUntilValid(() -> Command.from(inputView.readCommand()));
            state = tryGet(() -> playAndPrint(currentState, command, board)).orElse(state);
        }
    }

    private Board prepareBoard() {
        Board board = BoardFactory.createBoard();
        moveDao.findAll()
                .forEach(move -> board.move(move.source(), move.target()));
        return board;
    }

    private GameState measureStateFromBoard(Board board) {
        if (board.isKingCaptured()) {
            return new End();
        }
        return new Ready(board);
    }

    private GameState playAndPrint(GameState state, Command command, Board board) {
        final GameState newState = state.play(command);
        if (invokers.containsKey(command.type())) {
            invokers.get(command.type()).accept(command, board);
        }
        return newState;
    }

    private void printScores(Command command, Board board) {
        outputView.printScores(board.getGameStatus());
    }

    private void printBoard(Command command, Board board) {
        outputView.printBoard(board.getPiecesStatus());
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
