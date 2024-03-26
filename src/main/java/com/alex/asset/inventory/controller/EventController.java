package com.alex.asset.inventory.controller;


import com.alex.asset.inventory.dto.EventV1Create;
import com.alex.asset.inventory.dto.EventV2Get;
import com.alex.asset.inventory.service.EventService;
import com.alex.asset.utils.SecHolder;
import com.alex.asset.utils.dto.DtoActive;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
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
    @ResponseStatus(HttpStatus.OK)
    public List<EventV2Get> getEventsForSpecificUser(
            @PathVariable("id") Long inventId) {
        log.info(TAG + "Get all events for specific user for specific invent");
        return eventService.getEventsForSpecificUserAndInvent(SecHolder.getUserId(), inventId);
    }

    @Operation(summary = "Get all events for specific invent")
    @Secured("ROLE_ADMIN")
    @GetMapping("/invent/{id}/all")
    @ResponseStatus(HttpStatus.OK)
    public List<EventV2Get> getAllEventsForSpecificInvent(
            @PathVariable("id") Long inventId) {
        log.info(TAG + "Get all events for specific user for specific invent");
        return eventService.getAllEventsForSpecificInvent(inventId);
    }

    @Operation(summary = "Get event by id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventV2Get getEventById(
            @PathVariable("id") Long eventId) {
        log.info(TAG + "Get event by id");
        return eventService.getEvent(eventId);
    }


    @Operation(summary = "Create event for current invent")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventV2Get create(
            @RequestBody EventV1Create eventV1CreateDto) {
        log.info(TAG + "Create new event for current invent");
        return eventService.createEvent(
                SecHolder.getUserId(),
                eventV1CreateDto
        );
    }


    @Operation(summary = "Update visibility of event")
    @Secured("ROLE_ADMIN")
    @PutMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public void updateVisibility(
            @RequestBody DtoActive dtoActive) {
        log.info(TAG + "Update visibility of event");
        eventService.updateVisibility(
                SecHolder.getUserId(),
                dtoActive);
    }

    @Operation(summary = "Add products to event")
    @PutMapping("/{id}/products/bar-code")
    @ResponseStatus(HttpStatus.OK)
    public void addProductsToEvent(
            @PathVariable("id") Long eventId,
            @RequestBody List<String> listOfBarCodes) {
        log.info(TAG + "Add product which exists in fact");
        eventService.addProductsToEventByBarCode(
                SecHolder.getUserId(),
                eventId,
                listOfBarCodes);
    }


}
