package com.alex.asset.logs;


import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Log;
import com.alex.asset.logs.domain.LogDto;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.exceptions.shared.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogService {
    private final String TAG = "LOG_SERVICE - ";
    private final UserRepo userRepo;
    private final LogRepo logRepo;


    public List<LogDto> getAllLogs() {
        log.info(TAG + "get all logs");
        return LogMapper.toDTOs(logRepo.findAllByOrderByCreatedDesc());

    }

    @SneakyThrows
    public List<LogDto> getLogsForSpecificUser(Long userId) {
        log.info(TAG + "get logs for user with id {}", userId);
        return logRepo.findAllByUserOrderByCreatedDesc(userRepo.findById(userId).orElseThrow(
                        () -> new ResourceNotFoundException("User with id " + userId + " not found")))
                .stream().map(LogMapper::toDto).toList();
    }



    @Transactional
    @SneakyThrows
    public void addLog(User user, Action action, Section section, String text) {
        log.info(TAG + "add new log");
        Log log = new Log().toBuilder().user(user).action(action).section(section).text(text).build();
        logRepo.save(log);
    }
    @Transactional
    @SneakyThrows
    public void addLog(Long userId, Action action, Section section, String text) {
        log.info(TAG + "add new log");
        addLog(userRepo.getUser(userId), action, section, text);
    }


    public List<LogDto> getLogsForSpecificDateRange(LocalDate startDate, LocalDate endDate) {

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();

        return LogMapper.toDTOs(
                logRepo.getLogsByDataRange(startDateTime, endDateTime));
    }
}
