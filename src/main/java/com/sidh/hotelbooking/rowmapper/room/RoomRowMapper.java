package com.sidh.hotelbooking.rowmapper.room;

import com.sidh.hotelbooking.model.room.Room;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomRowMapper implements RowMapper<Room> {
    @Override
    public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Room.builder()
                .roomId(rs.getString("a_rm_id"))
                .roomClassId(rs.getString("a_rm_cls_id"))
                .floorNo(rs.getString("a_flr_no"))
                .status(rs.getString("a_st"))
                .roomNo(rs.getString("a_rm_no"))
                .build();
    }
}
