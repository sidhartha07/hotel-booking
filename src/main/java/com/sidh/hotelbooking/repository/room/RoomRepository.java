package com.sidh.hotelbooking.repository.room;

import com.sidh.hotelbooking.model.room.Room;
import com.sidh.hotelbooking.model.room.RoomClass;

import java.util.List;

public interface RoomRepository {
    void createRoom(List<Room> rooms);
    void createRoomClass(List<RoomClass> roomClasses);
    RoomClass findRoomClassById(String roomClassId);
    RoomClass findRoomClassByClassName(String className);
}
