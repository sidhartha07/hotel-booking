package com.sidh.hotelbooking.dto.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoomClassDto implements Serializable {
    private String roomClassName;
    private Double basePrice;
}
