package com.alex.asset.inventory.controller;


import com.alex.asset.inventory.dto.EventV1Create;
import com.alex.asset.inventory.dto.EventV2Get;
import com.alex.asset.inventory.service.EventService;
import com.alex.asset.security.config.jwt.CustomPrincipal;
import com.alex.asset.utils.dto.DtoActive;
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
@RequestMapping("/api/v1/event")
@Tag(name = "Event Controller", description = "Event API")
public class EventController {
    private final String TAG = "EVENT_CONTROLLER - ";
    private final EventService eventService;


    @Operation(summary = "Get events for specific user for specific invent")
    @GetMapping("/invent/{id}")
    public ResponseEntity<List<EventV2Get>> getEventsForSpecificUser(
            @PathVariable("id") Long inventId,
            Authentication authentication) {
        log.info(TAG + "Get all events for specific user for specific invent");
        return new ResponseEntity<>(eventService.getEventsForSpecificUserAndInvent(
                ((CustomPrincipal) authentication.getPrincipal()).getUserId(),
                inventId), HttpStatus.OK);
    }

    @Operation(summary = "Get all events for specific invent")
    @Secured("ROLE_ADMIN")
    @GetMapping("/invent/{id}/all")
    public ResponseEntity<List<EventV2Get>> getAllEventsForSpecificInvent(
            @PathVariable("id") Long inventId) {
        log.info(TAG + "Get all events for specific user for specific invent");

        return new ResponseEntity<>(eventService.getAllEventsForSpecificInvent(inventId), HttpStatus.OK);
    }

    @Operation(summary = "Get event by id")
    @GetMapping("/{id}")
    public ResponseEntity<EventV2Get> getEventById(
            @PathVariable("id") Long eventId) {
        log.info(TAG + "Get event by id");
        return new ResponseEntity<>(
                eventService.getEvent(eventId),
                HttpStatus.OK);
    }


    @Operation(summary = "Create event for current invent")
    @PostMapping
    public ResponseEntity<EventV2Get> create(
            @RequestBody EventV1Create eventV1CreateDto,
            Authentication authentication) {
        log.info(TAG + "Create new event for current invent");
        return new ResponseEntity<>(
                eventService.createEvent(
                        ((CustomPrincipal) authentication.getPrincipal()).getUserId(),
                        eventV1CreateDto),
                HttpStatus.CREATED);
    }


    @Operation(summary = "Update visibility of event")
    @Secured("ROLE_ADMIN")
    @PutMapping("/active")
    public ResponseEntity<HttpStatus> updateVisibility(
            @RequestBody DtoActive dtoActive,
            Authentication authentication) {
        log.info(TAG + "Update visibility of event");
        eventService.updateVisibility(
                ((CustomPrincipal) authentication.getPrincipal()).getUserId(),
                dtoActive);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Add products to event")
    @PutMapping("/{id}/products/bar-code")
    public ResponseEntity<HttpStatus> addProductsToEvent(
            @PathVariable("id") Long eventId,
            @RequestBody List<String> listOfBarCodes,
            Authentication authentication) {
        log.info(TAG + "Add product which exists in fact");
        eventService.addProductsToEventByBarCode(
                ((CustomPrincipal) authentication.getPrincipal()).getUserId(),
                eventId,
                listOfBarCodes);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
