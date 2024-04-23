package com.alex.asset.inventory.controller;


import com.alex.asset.inventory.dto.EventDTO;
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
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
    @RequestMapping("/api/v1/inventory/events")
@Tag(name = "Event Controller", description = "Event API")
public class EventController {
    private final String TAG = "EVENT_CONTROLLER - ";
    private final EventService eventService;


    @Operation(summary = "Get event by id")
    @GetMapping("/{event_id}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getEventById(
            @PathVariable("event_id") Long eventId,
            @RequestBody(required = false) List<String> eventFields) {
        log.info(TAG + "Get event by id");
        return eventService.getEvent(eventId, eventFields);
    }


    @Operation(summary = "Get events for inventory by id")
    @GetMapping("/inv/{inventory_id}/mode/{mode}/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, Object>> getEventsForInventory(
            @PathVariable("inventory_id") Long inventoryId,
            @PathVariable("mode") String mode,
            @RequestBody(required = false) List<String> eventFields) {
        log.info(TAG + "Get events for specific inventory");

        return eventService.getEventsForInventory(inventoryId, mode, eventFields, SecHolder.getUserId());
    }


    @Operation(summary = "Create event for current inventory")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> create(
            @RequestBody EventDTO eventV1CreateDto) {
        log.info(TAG + "Create new event for current inventory");
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

    @Operation(summary = "Add products to event by code. In the map you can add longitude/latitude/bar_code/rfid_code")
    @PutMapping("/{event_id}/products/{loc_id}/{type_code}")
    @ResponseStatus(HttpStatus.OK)
    public void addProductsToEvent(
            @PathVariable("event_id") Long eventId,
            @PathVariable("loc_id") Long locationId,
            @PathVariable("type_code") String typeCode,
            @RequestBody List<Map<String, Object>> listOfCodes) {
        log.info(TAG + "Add product which exists in fact");
        eventService.addProductsToEventByBarCode(listOfCodes, eventId, locationId, typeCode, SecHolder.getUserId());
    }


}