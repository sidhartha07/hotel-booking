package com.sidh.hotelbooking.model.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoomClass implements Serializable {
    private String roomClassId;
    private String roomClassName;
    private Double basePrice;
}
