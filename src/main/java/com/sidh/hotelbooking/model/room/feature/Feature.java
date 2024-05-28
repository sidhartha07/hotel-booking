package com.sidh.hotelbooking.model.room.feature;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Feature {
    private String featureId;
    private String featureName;
}
