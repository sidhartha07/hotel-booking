package com.sidh.hotelbooking.dto.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoomInsertRequestDto implements Serializable {
    private String roomClassName;
    private String floorNo;
    private String roomNo;
    private List<String> features;
    private List<BedType> bedTypes;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class BedType {
        private String type;
        private Integer noOfBeds;
    }
}
