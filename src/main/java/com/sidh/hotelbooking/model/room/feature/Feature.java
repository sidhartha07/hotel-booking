package com.sidh.hotelbooking.model.room.feature;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Feature implements Serializable {
    private String featureId;
    private String featureName;
}
