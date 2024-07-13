package com.sidh.hotelbooking.service.room;

import com.sidh.hotelbooking.dto.customer.MessageDto;
import com.sidh.hotelbooking.dto.room.*;
import com.sidh.hotelbooking.dto.room.bedtype.RoomClassBedTypeDto;
import com.sidh.hotelbooking.exception.InvalidRequestException;
import com.sidh.hotelbooking.model.room.BedTypeDetails;
import com.sidh.hotelbooking.model.room.Room;
import com.sidh.hotelbooking.model.room.RoomClass;
import com.sidh.hotelbooking.model.room.RoomDetails;
import com.sidh.hotelbooking.model.room.bedtype.BedType;
import com.sidh.hotelbooking.model.room.feature.Feature;
import com.sidh.hotelbooking.repository.room.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.sidh.hotelbooking.dto.room.RoomStatus.AVAIL;
import static com.sidh.hotelbooking.util.MapExceptionUtil.mapToMessageDto;

@Service
public class RoomServiceImpl implements RoomService {

    private static Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);

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

    @Override
    @Transactional
    public void createRoomWithRoomClassAndFeature(RoomInsertRequestDto roomInsertDto, RoomInsertResponseDto roomResponseDto, List<Room> roomList) {
        String roomClassName = roomInsertDto.getRoomClassName();
        String roomNo = roomInsertDto.getRoomNo();
        try {
            RoomClass roomClass = roomRepository.findRoomClassByClassName(roomClassName);
            if (roomClass == null) {
                throw new Exception("RoomClass Not found");
            } else {
                String roomClassId = roomClass.getRoomClassId();
                List<String> featureNames = roomInsertDto.getFeatures();
                List<Feature> featureList = featureNames.stream().map(featureName -> {
                    String featureId;
                    Feature feature = roomRepository.findFeatureByName(featureName);
                    if (feature != null) {
                        featureId = feature.getFeatureId();
                    } else {
                        featureId = UUID.randomUUID().toString();
                    }
                    return Feature.builder()
                            .featureId(featureId)
                            .featureName(featureName)
                            .build();
                }).toList();
                for (Feature feature : featureList) {
                    roomRepository.createRoomClassFeature(roomClassId, feature.getFeatureId());
                }
                List<RoomInsertRequestDto.BedType> bedTypes = roomInsertDto.getBedTypes();
                List<RoomClassBedTypeDto> roomClassBedTypeList = bedTypes.stream().map(bedType -> {
                    String bedTypeId;
                    BedType bedTyp = roomRepository.findBedTypeByName(bedType.getType());
                    if (bedTyp != null) {
                        bedTypeId = bedTyp.getBedTypeId();
                    } else {
                        bedTypeId = UUID.randomUUID().toString();
                    }
                    return RoomClassBedTypeDto.builder()
                            .bedTypeId(bedTypeId)
                            .bedTypeName(bedType.getType())
                            .noOfBeds(bedType.getNoOfBeds())
                            .build();
                }).toList();
                for (RoomClassBedTypeDto roomClassBedTypeDto : roomClassBedTypeList) {
                    roomRepository.createRoomClassBedType(roomClassId, roomClassBedTypeDto.getBedTypeId(), roomClassBedTypeDto.getNoOfBeds());
                }
                List<BedType> bedTypeList = roomClassBedTypeList.stream().map(bedType -> BedType.builder()
                        .bedTypeId(bedType.getBedTypeId())
                        .bedTypeName(bedType.getBedTypeName())
                        .build()).toList();
                roomRepository.createFeature(featureList);
                roomRepository.createBedType(bedTypeList);
                Room room = Room.builder()
                        .roomNo(roomNo)
                        .roomClassId(roomClassId)
                        .floorNo(roomInsertDto.getFloorNo())
                        .status(String.valueOf(AVAIL))
                        .build();
                roomList.add(room);
                roomResponseDto.getRoomsInsertedSuccess().add(roomNo);
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new InvalidRequestException(HttpStatus.INTERNAL_SERVER_ERROR,
                    mapToMessageDto(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage()));
        } catch (Exception ex) {
            logger.warn("RoomClass {} not found. Ignoring room no {}.", roomClassName, roomNo);
            roomResponseDto.getRoomsInsertedFailure().add(roomNo);
        }
    }

    @Override
    public RoomDetailsDto findRoomByRoomId(String roomId) {
        List<Feature> features;
        List<BedTypeDetails> bedTypes;
        RoomDetails roomDetails = roomRepository.findRoomById(roomId);
        if (roomDetails != null) {
            String roomClassId = roomDetails.getRoomClassId();
            features = roomRepository.findFeatureByRoomClassId(roomClassId);
            bedTypes = roomRepository.findBedTypeRoomClassId(roomClassId);
        } else {
            throw new InvalidRequestException(HttpStatus.NOT_FOUND,
                    mapToMessageDto(HttpStatus.NOT_FOUND.toString(), "Room details not found for this roomId."));
        }
        return RoomDetailsDto.builder()
                .roomId(roomId)
                .roomNo(roomDetails.getRoomNo())
                .floorNo(roomDetails.getFloorNo())
                .status(roomDetails.getStatus())
                .roomClassName(roomDetails.getRoomClassName())
                .basePrice(roomDetails.getBasePrice())
                .features(features.stream().map(Feature::getFeatureName).toList())
                .bedTypes(bedTypes.stream().map(bedTypeDetails -> RoomInsertRequestDto.BedType.builder()
                                .type(bedTypeDetails.getBedTypeName())
                                .noOfBeds(bedTypeDetails.getNoOfBeds())
                                .build())
                        .toList())
                .build();
    }

    private List<RoomClass> mapToRoomClassEntity(List<RoomClassDto> roomClassDtoList) {
        return roomClassDtoList.stream().map(roomClassDto -> RoomClass.builder()
                .roomClassName(roomClassDto.getRoomClassName())
                .basePrice(roomClassDto.getBasePrice())
                .build()).toList();
    }
}
