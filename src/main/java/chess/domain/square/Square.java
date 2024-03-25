package chess.domain.square;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Square {
    private static final String ERROR_INVALID_PATTERN = "문자 1개 숫자 1개를 붙인 위치형식으로 입력해 주세요.";
    private static final String PATTERN = "^[a-z][0-9]$";
    private static final Map<String, Square> cache = new HashMap<>();

    private final File file;
    private final Rank rank;

    private Square(File file, Rank rank) {
        this.file = file;
        this.rank = rank;
    }

    public static Square from(String square) {
        validatePattern(square);
        return cache.computeIfAbsent(square, s -> {
            File file = File.from(s.charAt(0));
            Rank rank = Rank.from(s.charAt(1));
            return new Square(file, rank);
        });
    }

    public static Square of(File file, Rank rank) {
        String squareKey = generateSquareKey(file, rank);
        return cache.computeIfAbsent(squareKey, k -> new Square(file, rank));
    }

    private static void validatePattern(String square) {
        if (!square.matches(PATTERN)) {
            throw new IllegalArgumentException(ERROR_INVALID_PATTERN);
        }
    }

    private static String generateSquareKey(File file, Rank rank) {
        return file.name().toLowerCase() + rank.toInput();
    }

    public List<Square> generatePath(Square target) {
        List<Square> path = new ArrayList<>();
        int vectorFile = file.getVectorTo(target.file);
        int vectorRank = rank.getVectorTo(target.rank);

        Square current = add(vectorFile, vectorRank);
        while (!current.equals(target)) {
            path.add(current);
            current = current.add(vectorFile, vectorRank);
        }
        return path;
    }

    private Square add(int vectorFile, int vectorRank) {
        return new Square(file.add(vectorFile), rank.add(vectorRank));
    }

    public boolean isSameFile(Square other) {
        return file == other.file;
    }

    public boolean isSameFile(File otherFile) {
        return file == otherFile;
    }

    public boolean isSameRank(Square other) {
        return rank == other.rank;
    }

    public boolean isSameRank(Rank otherRank) {
        return rank == otherRank;
    }

    public boolean isSameDiagonal(Square other) {
        return distanceRankFrom(other) == distanceFileFrom(other);
    }

    public int distanceFileFrom(Square other) {
        return file.distanceFrom(other.file);
    }

    public int distanceRankFrom(Square other) {
        return rank.distanceFrom(other.rank);
    }

    public boolean isUpperThan(Square other) {
        return rank.compareTo(other.rank) < 0;
    }

    public boolean isLowerThan(Square other) {
        return rank.compareTo(other.rank) > 0;
    }

    public int getFileOrdinal() {
        return file.ordinal();
    }

    public int getRankOrdinal() {
        return rank.ordinal();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return file == square.file && rank == square.rank;
    }

    @Override
    public String toString() {
        return generateSquareKey(file, rank);
    }
}
