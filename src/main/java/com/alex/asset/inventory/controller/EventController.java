package com.alex.asset.inventory.controller;


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
@RequestMapping("/api/v2/inventory/events")
@Tag(name = "Event Controller", description = "Event API")
public class EventController {
    private final String TAG = "EVENT_CONTROLLER - ";
    private final EventService eventService;


    @Operation(summary = "Get event by id")
    @GetMapping("/{event_id}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getEventById(
            @PathVariable("event_id") Long eventId,
            @RequestBody(required = false) List<String> fields) {
        log.info(TAG + "getEventById");
        return eventService.getEventById(eventId, fields, SecHolder.getUserId());
    }


    @Operation(summary = "Get events for inventory by id")
    @GetMapping("all/{inventory_id}/{access_mode}")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, Object>> getEventsForInventory(
            @PathVariable("inventory_id") Long inventoryId,
            @PathVariable("access_mode") String accessMode,
            @RequestBody(required = false) List<String> fields) {
        log.info(TAG + "getEventsForInventory");
        return eventService.getEventsForInventory(inventoryId, accessMode, fields, SecHolder.getUserId());
    }

    @Operation(summary = "Update visibility of event")
    @Secured("ROLE_ADMIN")
    @PutMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public void updateVisibility(
            @RequestBody DtoActive dtoActive) {
        log.info(TAG + "updateVisibility");
        eventService.updateVisibility(
                SecHolder.getUserId(),
                dtoActive);
    }



}
