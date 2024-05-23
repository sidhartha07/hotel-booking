package com.sidh.hotelbooking.exception;

import com.sidh.hotelbooking.dto.customer.MessageDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidRequestException extends RuntimeException {
    private static final long serialVersionUID = 30L;

    private final HttpStatus status;
    private final transient Object error;
    private final MessageDto messageDto;

    public InvalidRequestException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
        this.error = message;
        this.messageDto = null;
    }

    public InvalidRequestException(HttpStatus status, MessageDto messageDto) {
        super(messageDto.getMessage());
        this.status = status;
        this.error = messageDto;
        this.messageDto = messageDto;
    }
}
