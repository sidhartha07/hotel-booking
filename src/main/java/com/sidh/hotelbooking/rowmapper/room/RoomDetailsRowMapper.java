package com.sidh.hotelbooking.rowmapper.room;

import com.sidh.hotelbooking.model.room.RoomDetails;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomDetailsRowMapper implements RowMapper<RoomDetails> {
    @Override
    public RoomDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        return RoomDetails.builder()
                .roomId(rs.getString("a_rm_id"))
                .roomNo(rs.getString("a_rm_no"))
                .floorNo(rs.getString("a_flr_no"))
                .status(rs.getString("a_st"))
                .roomClassId(rs.getString("a_rm_cls_id"))
                .roomClassName(rs.getString("a_cls_nm"))
                .basePrice(rs.getString("a_base_price"))
                .build();
    }
}
