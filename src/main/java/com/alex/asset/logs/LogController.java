package com.alex.asset.logs;


import com.alex.asset.logs.domain.LogDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/logs")
@Tag(name = "Log Controller", description = "Log API")
public class LogController {


    private final String TAG = "LOG_CONTROLLER - ";

    private final LogService logService;


    @Operation(summary = "Get all logs")
    @Secured("ROLE_ADMIN")
    @GetMapping
    public ResponseEntity<List<LogDto>> getAllLogs() {
        log.info(TAG + "Get all logs");
        return new ResponseEntity<>(logService.getAllLogs(), HttpStatus.OK);
    }


    @Operation(summary = "Get all logs")
    @Secured("ROLE_ADMIN")
    @GetMapping("/user/{id}")
    public ResponseEntity<List<LogDto>> getLogsForSpecificUser(
            @PathVariable("id") Long userId) {
        log.info(TAG + "Get logs for user with id {}", userId);
        return new ResponseEntity<>(logService.getLogsForSpecificUser(userId), HttpStatus.OK);
    }


}
