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
        Command command = requestInitCommandUntilValid();
        Board board = BoardFactory.createBoard();
        if (command.isType(CommandType.START)) {
            outputView.printBoard(board.getPiecesStatus());
        }
        while (!command.isType(CommandType.END)) {
            command = requestUntilValid(this::requestPlayCommand);
            tryMove(command, board);
        }
    }

    private Command requestInitCommandUntilValid() {
        Command command;
        do {
            command = requestUntilValid(this::requestInitCommand);
        } while (!command.anyMatchType(CommandType.START, CommandType.END));
        return command;
    }

    private Command requestInitCommand() {
        Command command = requestCommand();
        if (command.isType(CommandType.MOVE)) {
            throw new IllegalArgumentException("아직 게임이 시작되지 않았습니다.");
        }
        return command;
    }

    private Command requestPlayCommand() {
        Command command = requestCommand();
        if (command.isType(CommandType.START)) {
            throw new IllegalArgumentException("이미 게임이 시작되었습니다.");
        }
        return command;
    }

    private Command requestCommand() {
        return requestUntilValid(() -> Command.from(inputView.readCommand()));
    }

    private void tryMove(final Command command, final Board board) {
        try {
            Square source = Square.from(command.getArgument(1));
            Square target = Square.from(command.getArgument(2));
            board.move(source, target);
            outputView.printBoard(board.getPiecesStatus());
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
