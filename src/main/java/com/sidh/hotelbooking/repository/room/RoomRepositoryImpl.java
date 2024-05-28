package com.sidh.hotelbooking.repository.room;

import com.sidh.hotelbooking.mapper.room.RoomClassRowMapper;
import com.sidh.hotelbooking.model.room.Room;
import com.sidh.hotelbooking.model.room.RoomClass;
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
