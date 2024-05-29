package com.alex.asset.inventory.controller;


import com.alex.asset.inventory.dto.EventDTO;
import com.alex.asset.inventory.service.EventService;
import com.alex.asset.utils.SecHolder;
import com.alex.asset.utils.UtilsEvent;
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



    @Operation(summary = "Get short event by id (without products, only their amount) endpoint for android app")
    @GetMapping("/app/{event_id}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getShortEventById(
            @PathVariable("event_id") Long eventId) {
        log.info(TAG + "Get short event by id");
        return eventService.getEvent(eventId, UtilsEvent.getFieldsSimpleView());
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
    @Secured("ROLE_ADMIN")
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

    @Operation(summary = "Add products to event by bar code. In the map you can add longitude/latitude")
    @PutMapping("/{event_id}/products/barcode/{loc_id}")
    @ResponseStatus(HttpStatus.OK)
    public void addProductsToEvent(
            @PathVariable("event_id") Long eventId,
            @PathVariable("loc_id") Long locationId,
            @RequestBody List<Map<String, Object>> listOfCodes) {
        log.info(TAG + "BAR CODE Add product which exists in fact by BAR CODE");
        eventService.addProductsToEventByBarCode(listOfCodes, eventId, locationId, SecHolder.getUserId());
    }

    @Operation(summary = "Add products to event by rfid code")
    @PutMapping("/{event_id}/products/rfid")
    @ResponseStatus(HttpStatus.OK)
    public void addProductsToEventByRFID(
            @PathVariable("event_id") Long eventId,
            @RequestBody List<String> listOfCodes) {
        log.info(TAG + "RFID Add product which exists in fact by RFID CODE");
        System.out.println(listOfCodes);
        eventService.addProductsToEventByRfidCode(listOfCodes, eventId, SecHolder.getUserId());
    }

}