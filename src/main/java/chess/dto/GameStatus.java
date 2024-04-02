package chess.dto;

public record GameStatus(Score whiteScore, Score blackScore) {

    public static GameStatus from(double whiteScore, double blackScore) {
        return new GameStatus(new Score(whiteScore), new Score(blackScore));
    }

    public double getWhiteScore() {
        return whiteScore.value();
    }

    public double getBlackScore() {
        return blackScore.value();
    }
}
