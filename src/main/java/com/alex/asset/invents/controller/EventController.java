package com.alex.asset.invents.controller;


import com.alex.asset.invents.dto.EventDto;
import com.alex.asset.invents.dto.InventDto;
import com.alex.asset.invents.service.EventService;
import com.alex.asset.invents.service.InventService;
import com.alex.asset.security.config.jwt.CustomPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/invent/event")
@Tag(name = "Event Controller", description = "Event API")
public class EventController {
    private final String TAG = "EVENT_CONTROLLER - ";

    private final EventService eventService;


    @Operation(summary = "Get event for specific user for specific invent")
    @GetMapping("/inventarization/{id}")
    public ResponseEntity<List<EventDto>> getEventsForSpecificUser(
            @PathVariable("id") Long inventId,
            Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "Get all events for specific user for specific invent");

        return new ResponseEntity<>(eventService.getMyEventsForCurrentInvent(principal.getUserId(), inventId), HttpStatus.OK);
    }

    @Operation(summary = "Get event by id")
    @GetMapping("/events/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable("id") Long eventId) {
        log.info(TAG + "Get event");
        return new ResponseEntity<>(eventService.getEvent(eventId), HttpStatus.OK);
    }
//
//
//    @Operation(summary = "Get invent by id")
//    @GetMapping("/{id}")
//    public ResponseEntity<InventDto> getEventsByInvent(@PathVariable("id") Long inventId) {
//        log.info(TAG + "Get invent with id {}", inventId);
//        return new ResponseEntity<>(inventService.getInventById(inventId), HttpStatus.OK);
//    }
//
//    @Operation(summary = "Get invent by id")
//    @GetMapping("/{id}")
//    public ResponseEntity<InventDto> createNewEvent(@PathVariable("id") Long inventId) {
//        log.info(TAG + "Get invent with id {}", inventId);
//        return new ResponseEntity<>(inventService.getInventById(inventId), HttpStatus.OK);
//    }
//
//
//    @Operation(summary = "Get invent by id")
//    @GetMapping("/{id}")
//    public ResponseEntity<InventDto> updateEvent(@PathVariable("id") Long inventId) {
//        log.info(TAG + "Get invent with id {}", inventId);
//        return new ResponseEntity<>(inventService.getInventById(inventId), HttpStatus.OK);
//    }
//
//
//    @Operation(summary = "Get invent by id")
//    @GetMapping("/{id}")
//    public ResponseEntity<InventDto> finishEvent(@PathVariable("id") Long inventId) {
//        log.info(TAG + "Get invent with id {}", inventId);
//        return new ResponseEntity<>(inventService.getInventById(inventId), HttpStatus.OK);
//    }
//
//
//    @Operation(summary = "Get invent by id")
//    @GetMapping("/{id}")
//    public ResponseEntity<InventDto> updateVisibilityOfEvent(@PathVariable("id") Long inventId) {
//        log.info(TAG + "Get invent with id {}", inventId);
//        return new ResponseEntity<>(inventService.getInventById(inventId), HttpStatus.OK);
//    }
}
