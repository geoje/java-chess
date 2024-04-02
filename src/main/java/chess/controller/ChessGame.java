package chess.controller;

import chess.domain.board.Board;
import chess.domain.board.BoardFactory;
import chess.domain.game.command.Command;
import chess.domain.game.command.CommandType;
import chess.domain.game.room.Room;
import chess.domain.game.room.RoomId;
import chess.domain.game.state.GameState;
import chess.domain.game.state.Ready;
import chess.domain.piece.PieceColor;
import chess.domain.square.Move;
import chess.dto.UserStatus;
import chess.repository.MoveDao;
import chess.repository.MoveRepository;
import chess.repository.RoomDao;
import chess.repository.RoomRepository;
import chess.view.InputView;
import chess.view.OutputView;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ChessGame {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final RoomRepository roomRepository = new RoomDao();
    private final MoveRepository moveRepository = new MoveDao();
    private final Map<CommandType, Consumer<Command>> invokers;

    private Room room;
    private Board board;

    public ChessGame() {
        invokers = Map.of(
                CommandType.START, this::startNewGame,
                CommandType.RESUME, this::resumeGame,
                CommandType.ROOM, this::printRoom,
                CommandType.USER, this::printUser,
                CommandType.MOVE, this::moveAndPrint,
                CommandType.STATUS, this::printScores
        );
    }

    public void run() {
        outputView.printStartMessage();
        GameState state = new Ready();

        while (!state.isEnd()) {
            GameState currentState = state;
            Command command = requestUntilValid(() -> Command.from(inputView.readCommand()));
            state = tryGet(() -> playAndPrint(currentState, command)).orElse(state);
        }
        saveAndPrintWinner();
    }

    private GameState playAndPrint(GameState state, Command command) {
        final GameState newState = state.play(command, board);
        if (invokers.containsKey(command.type())) {
            invokers.get(command.type()).accept(command);
        }
        return newState;
    }

    private void startNewGame(Command command) {
        Room roomWithoutId = Room.of(command.getArgument(1), command.getArgument(2));
        room = roomRepository.save(roomWithoutId);
        board = BoardFactory.createBoard();
        outputView.printRoom(room);
        outputView.printBoard(board.getPiecesStatus());
    }

    private void resumeGame(Command command) {
        RoomId roomId = RoomId.from(command.getArgument(1));
        room = roomRepository.findById(roomId.value());
        if (room == null) {
            throw new IllegalArgumentException("존재하지 않는 방입니다.");
        }
        board = BoardFactory.createBoard();
        List<Move> moves = moveRepository.findAllByRoomId(room.getId());
        moves.forEach(move -> board.move(move.source(), move.target()));
        outputView.printRoom(room);
        outputView.printBoard(board.getPiecesStatus());
        if (board.isKingCaptured()) {
            throw new IllegalArgumentException("이미 게임이 종료된 방 입니다.");
        }
    }

    private void printRoom(Command command) {
        final List<Room> rooms = roomRepository.findAllInProgress();
        outputView.printRooms(rooms);
    }

    private void printUser(Command command) {
        String username = command.getArgument(1);
        List<Room> rooms = roomRepository.findAllByUserWhite(username);
        if (rooms.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 유저 입니다");
        }
        rooms.addAll(roomRepository.findAllByUserBlack(username));
        UserStatus userStatus = UserStatus.from(rooms, username);
        outputView.printUser(userStatus.win(), userStatus.lose(), userStatus.inProgress());
    }

    private void moveAndPrint(Command command) {
        int roomId = room.getId();
        String source = command.getArgument(1);
        String target = command.getArgument(2);
        moveRepository.save(Move.from(roomId, source, target));
        outputView.printBoard(board.getPiecesStatus());
    }

    private void printScores(Command command) {
        outputView.printRoom(room);
        outputView.printScores(board.getGameStatus());
    }

    private void saveAndPrintWinner() {
        if (board == null || !board.isKingCaptured()) {
            return;
        }
        final String winnerUsername = getWinnerUsername();
        roomRepository.updateWinnerById(room.getId(), winnerUsername);
        outputView.printWinner(winnerUsername);
    }

    private String getWinnerUsername() {
        PieceColor winnerColor = board.getWinnerColor();
        if (winnerColor == PieceColor.WHITE) {
            return room.getUserWhiteName();
        }
        if (winnerColor == PieceColor.BLACK) {
            return room.getUserBlackName();
        }
        return "";
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
