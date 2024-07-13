package com.sidh.hotelbooking.dto.room;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class RoomInsertResponseDto implements Serializable {
    private List<String> roomsInsertedSuccess;
    private List<String> roomsInsertedFailure;
}
