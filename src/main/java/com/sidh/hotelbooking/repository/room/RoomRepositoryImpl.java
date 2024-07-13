package com.sidh.hotelbooking.repository.room;

import com.sidh.hotelbooking.model.room.BedTypeDetails;
import com.sidh.hotelbooking.model.room.RoomDetails;
import com.sidh.hotelbooking.rowmapper.room.RoomClassRowMapper;
import com.sidh.hotelbooking.model.room.Room;
import com.sidh.hotelbooking.model.room.RoomClass;
import com.sidh.hotelbooking.model.room.bedtype.BedType;
import com.sidh.hotelbooking.model.room.feature.Feature;
import com.sidh.hotelbooking.rowmapper.room.RoomDetailsRowMapper;
import com.sidh.hotelbooking.rowmapper.room.bedtype.BedTypeDetailsRowMapper;
import com.sidh.hotelbooking.rowmapper.room.bedtype.BedTypeRowMapper;
import com.sidh.hotelbooking.rowmapper.room.feature.FeatureRowMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.sidh.hotelbooking.util.RoomCmnConstants.*;
import static com.sidh.hotelbooking.util.RoomQueries.*;

@Repository
public class RoomRepositoryImpl implements RoomRepository {

    private final Logger logger = LoggerFactory.getLogger(RoomRepositoryImpl.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void createRoom(List<Room> rooms) {
        int[] count = jdbcTemplate.batchUpdate(INSERT_ROOM, getSqlParamSource(rooms));
        if (!ArrayUtils.isEmpty(count)) {
            logger.info("Merged {} rows to t_rm", count.length);
        }
    }

    @Override
    public void createRoomClass(List<RoomClass> roomClasses) {
        SqlParameterSource[] paramsSource = roomClasses.stream().map(roomClass -> {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue(ROOM_CLASS_NAME, roomClass.getRoomClassName());
            parameterSource.addValue(BASE_PRICE, roomClass.getBasePrice());
            return parameterSource;
        }).toArray(SqlParameterSource[]::new);
        int[] count = jdbcTemplate.batchUpdate(INSERT_ROOM_CLASS, paramsSource);
        if (!ArrayUtils.isEmpty(count)) {
            logger.info("Merged {} rows to t_rm_cls", count.length);
        }
    }

    @Override
    public RoomClass findRoomClassById(String roomClassId) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue(ROOM_CLASS_ID, roomClassId);
        List<RoomClass> roomClasses = jdbcTemplate.query(FIND_ROOM_CLASS_BY_ID, map, new RoomClassRowMapper());
        return !CollectionUtils.isEmpty(roomClasses) ? roomClasses.stream().findFirst().get() : null;
    }

    @Override
    public RoomClass findRoomClassByClassName(String className) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue(ROOM_CLASS_NAME, className);
        List<RoomClass> roomClasses = jdbcTemplate.query(FIND_ROOM_CLASS_BY_CLASS_NAME, map, new RoomClassRowMapper());
        return !CollectionUtils.isEmpty(roomClasses) ? roomClasses.stream().findFirst().get() : null;
    }

    @Override
    public void createFeature(List<Feature> featureList) {
        SqlParameterSource[] paramSource = featureList.stream().map(feature -> {
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue(FEATURE_ID, feature.getFeatureId());
            map.addValue(FEATURE_NAME, feature.getFeatureName());
            return map;
        }).toArray(SqlParameterSource[]::new);
        int[] count = jdbcTemplate.batchUpdate(INSERT_FEATURE, paramSource);
        if (!ArrayUtils.isEmpty(count)) {
            logger.info("Merged {} rows to t_ftre", count.length);
        }
    }

    @Override
    public void createBedType(List<BedType> bedTypeList) {
        SqlParameterSource[] paramSource = bedTypeList.stream().map(bedType -> {
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue(BED_TYPE_ID, bedType.getBedTypeId());
            map.addValue(BED_TYPE_NAME, bedType.getBedTypeName());
            return map;
        }).toArray(SqlParameterSource[]::new);
        int[] count = jdbcTemplate.batchUpdate(INSERT_BED_TYPE, paramSource);
        if (!ArrayUtils.isEmpty(count)) {
            logger.info("Merged {} rows to t_bd_typ", count.length);
        }
    }

    @Override
    public void createRoomClassFeature(String roomClassId, String featureId) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue(ROOM_CLASS_ID, roomClassId);
        map.addValue(FEATURE_ID, featureId);
        int cnt = jdbcTemplate.update(INSERT_ROOM_CLASS_FEATURE, map);
        logger.info("Inserted {} row to t_rm_cls_ftre", cnt);
    }

    @Override
    public void createRoomClassBedType(String roomClassId, String bedTypeId, Integer noOfBeds) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue(ROOM_CLASS_ID, roomClassId);
        map.addValue(BED_TYPE_ID, bedTypeId);
        map.addValue(NO_OF_BEDS, noOfBeds);
        int cnt = jdbcTemplate.update(INSERT_ROOM_CLASS_BED_TYPE, map);
        logger.info("Inserted {} row to t_rm_cls_bd_typ", cnt);
    }

    @Override
    public Feature findFeatureByName(String featureName) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue(FEATURE_NAME, featureName);
        List<Feature> result = jdbcTemplate.query(FIND_FEATURE_BY_NAME, map, new FeatureRowMapper());
        return !CollectionUtils.isEmpty(result) ? result.stream().findFirst().get() : null;
    }

    @Override
    public BedType findBedTypeByName(String bedTypeName) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue(BED_TYPE_NAME, bedTypeName);
        List<BedType> result = jdbcTemplate.query(FIND_BED_TYPE_BY_NAME, map, new BedTypeRowMapper());
        return !CollectionUtils.isEmpty(result) ? result.stream().findFirst().get() : null;
    }

    @Override
    public RoomDetails findRoomById(String roomId) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue(ROOM_ID, roomId);
        List<RoomDetails> roomDetails = jdbcTemplate.query(FIND_ROOM_BY_ID, map, new RoomDetailsRowMapper());
        return !CollectionUtils.isEmpty(roomDetails) ? roomDetails.stream().findFirst().get() : null;
    }

    @Override
    public List<Feature> findFeatureByRoomClassId(String roomClassId) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue(ROOM_CLASS_ID, roomClassId);
        return jdbcTemplate.query(FIND_FEATURE_BY_ROOM_CLASS_ID, map, new FeatureRowMapper());
    }

    @Override
    public List<BedTypeDetails> findBedTypeRoomClassId(String roomClassId) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue(ROOM_CLASS_ID, roomClassId);
        return jdbcTemplate.query(FIND_BED_TYPE_BY_ROOM_CLASS_ID, map, new BedTypeDetailsRowMapper());
    }

    public SqlParameterSource[] getSqlParamSource(List<Room> rooms) {
        return rooms.stream().map(room -> {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue(ROOM_CLASS_ID, room.getRoomClassId());
            parameterSource.addValue(FLOOR_NO, room.getFloorNo());
            parameterSource.addValue(STATUS, room.getStatus());
            parameterSource.addValue(ROOM_NO, room.getRoomNo());
            return parameterSource;
        }).toArray(SqlParameterSource[]::new);
    }
}
