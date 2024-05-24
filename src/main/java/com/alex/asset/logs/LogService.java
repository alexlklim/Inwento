package com.alex.asset.logs;


import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Log;
import com.alex.asset.logs.domain.LogDto;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.exceptions.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return logRepo.findAllByOrderByCreatedDesc()
                .stream().map(this::toDto).toList();

    }

    @SneakyThrows
    public List<LogDto> getLogsForSpecificUser(Long userId) {
        log.info(TAG + "get logs for user with id {}", userId);
        return logRepo.findAllByUserOrderByCreatedDesc(userRepo.findById(userId).orElseThrow(
                        () -> new ResourceNotFoundException("User with id " + userId + " not found")))
                .stream().map(this::toDto).toList();
    }


    @Transactional
    @SneakyThrows
    public void addLog(Long userId, Action action, Section section, String text) {
        log.info(TAG + "add new log");
        Log log = new Log();
        log.setUser(userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + userId + " not found")
        ));
        log.setAction(action);
        log.setSection(section);
        log.setText(text);
        logRepo.save(log);
    }

    LogDto toDto(Log entity) {
        LogDto dto = new LogDto();
        dto.setId(entity.getId());
        dto.setCreated(entity.getCreated());
        dto.setUserEmail(entity.getUser().getEmail());
        dto.setAction(entity.getAction());
        dto.setSection(entity.getSection());
        dto.setText(entity.getText());
        return dto;
    }


}
