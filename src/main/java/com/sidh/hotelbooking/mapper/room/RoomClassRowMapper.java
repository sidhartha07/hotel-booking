package com.sidh.hotelbooking.mapper.room;

import com.sidh.hotelbooking.model.room.RoomClass;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomClassRowMapper implements RowMapper<RoomClass> {
    @Override
    public RoomClass mapRow(ResultSet rs, int rowNum) throws SQLException {
        return RoomClass.builder()
                .roomClassId(rs.getString("a_rm_cls_id"))
                .roomClassName(rs.getString("a_cls_nm"))
                .basePrice(rs.getDouble("a_base_price"))
                .build();
    }
}
