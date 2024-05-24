package com.alex.asset.configure.controllers;


import com.alex.asset.configure.domain.Branch;
import com.alex.asset.configure.domain.Location;
import com.alex.asset.configure.services.LocationService;
import com.alex.asset.utils.SecHolder;
import com.alex.asset.utils.dto.DtoActive;
import com.alex.asset.utils.dto.DtoName;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/company/loc")
@Tag(name = "Location Controller", description = "Location API")
public class LocationController {
    private final String TAG = "BRANCH_CONTROLLER - ";
    private final LocationService locationService;

    @Operation(summary = "Get all active branches")
    @GetMapping("/branch")
    @ResponseStatus(HttpStatus.OK)
    public List<Branch> getAllBranches() {
        log.info(TAG + "Try to get all branches");
        return locationService.getBranches();
    }

    @Operation(summary = "Add new branch")
    @PostMapping("/branch")
    @ResponseStatus(HttpStatus.OK)
    public Branch addBranch(
            @RequestBody DtoName dtoName) {
        log.info(TAG + "Try to add branch");
        return locationService.addBranch(dtoName, SecHolder.getUserId());
    }

    @Operation(summary = "Update branch, send id of branch and it's new status: active or not)")
    @PutMapping("/branch")
    @ResponseStatus(HttpStatus.OK)
    public void updateBranch(
            @RequestBody DtoActive dtoActive) {
        log.info(TAG + "Try to update branch");
        locationService.updateBranch(
                dtoActive,
                SecHolder.getUserId());
    }

    @Operation(summary = "Get all active locations (for all branches) location will contain the id of branch)")
    @GetMapping("/location")
    @ResponseStatus(HttpStatus.OK)
    public List<Location> getAllLocations() {
        log.info(TAG + "Try to get all locations");
        return locationService.getLocations();
    }

    @Operation(summary = "Add new location")
    @PostMapping("/{branch_id}/location")
    @ResponseStatus(HttpStatus.OK)
    public Location addLocation(
            @PathVariable("branch_id") Long branchId,
            @RequestBody DtoName dtoName) {
        log.info(TAG + "Try to add location");
        return locationService.addLocation(dtoName, branchId, SecHolder.getUserId());
    }

    @Operation(summary = "Update location, send id of location and it's new status: active or not)")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateLocation(
            @RequestBody DtoActive dtoActive) {
        log.info(TAG + "Try to update location");
        locationService.updateLocation(
                dtoActive,
                SecHolder.getUserId());
    }


}
