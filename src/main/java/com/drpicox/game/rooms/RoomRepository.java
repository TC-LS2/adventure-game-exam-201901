package com.drpicox.game.rooms;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, RoomCoordinates> {
}
