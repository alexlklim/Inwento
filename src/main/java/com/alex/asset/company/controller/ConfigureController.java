package com.alex.asset.company.controller;

import com.alex.asset.company.domain.Branch;
import com.alex.asset.company.domain.MPK;
import com.alex.asset.company.domain.KST;
import com.alex.asset.company.dto.DataDto;
import com.alex.asset.utils.dto.ActiveDto;
import com.alex.asset.utils.dto.DtoName;
import com.alex.asset.company.service.CompanyService;
import com.alex.asset.company.service.ConfigureService;
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
@RequestMapping("/api/core/company")
public class ConfigureController {
    private final String TAG = "FIELDS_CONTROLLER - ";

    private final CompanyService companyService;

    private final ConfigureService configureService;

    @GetMapping("/all")
    public ResponseEntity<DataDto> getAllFields() {
        log.info(TAG + "Try to get all fields");
        return new ResponseEntity<>(companyService.getAllFields(), HttpStatus.OK);
    }


    @PutMapping("/unit")
    public ResponseEntity<HttpStatus> updateUnits(@RequestBody List<ActiveDto> DTOs){
        log.info(TAG + "Try to update units");
        configureService.updateUnits(DTOs);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/asset-status")
    public ResponseEntity<HttpStatus> updateAssetStatuses(@RequestBody List<ActiveDto> DTOs){
        log.info(TAG + "Try to update asset statuses");
        configureService.updateAssetStatuses(DTOs);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/kst/{num}")
    public ResponseEntity<List<KST>> getKSTByNum(@PathVariable("num") String num) {
        log.info(TAG + "Try to get KST by num {}", num);
        if (num.length() < 2) return new ResponseEntity<>(HttpStatus.CONFLICT);
        return new ResponseEntity<>(configureService.getKSTsByNum(num), HttpStatus.OK);
    }



    @GetMapping("/branch")
    public ResponseEntity<List<Branch>> getAllBranches() {
        log.info(TAG + "Try to get all branches");
        return new ResponseEntity<>(configureService.getBranches(), HttpStatus.OK);
    }
    @PostMapping("/branch")
    public ResponseEntity<Branch> addBranch(@RequestBody DtoName dto) {
        log.info(TAG + "Try to add branch {}", dto.getName());
        return new ResponseEntity<>(configureService.addBranch(dto), HttpStatus.OK);
    }

    @PutMapping("/branch")
    public ResponseEntity<HttpStatus> updateBranch(@RequestBody ActiveDto dto) {
        log.info(TAG + "Try to update branch {}", dto.getId());
        configureService.updateBranch(dto);
        return new ResponseEntity<>( HttpStatus.OK);
    }



    @GetMapping("/mpk")
    public ResponseEntity<List<MPK>> getAllMPKs() {
        log.info(TAG + "Try to get all MPK");
        return new ResponseEntity<>(configureService.getMPKs(), HttpStatus.OK);
    }
    @PostMapping("/mpk")
    public ResponseEntity<MPK> addMPK(@RequestBody DtoName dto) {
        log.info(TAG + "Try to add MPK {}", dto.getName());
        return new ResponseEntity<>(configureService.addMPK(dto), HttpStatus.OK);
    }

    @PutMapping("/mpk")
    public ResponseEntity<HttpStatus> updateMPKs(@RequestBody ActiveDto dto) {
        log.info(TAG + "Try to update MPK {}", dto.getId());
        configureService.updateMPK(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
