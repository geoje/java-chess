package chess.controller;

import chess.domain.board.Board;
import chess.domain.board.BoardFactory;
import chess.domain.square.Square;
import chess.view.Command;
import chess.view.CommandType;
import chess.view.InputView;
import chess.view.OutputView;

import java.util.Optional;
import java.util.function.Supplier;

public class ChessGame {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public void run() {
        outputView.printStartMessage();
        Command command = requestCommandUntilStartOrEnd();
        Board board = BoardFactory.createBoard();
        outputView.printBoard(board.getStatus());

        while (!command.isType(CommandType.END)) {
            command = requestUntilValid(this::requestCommandOnProgress);
            tryMove(command, board);
        }
    }

    private Command requestCommand() {
        return requestUntilValid(() -> Command.from(inputView.readCommand()));
    }

    private Command requestCommandUntilStartOrEnd() {
        Command command;
        do {
            command = requestUntilValid(this::requestCommandOnReady);
        } while (!command.isType(CommandType.START));
        return command;
    }

    private Command requestCommandOnReady() {
        Command command = requestCommand();
        if (command.isType(CommandType.MOVE)) {
            throw new IllegalArgumentException("아직 게임이 시작되지 않았습니다.");
        }
        return command;
    }

    private Command requestCommandOnProgress() {
        Command command = requestCommand();
        if (command.isType(CommandType.START)) {
            throw new IllegalArgumentException("이미 게임이 시작되었습니다.");
        }
        return command;
    }

    private void tryMove(final Command command, final Board board) {
        try {
            Square source = Square.from(command.getArgument(1));
            Square target = Square.from(command.getArgument(2));
            board.move(source, target);
            outputView.printBoard(board.getStatus());
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
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
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return Optional.empty();
        }
    }
}
