package com.sidh.hotelbooking.model.room;

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
public class RoomDetails implements Serializable {
    private String roomId;
    private String roomNo;
    private String floorNo;
    private String status;
    private String roomClassId;
    private String roomClassName;
    private String basePrice;
}
