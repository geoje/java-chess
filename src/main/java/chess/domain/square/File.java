package chess.domain.square;

public enum File {

    A(0),
    B(1),
    C(2),
    D(3),
    E(4),
    F(5),
    G(6),
    H(7);

    private static final char FIRST_INPUT = 'a';

    private final int index;

    File(int index) {
        this.index = index;
    }

    public static File from(final char input) {
        int index = input - FIRST_INPUT;
        validateRange(index);
        return values()[index];
    }

    private static void validateRange(final int value) {
        if (value < 0 || value >= values().length) {
            throw new IllegalArgumentException("범위 밖의 파일 입니다.");
        }
    }

    public File add(final int value) {
        int sum = index + value;
        validateRange(sum);
        return values()[sum];
    }

    public int getVectorTo(final File other) {
        return (int) Math.signum(other.compareTo(this));
    }

    public int distanceFrom(final File other) {
        return Math.abs(compareTo(other));
    }

    public char convertToKey() {
        return (char) (FIRST_INPUT + index);
    }

    public int getIndex() {
        return index;
    }
}
