package com.alex.asset.company.controller.configure;


import com.alex.asset.company.domain.KST;
import com.alex.asset.company.service.CompanyService;
import com.alex.asset.company.service.ConfigureService;
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
@RequestMapping("/api/core/company/kst")
@Tag(name = "KST Controller", description = "KST API")
public class KSTController {
    private final String TAG = "KST_CONTROLLER - ";

    private final ConfigureService configureService;

    @Operation(summary = "Get all active KST by KST num")
    @GetMapping("/{num}")
    public ResponseEntity<List<KST>> getKSTByNum(@PathVariable("num") String num) {
        log.info(TAG + "Try to get KST by num {}", num);
        if (num.length() < 2) return new ResponseEntity<>(HttpStatus.CONFLICT);
        return new ResponseEntity<>(configureService.getKSTsByNum(num), HttpStatus.OK);
    }



}
