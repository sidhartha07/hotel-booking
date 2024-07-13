package com.sidh.hotelbooking.dto.room.bedtype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoomClassBedTypeDto implements Serializable {
    private String bedTypeId;
    private String bedTypeName;
    private Integer noOfBeds;
}
