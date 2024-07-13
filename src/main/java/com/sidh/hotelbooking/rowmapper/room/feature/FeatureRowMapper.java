package com.sidh.hotelbooking.rowmapper.room.feature;

import com.sidh.hotelbooking.model.room.feature.Feature;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FeatureRowMapper implements RowMapper<Feature> {
    @Override
    public Feature mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Feature.builder()
                .featureId(rs.getString("a_ftre_id"))
                .featureName(rs.getString("a_ftre_nm"))
                .build();
    }
}
