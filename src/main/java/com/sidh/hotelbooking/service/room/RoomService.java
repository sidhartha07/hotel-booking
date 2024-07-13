package com.sidh.hotelbooking.service.room;

import com.sidh.hotelbooking.dto.customer.MessageDto;
import com.sidh.hotelbooking.dto.room.*;
import com.sidh.hotelbooking.model.room.Room;

import java.util.List;

public interface RoomService {
    RoomInsertResponseDto createRoom(List<RoomDto> roomDtoList);
    MessageDto createRoomClass(List<RoomClassDto> roomClassDtoList);
    void createRoomWithRoomClassAndFeature(RoomInsertRequestDto roomInsertDto, RoomInsertResponseDto roomResponseDto, List<Room> roomList);
    RoomDetailsDto findRoomByRoomId(String roomId);
}
