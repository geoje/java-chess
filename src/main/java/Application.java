import chess.controller.ChessGame;
import chess.view.InputView;
import chess.view.OutputView;

public class Application {

    public static void main(final String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        ChessGame chessGame = new ChessGame(inputView, outputView);
        chessGame.run();
    }
}
