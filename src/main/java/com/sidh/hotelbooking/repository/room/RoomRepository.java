package com.sidh.hotelbooking.repository.room;

import com.sidh.hotelbooking.model.room.BedTypeDetails;
import com.sidh.hotelbooking.model.room.Room;
import com.sidh.hotelbooking.model.room.RoomClass;
import com.sidh.hotelbooking.model.room.RoomDetails;
import com.sidh.hotelbooking.model.room.bedtype.BedType;
import com.sidh.hotelbooking.model.room.feature.Feature;

import java.util.List;

public interface RoomRepository {
    void createRoom(List<Room> rooms);
    void createRoomClass(List<RoomClass> roomClasses);
    RoomClass findRoomClassById(String roomClassId);
    RoomClass findRoomClassByClassName(String className);
    void createFeature(List<Feature> features);
    void createBedType(List<BedType> bedTypes);
    void createRoomClassFeature(String roomClassId, String featureId);
    void createRoomClassBedType(String roomClassId, String bedTypeId, Integer noOfBeds);
    Feature findFeatureByName(String featureName);
    BedType findBedTypeByName(String bedTypeName);
    RoomDetails findRoomById(String roomId);
    List<Feature> findFeatureByRoomClassId(String roomClassId);
    List<BedTypeDetails> findBedTypeRoomClassId(String roomClassId);
}
