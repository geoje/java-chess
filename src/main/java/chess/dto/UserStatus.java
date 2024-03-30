package chess.dto;

import chess.domain.game.room.Room;

import java.util.List;

public record UserStatus(int win, int lose, List<Room> inProgress) {
    public static UserStatus from(List<Room> rooms, String username) {
        final long winCount = rooms.stream().filter(room -> room.winner().name().equals(username)).count();
        final List<Room> inProgressRooms = rooms.stream().filter(room -> room.winner().name().isEmpty()).toList();
        final long inProgressCount = inProgressRooms.size();
        final long loseCount = rooms.size() - winCount - inProgressCount;
        return new UserStatus((int) winCount, (int) loseCount, inProgressRooms);
    }
}
