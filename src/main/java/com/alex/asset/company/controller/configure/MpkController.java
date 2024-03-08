package com.alex.asset.company.controller.configure;

import com.alex.asset.company.domain.MPK;
import com.alex.asset.company.service.ConfigureService;
import com.alex.asset.utils.dto.DtoActive;
import com.alex.asset.utils.dto.DtoName;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/core/company/mpk")
@Tag(name = "MPK Controller", description = "MPK API")
public class MpkController {

    private final String TAG = "FIELDS_CONTROLLER - ";

    private final ConfigureService configureService;

    @Operation(summary = "Get all active MPK")
    @GetMapping
    public ResponseEntity<List<MPK>> getAllMPKs() {
        log.info(TAG + "Try to get all MPK");
        return new ResponseEntity<>(configureService.getMPKs(), HttpStatus.OK);
    }

    @Operation(summary = "Add new MPK")
    @PostMapping
    public ResponseEntity<MPK> addMPK(@RequestBody DtoName dto) {
        log.info(TAG + "Try to add MPK {}", dto.getName());
        return new ResponseEntity<>(configureService.addMPK(dto), HttpStatus.OK);
    }

    @Operation(summary = "Update MPK visibility by id")
    @PutMapping
    public ResponseEntity<HttpStatus> updateMPKs(@RequestBody DtoActive dto) {
        log.info(TAG + "Try to update MPK {}", dto.getId());
        configureService.updateMPK(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
