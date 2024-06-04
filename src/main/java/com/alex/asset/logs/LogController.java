package com.alex.asset.logs;


import com.alex.asset.logs.domain.LogDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    @ResponseStatus(HttpStatus.OK)
    public List<LogDto> getAllLogs() {
        log.info(TAG + "Get all logs");
        return logService.getAllLogs();
    }


    @Operation(summary = "Get all logs")
    @Secured("ROLE_ADMIN")
    @GetMapping("/user/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<LogDto> getLogsForSpecificUser(
            @PathVariable("user_id") Long userId) {
        log.info(TAG + "Get logs for user with id {}", userId);
        return logService.getLogsForSpecificUser(userId);
    }

    @Operation(summary = "Get all logs")
    @Secured("ROLE_ADMIN")
    @GetMapping("/date/{start_date}/{end_date}")
    @ResponseStatus(HttpStatus.OK)
    public List<LogDto> getLogsByDateRange(
            @PathVariable("start_date") LocalDate startDate,
            @PathVariable("end_date") LocalDate endDate) {
        log.info(TAG + "Get logs for user with id ");
        return logService.getLogsForSpecificDateRange(startDate, endDate);
    }



}
