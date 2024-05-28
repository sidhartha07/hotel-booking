package com.sidh.hotelbooking.util;

import com.sidh.hotelbooking.dto.customer.MessageDto;

public class MapExceptionUtil {

    private MapExceptionUtil() {

    }

    public static MessageDto mapToMessageDto(String status, String message) {
        return MessageDto.builder()
                .status(status)
                .message(message)
                .build();
    }
}
