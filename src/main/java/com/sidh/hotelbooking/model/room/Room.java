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
public class Room implements Serializable {
    private String roomId;
    private String roomClassId;
    private String floorNo;
    private String status;
    private String roomNo;
}
