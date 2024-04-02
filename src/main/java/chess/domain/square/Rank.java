package chess.domain.square;

public enum Rank {

    EIGHT(0),
    SEVEN(1),
    SIX(2),
    FIVE(3),
    FOUR(4),
    THREE(5),
    TWO(6),
    ONE(7);

    private static final char FIRST_INPUT = '8';

    private final int index;

    Rank(int index) {
        this.index = index;
    }

    public static Rank from(final char input) {
        int index = FIRST_INPUT - input;
        validateRange(index);
        return values()[index];
    }

    private static void validateRange(final int value) {
        if (value < 0 || value >= values().length) {
            throw new IllegalArgumentException("범위 밖의 랭크 입니다.");
        }
    }

    public Rank add(final int value) {
        int sum = index + value;
        validateRange(sum);
        return values()[sum];
    }

    public int getVectorTo(final Rank other) {
        return (int) Math.signum(other.compareTo(this));
    }

    public int distanceFrom(final Rank other) {
        return Math.abs(compareTo(other));
    }

    public char toInput() {
        return (char) (FIRST_INPUT - index);
    }

    public int getIndex() {
        return index;
    }
}
