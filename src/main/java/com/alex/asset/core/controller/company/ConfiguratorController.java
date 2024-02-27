package com.alex.asset.core.controller.company;

import com.alex.asset.core.domain.fields.Branch;
import com.alex.asset.core.domain.fields.MPK;
import com.alex.asset.core.domain.fields.constants.KST;
import com.alex.asset.core.dto.DataDto;
import com.alex.asset.core.dto.simple.ActiveDto;
import com.alex.asset.core.dto.simple.DtoName;
import com.alex.asset.core.service.CompanyService;
import com.alex.asset.core.service.FieldService;
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
public class ConfiguratorController {
    private final String TAG = "FIELDS_CONTROLLER - ";

    private final CompanyService companyService;

    private final FieldService fieldService;

    @GetMapping("/all")
    public ResponseEntity<DataDto> getAllFields() {
        log.info(TAG + "Try to get all fields");
        return new ResponseEntity<>(companyService.getAllFields(), HttpStatus.OK);
    }


    @PutMapping("/unit")
    public ResponseEntity<HttpStatus> updateUnits(@RequestBody List<ActiveDto> DTOs){
        log.info(TAG + "Try to update units");
        fieldService.updateUnits(DTOs);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/asset-status")
    public ResponseEntity<HttpStatus> updateAssetStatuses(@RequestBody List<ActiveDto> DTOs){
        log.info(TAG + "Try to update asset statuses");
        fieldService.updateAssetStatuses(DTOs);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/kst/{num}")
    public ResponseEntity<List<KST>> getKSTByNum(@PathVariable("num") String num) {
        log.info(TAG + "Try to get KST by num {}", num);
        if (num.length() < 2) return new ResponseEntity<>(HttpStatus.CONFLICT);
        return new ResponseEntity<>(fieldService.getKSTsByNum(num), HttpStatus.OK);
    }



    @GetMapping("/branch")
    public ResponseEntity<List<Branch>> getAllBranches() {
        log.info(TAG + "Try to get all branches");
        return new ResponseEntity<>(fieldService.getBranches(), HttpStatus.OK);
    }
    @PostMapping("/branch")
    public ResponseEntity<Branch> addBranch(@RequestBody DtoName dto) {
        log.info(TAG + "Try to add branch {}", dto.getName());
        return new ResponseEntity<>(fieldService.addBranch(dto), HttpStatus.OK);
    }

    @PutMapping("/branch")
    public ResponseEntity<HttpStatus> updateBranch(@RequestBody ActiveDto dto) {
        log.info(TAG + "Try to update branch {}", dto.getId());
        fieldService.updateBranch(dto);
        return new ResponseEntity<>( HttpStatus.OK);
    }
    @DeleteMapping("/branch/{id}")
    public ResponseEntity<HttpStatus> deleteBranch(@PathVariable("id") Long id) {
        log.info(TAG + "Try to delete branch {}", id);
        fieldService.deleteBranch(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/mpk")
    public ResponseEntity<List<MPK>> getAllMPKs() {
        log.info(TAG + "Try to get all MPK");
        return new ResponseEntity<>(fieldService.getMPKs(), HttpStatus.OK);
    }
    @PostMapping("/mpk")
    public ResponseEntity<MPK> addMPK(@RequestBody DtoName dto) {
        log.info(TAG + "Try to add MPK {}", dto.getName());
        return new ResponseEntity<>(fieldService.addMPK(dto), HttpStatus.OK);
    }

    @PutMapping("/mpk")
    public ResponseEntity<HttpStatus> updateMPKs(@RequestBody ActiveDto dto) {
        log.info(TAG + "Try to update MPK {}", dto.getId());
        fieldService.updateMPK(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/mpk/{id}")
    public ResponseEntity<HttpStatus> deleteMPK(@PathVariable("id") Long id) {
        log.info(TAG + "Try to delete MPK {}", id);
        fieldService.deleteMPK(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }






}
