package com.alex.asset.company.controller.configure;


import com.alex.asset.company.domain.Branch;
import com.alex.asset.company.service.CompanyService;
import com.alex.asset.company.service.ConfigureService;
import com.alex.asset.utils.dto.DtoActive;
import com.alex.asset.utils.dto.DtoName;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/core/company/branch")
@Tag(name = "Branch Controller", description = "Branch API")
public class BranchController {
    private final String TAG = "BRANCH_CONTROLLER - ";

    private final ConfigureService configureService;

    @GetMapping
    public ResponseEntity<List<Branch>> getAllBranches() {
        log.info(TAG + "Try to get all branches");
        return new ResponseEntity<>(configureService.getBranches(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Branch> addBranch(@RequestBody DtoName dto) {
        log.info(TAG + "Try to add branch {}", dto.getName());
        return new ResponseEntity<>(configureService.addBranch(dto), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateBranch(@RequestBody DtoActive dto) {
        log.info(TAG + "Try to update branch {}", dto.getId());
        configureService.updateBranch(dto);
        return new ResponseEntity<>( HttpStatus.OK);
    }


}
