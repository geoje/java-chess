package chess.view;

import chess.domain.game.room.Room;
import chess.dto.GameStatus;
import chess.dto.PieceDrawing;

import java.util.Arrays;
import java.util.List;

public class OutputView {
    private static final int BOARD_SIZE = 8;
    private static final char EMPTY_PIECE = '.';
    private static final String TITLE_START = """
            +-------------------------------------------------------+
            | 우아한 체스 게임\t\t\t\t\t\t\t\t\t\t|
            +-------------------------------------------------------+
            | 게임 시작\t\t| start [white유저이름] [black유저이름]\t|
            | 게임 재개\t\t| resume [방번호]\t\t\t\t\t\t|
            | 방 리스트\t\t| room\t\t\t\t\t\t\t\t\t|
            | 유저 정보\t\t| user [유저이름]\t\t\t\t\t\t|
            | 이동\t\t\t| move [source위치] [target위치]\t\t\t|
            | 승패 및 점수\t| status\t\t\t\t\t\t\t\t|
            | 게임 종료\t\t| end\t\t\t\t\t\t\t\t\t|
            +-------------------------------------------------------+
                        
            """;
    private static final String ROOM_STATUS_FORMAT = "방 번호: %d / 흰색 유저: %s / 검은색 유저: %s%n";
    private static final String TITLE_WINNER = "승자: ";
    private static final String FORMAT_COLOR_SCORE = "%s: %.1f%n";
    private static final String NO_ROOM = "방이 존재하지 않습니다.%n%n";
    private static final String ERROR_MESSAGE_FORMAT = "[ERROR] %s%n%n";

    public void printStartMessage() {
        System.out.printf(TITLE_START);
    }

    public void printRooms(List<Room> rooms) {
        if (rooms.isEmpty()) {
            System.out.printf(NO_ROOM);
            return;
        }
        rooms.forEach(this::printRoom);
        System.out.println();
    }

    public void printRoom(Room room) {
        System.out.printf(ROOM_STATUS_FORMAT,
                room.id().value(),
                room.userWhite().name(),
                room.userBlack().name());
    }

    public void printBoard(final List<PieceDrawing> pieceDrawings) {
        char[][] board = generateEmptyBoard();
        setPiecesOnBoard(board, pieceDrawings);
        for (char[] line : board) {
            System.out.println(line);
        }
        System.out.println();
    }

    private char[][] generateEmptyBoard() {
        char[][] emptyBoard = new char[BOARD_SIZE][BOARD_SIZE];
        for (char[] line : emptyBoard) {
            Arrays.fill(line, EMPTY_PIECE);
        }
        return emptyBoard;
    }

    private void setPiecesOnBoard(final char[][] board, final List<PieceDrawing> pieceDrawings) {
        for (PieceDrawing pieceDrawing : pieceDrawings) {
            char pieceSymbol = PieceMapper.map(pieceDrawing.typeName(), pieceDrawing.colorName());
            board[pieceDrawing.rankIndex()][pieceDrawing.fileIndex()] = pieceSymbol;
        }
    }

    public void printScores(GameStatus gameStatus) {
        gameStatus.scoresByColor().forEach(this::printScore);
        System.out.println(TITLE_WINNER + PieceColorMapper.map(gameStatus.winner()));
        System.out.println();
    }

    private void printScore(String color, double score) {
        String outputColor = PieceColorMapper.map(color);
        System.out.printf(FORMAT_COLOR_SCORE, outputColor, score);
    }

    public void printErrorMessage(String message) {
        System.out.printf(ERROR_MESSAGE_FORMAT, message);
    }

}
