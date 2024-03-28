package chess.dto;

import java.util.Map;

public record GameStatus(Map<String, Double> scoresByColor, String winner) {
}
