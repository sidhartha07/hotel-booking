package com.sidh.hotelbooking.service.room;

import com.sidh.hotelbooking.dto.customer.MessageDto;
import com.sidh.hotelbooking.dto.room.RoomClassDto;
import com.sidh.hotelbooking.dto.room.RoomDto;
import com.sidh.hotelbooking.dto.room.RoomInsertResponseDto;
import com.sidh.hotelbooking.exception.InvalidRequestException;
import com.sidh.hotelbooking.model.room.Room;
import com.sidh.hotelbooking.model.room.RoomClass;
import com.sidh.hotelbooking.repository.room.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.sidh.hotelbooking.dto.room.RoomStatus.AVAIL;
import static com.sidh.hotelbooking.util.MapExceptionUtil.mapToMessageDto;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    @Transactional
    public RoomInsertResponseDto createRoom(List<RoomDto> roomDtoList) {
        List<String> successList = new ArrayList<>();
        List<String> failureList = new ArrayList<>();
        List<RoomDto> roomsToBeInserted = new ArrayList<>();
        if (roomDtoList != null && !CollectionUtils.isEmpty(roomDtoList)) {
            try {
                for (RoomDto roomDto : roomDtoList) {
                    RoomClass roomClass = roomRepository.findRoomClassByClassName(roomDto.getRoomClassName());
                    if (roomClass != null) {
                        roomDto.setRoomClassName(roomClass.getRoomClassId());
                        roomsToBeInserted.add(roomDto);
                        successList.add(roomDto.getRoomNo());
                    } else {
                        failureList.add(roomDto.getRoomNo());
                    }
                }
            } catch (RuntimeException ex) {
                throw new InvalidRequestException(HttpStatus.INTERNAL_SERVER_ERROR,
                        mapToMessageDto(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage()));
            }
            try {
                roomRepository.createRoom(mapToRoomEntity(roomsToBeInserted));
            } catch (RuntimeException ex) {
                throw new InvalidRequestException(HttpStatus.INTERNAL_SERVER_ERROR,
                        mapToMessageDto(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage()));
            }
        } else {
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST,
                    mapToMessageDto(HttpStatus.BAD_REQUEST.toString(), "Invalid request."));
        }
        return RoomInsertResponseDto.builder()
                .roomsInsertedSuccess(successList)
                .roomsInsertedFailure(failureList)
                .build();
    }

    private List<Room> mapToRoomEntity(List<RoomDto> successList) {
        return successList.stream().map(roomDto -> Room.builder()
                .roomNo(roomDto.getRoomNo())
                .roomClassId(roomDto.getRoomClassName())
                .floorNo(roomDto.getFloorNo())
                .status(String.valueOf(AVAIL))
                .build()).toList();
    }

    @Override
    @Transactional
    public MessageDto createRoomClass(List<RoomClassDto> roomClassDtoList) {
        if (roomClassDtoList != null && !CollectionUtils.isEmpty(roomClassDtoList)) {
            try {
                roomRepository.createRoomClass(mapToRoomClassEntity(roomClassDtoList));
            } catch (RuntimeException ex) {
                throw new InvalidRequestException(HttpStatus.INTERNAL_SERVER_ERROR,
                        mapToMessageDto(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage()));
            }
        } else {
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST,
                    mapToMessageDto(HttpStatus.BAD_REQUEST.toString(), "Invalid request."));
        }
        return MessageDto.builder()
                .message("Room Class(s) inserted successfully")
                .build();
    }

    private List<RoomClass> mapToRoomClassEntity(List<RoomClassDto> roomClassDtoList) {
        return roomClassDtoList.stream().map(roomClassDto -> RoomClass.builder()
                .roomClassName(roomClassDto.getRoomClassName())
                .basePrice(roomClassDto.getBasePrice())
                .build()).toList();
    }
}
