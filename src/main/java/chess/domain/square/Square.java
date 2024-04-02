package chess.domain.square;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Square {

    private static final String FILE_AND_RANK_PATTERN = "^[a-z][0-9]$";
    private static final Map<String, Square> CACHE = new HashMap<>();

    private final File file;
    private final Rank rank;

    private Square(final File file, final Rank rank) {
        this.file = file;
        this.rank = rank;
    }

    public static Square from(final String square) {
        validatePattern(square);
        return CACHE.computeIfAbsent(square, s -> {
            File file = File.from(s.charAt(0));
            Rank rank = Rank.from(s.charAt(1));
            return new Square(file, rank);
        });
    }

    public static Square of(final File file, final Rank rank) {
        String squareKey = generateKey(file, rank);
        return CACHE.computeIfAbsent(squareKey, k -> new Square(file, rank));
    }

    private static void validatePattern(final String square) {
        if (!square.matches(FILE_AND_RANK_PATTERN)) {
            throw new IllegalArgumentException("문자 1개 숫자 1개를 붙인 위치형식으로 입력해 주세요.");
        }
    }

    private static String generateKey(final File file, final Rank rank) {
        return String.valueOf(file.convertToKey()) + rank.convertToKey();
    }

    public List<Square> generatePath(final Square target) {
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

    private Square add(final int vectorFile, final int vectorRank) {
        return new Square(file.add(vectorFile), rank.add(vectorRank));
    }

    public boolean isSameFile(final Square other) {
        return file == other.file;
    }

    public boolean isSameFile(final File file) {
        return this.file == file;
    }

    public boolean isSameRank(final Square other) {
        return rank == other.rank;
    }

    public boolean isSameRank(final Rank rank) {
        return this.rank == rank;
    }

    public boolean isSameDiagonal(final Square other) {
        return calculateRankDistance(other) == calculateFileDistance(other);
    }

    public int calculateFileDistance(final Square other) {
        return file.distanceFrom(other.file);
    }

    public int calculateRankDistance(final Square other) {
        return rank.distanceFrom(other.rank);
    }

    public boolean isUpperThan(final Square other) {
        return rank.compareTo(other.rank) < 0;
    }

    public boolean isLowerThan(final Square other) {
        return rank.compareTo(other.rank) > 0;
    }

    public int getFileIndex() {
        return file.getIndex();
    }

    public int getRankIndex() {
        return rank.getIndex();
    }

    public String convertToKey() {
        return generateKey(file, rank);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return file == square.file && rank == square.rank;
    }

    @Override
    public String toString() {
        return "Square{" +
                "file=" + file +
                ", rank=" + rank +
                '}';
    }
}
