package chess.dto;

public record Score(double value) {
    public Score {
        validatePositive(value);
    }

    private void validatePositive(double value) {
        if (value < 0) {
            throw new IllegalArgumentException("점수는 음수가 될 수 없습니다.");
        }
    }
}
