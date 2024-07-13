package com.sidh.hotelbooking.rowmapper.room.bedtype;

import com.sidh.hotelbooking.model.room.bedtype.BedType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BedTypeRowMapper implements RowMapper<BedType> {

    @Override
    public BedType mapRow(ResultSet rs, int rowNum) throws SQLException {
        return BedType.builder()
                .bedTypeId(rs.getString("a_bd_typ_id"))
                .bedTypeName(rs.getString("a_bd_typ_nm"))
                .build();
    }
}
