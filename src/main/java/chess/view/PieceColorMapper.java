package chess.view;

public enum PieceColorMapper {
    WHITE("흰색"),
    BLACK("검은색");

    private final String name;

    PieceColorMapper(String name) {
        this.name = name;
    }

    public static String map(String colorName) {
        if (colorName.isBlank()) {
            return "";
        }
        return valueOf(colorName).name;
    }
}
