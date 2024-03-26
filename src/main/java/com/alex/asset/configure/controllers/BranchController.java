package com.alex.asset.configure.controllers;


import com.alex.asset.configure.domain.Branch;
import com.alex.asset.configure.services.ConfigureService;
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
@RequestMapping("/api/v1/company/branch")
@Tag(name = "Branch Controller", description = "Branch API")
public class BranchController {
    private final String TAG = "BRANCH_CONTROLLER - ";

    private final ConfigureService configureService;

    @Operation(summary = "Get all active branches")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Branch> getAllBranches() {
        log.info(TAG + "Try to get all branches");
        return configureService.getBranches();
    }

    @Operation(summary = "Add new branch")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Branch addBranch(
            @RequestBody DtoName dtoName) {
        log.info(TAG + "Try to add branch");
        return configureService.addBranch(dtoName, SecHolder.getUserId());
    }

    @Operation(summary = "Update branch, send id of branch and it's new status: active or not)")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateBranch(
            @RequestBody DtoActive dtoActive) {
        log.info(TAG + "Try to update branch");
        configureService.updateBranch(
                dtoActive,
                SecHolder.getUserId());
    }


}
