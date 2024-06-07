package com.alex.asset.inventory.controller;


import com.alex.asset.inventory.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    // get event by id with custom fields
    // get event by inventory id with custom fields
    // update event
    // create event (for cases, when something goes wrong and server didn't create event for one of branch


    // add product to event by bar code
    // add product to event by rfid code
//     we need these two endpoint because of difference in rules how we treat rfid and bar code




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
}
