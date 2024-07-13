package com.sidh.hotelbooking.model.room.bedtype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BedType implements Serializable {
    private String bedTypeId;
    private String bedTypeName;
}
