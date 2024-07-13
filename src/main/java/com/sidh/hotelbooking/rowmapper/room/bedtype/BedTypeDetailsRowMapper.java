package com.sidh.hotelbooking.rowmapper.room.bedtype;

import com.sidh.hotelbooking.model.room.BedTypeDetails;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BedTypeDetailsRowMapper implements RowMapper<BedTypeDetails> {
    @Override
    public BedTypeDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        return BedTypeDetails.builder()
                .bedTypeName(rs.getString("a_bd_typ_nm"))
                .noOfBeds(rs.getInt("a_no_beds"))
                .build();
    }
}
