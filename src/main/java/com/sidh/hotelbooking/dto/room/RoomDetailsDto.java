package com.sidh.hotelbooking.dto.room;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sidh.hotelbooking.model.room.BedTypeDetails;
import com.sidh.hotelbooking.model.room.feature.Feature;
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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RoomDetailsDto implements Serializable {
    private String roomId;
    private String roomNo;
    private String floorNo;
    private String status;
    private String roomClassId;
    private String roomClassName;
    private String basePrice;
    private List<String> features;
    private List<RoomInsertRequestDto.BedType> bedTypes;
}
