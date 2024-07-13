package com.sidh.hotelbooking.service.room.handler;

import com.sidh.hotelbooking.dto.room.RoomInsertRequestDto;
import com.sidh.hotelbooking.dto.room.RoomInsertResponseDto;
import com.sidh.hotelbooking.model.room.Room;
import com.sidh.hotelbooking.repository.room.RoomRepository;
import com.sidh.hotelbooking.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceHandler {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;

    public RoomInsertResponseDto createRoomWithAll(List<RoomInsertRequestDto> roomDtoList) {
        RoomInsertResponseDto roomInsertResponseDto = RoomInsertResponseDto.builder()
                .roomsInsertedSuccess(new ArrayList<>())
                .roomsInsertedFailure(new ArrayList<>())
                .build();
        List<Room> roomsList = new ArrayList<>();
        if (roomDtoList != null && !CollectionUtils.isEmpty(roomDtoList)) {
            for (RoomInsertRequestDto roomInsertRequestDto : roomDtoList) {
                roomService.createRoomWithRoomClassAndFeature(roomInsertRequestDto, roomInsertResponseDto, roomsList);
            }
        }
        roomRepository.createRoom(roomsList);
        return roomInsertResponseDto;
    }
}
