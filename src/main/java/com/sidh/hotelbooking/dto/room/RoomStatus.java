package com.sidh.hotelbooking.dto.room;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum RoomStatus {
    AVAIL("AVAIL", "AVAILABLE", 0),
    OCCUP("OCCUP", "OCCUPIED", 1),
    RDCLN("RDCLN", "READY TO CLEAN", 2);

    public final String roomStatus;
    public final String displayStatus;
    public final int order;

    RoomStatus(String roomStatus, String displayStatus, int order) {
        this.roomStatus = roomStatus;
        this.displayStatus = displayStatus;
        this.order = order;
    }
}
