package com.alex.asset.inventory.controller;

import com.alex.asset.inventory.service.CodeEventHandler;
import com.alex.asset.inventory.service.EventService;
import com.alex.asset.utils.SecHolder;
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
@RequestMapping("/api/v2/inventory/events/products")
@Tag(name = "Event Controller", description = "Code Event API")
public class CodeEventController {

    private final String TAG = "EVENT_CONTROLLER - ";
    private final CodeEventHandler codeEventHandler;

    @Operation(summary = "Add products to event by bar code. In the map you can add longitude/latitude")
    @PutMapping("/{event_id}/barcode/{loc_id}")
    @ResponseStatus(HttpStatus.OK)
    public void addProductsToEvent(
            @PathVariable("event_id") Long eventId,
            @PathVariable("loc_id") Long locationId,
            @RequestBody List<Map<String, Object>> listOfCodes) {
        log.info(TAG + "addProductsToEvent");
        codeEventHandler.addProductsToEventByBarCode(listOfCodes, eventId, locationId, SecHolder.getUserId());
    }

    @Operation(summary = "Add products to event by rfid code")
    @PutMapping("/{event_id}/rfid")
    @ResponseStatus(HttpStatus.OK)
    public void addProductsToEventByRFID(
            @PathVariable("event_id") Long eventId,
            @RequestBody List<String> listOfCodes) {
        log.info(TAG + "addProductsToEventByRFID");
        codeEventHandler.addProductsToEventByRfidCode(listOfCodes, eventId, SecHolder.getUserId());
    }

}
