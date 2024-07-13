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
public class BedTypeDetails implements Serializable {
    private String bedTypeName;
    private Integer noOfBeds;
}
