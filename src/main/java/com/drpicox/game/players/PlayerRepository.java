package com.drpicox.game.players;

import com.drpicox.game.rooms.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, String> {
    List<Player> getPlayersByRoom(Room room);
}
