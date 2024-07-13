package com.sidh.hotelbooking.controller;

import com.sidh.hotelbooking.dto.customer.MessageDto;
import com.sidh.hotelbooking.dto.room.*;
import com.sidh.hotelbooking.exception.InvalidRequestException;
import com.sidh.hotelbooking.model.room.RoomDetails;
import com.sidh.hotelbooking.repository.room.RoomRepository;
import com.sidh.hotelbooking.service.room.RoomService;
import com.sidh.hotelbooking.service.room.handler.RoomServiceHandler;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sidh.hotelbooking.util.MapExceptionUtil.mapToMessageDto;

@RestController
@RequestMapping("/api/v1/room")
public class RoomController {

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomServiceHandler roomServiceHandler;

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/class/insert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertRoomClass(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            description = "Provide user details for registration",
            content = {@Content(schema = @Schema(name = "", example = """
                    [
                      {
                        "roomClassName": "string",
                        "basePrice": "number"
                      }
                    ]
                    """))}) @RequestBody List<RoomClassDto> request) {
        if (request != null && !CollectionUtils.isEmpty(request)) {
            request.forEach(room -> {
                if (!StringUtils.hasText(room.getRoomClassName()) || room.getBasePrice() == null) {
                    throw new InvalidRequestException(HttpStatus.BAD_REQUEST,
                            mapToMessageDto(HttpStatus.BAD_REQUEST.toString(), "Invalid request."));
                }
            });
        } else {
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST,
                    mapToMessageDto(HttpStatus.BAD_REQUEST.toString(), "Invalid request."));
        }
        MessageDto response = roomService.createRoomClass(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/insert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomInsertResponseDto> insertRoom(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            description = "Provide user details for registration",
            content = {@Content(schema = @Schema(name = "", example = """
                    [
                      {
                        "roomClassName": "string",
                        "floorNo": "string",
                        "roomNo": "string"
                      }
                    ]
                    """))}) @RequestBody List<RoomDto> request) {
        if (request != null && !CollectionUtils.isEmpty(request)) {
            request.forEach(room -> {
                if (!StringUtils.hasText(room.getRoomClassName()) ||
                        !StringUtils.hasText(room.getRoomNo()) ||
                        !StringUtils.hasText(room.getFloorNo())) {
                    throw new InvalidRequestException(HttpStatus.BAD_REQUEST,
                            mapToMessageDto(HttpStatus.BAD_REQUEST.toString(), "Invalid request."));
                }
            });
        } else {
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST,
                    mapToMessageDto(HttpStatus.BAD_REQUEST.toString(), "Invalid request."));
        }
        RoomInsertResponseDto response = roomService.createRoom(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/feature-class-bd-ty/insert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertRoomWithFeatureClassBedType(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            description = "Provide user details for registration",
            content = {@Content(schema = @Schema(name = "", example = """
                    [
                        {
                            "roomClassName": "string",
                            "floorNo": "string",
                            "roomNo": "string",
                     	    "features": [
                     		    "string",
                     		    "string"
                     	    ],
                     	    "bedTypes": [
                     		    {
                     			    "type": "string",
                     			    "noOfBeds": 0
                     		    }
                     	    ]
                        }
                     ]
                    """))}) @RequestBody List<RoomInsertRequestDto> request) {
        RoomInsertResponseDto response = roomServiceHandler.createRoomWithAll(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/{roomId}")
    public ResponseEntity<Object> findRoomById(@PathVariable String roomId) {
        if (!StringUtils.hasText(roomId)) {
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST,
                    mapToMessageDto(HttpStatus.BAD_REQUEST.toString(), "Invalid request."));
        }
        RoomDetailsDto response = roomService.findRoomByRoomId(roomId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler(value = {InvalidRequestException.class})
    public ResponseEntity<Object> handleRoomException(InvalidRequestException ex) {
        logger.warn("Room operation failed with Error - {}", ex.getError());
        return ResponseEntity.status(ex.getStatus().value())
                .header("produces", MediaType.APPLICATION_JSON_VALUE)
                .body(ex.getMessageDto());
    }
}
