package com.sidh.hotelbooking.service.room;

import com.sidh.hotelbooking.dto.customer.MessageDto;
import com.sidh.hotelbooking.dto.room.RoomClassDto;
import com.sidh.hotelbooking.dto.room.RoomDto;
import com.sidh.hotelbooking.dto.room.RoomInsertResponseDto;

import java.util.List;

public interface RoomService {
    RoomInsertResponseDto createRoom(List<RoomDto> roomDtoList);
    MessageDto createRoomClass(List<RoomClassDto> roomClassDtoList);
}
