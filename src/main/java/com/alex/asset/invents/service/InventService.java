package com.alex.asset.invents.service;


import com.alex.asset.invents.domain.Invent;
import com.alex.asset.invents.dto.InventDto;
import com.alex.asset.invents.mapper.InventMapper;
import com.alex.asset.invents.repo.InventRepo;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.dto.DtoActive;
import com.alex.asset.utils.expceptions.errors.InventIsAlreadyInProgress;
import com.alex.asset.utils.expceptions.errors.InventIsAlreadyNotActive;
import com.alex.asset.utils.expceptions.errors.InventIsNotStartedYet;
import com.alex.asset.utils.expceptions.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class InventService {
    private final String TAG = "INVENT_SERVICE - ";

    private final InventRepo inventRepo;
    private final UserRepo userRepo;
    private final InventMapper inventMapper;


    public boolean isAnyInventActive() {
        log.info(TAG + "Check is any invent active now or not");
        return inventRepo.isInventNow(LocalDate.now());
    }


    @SneakyThrows
    public void startInvent(Long userId, InventDto dto) {
        log.info(TAG + "Start new invent by user with id {}", userId);
        if (isAnyInventActive()) {
            throw new InventIsAlreadyInProgress("Invent is already in progress this time");
        }
        log.info(TAG + "User {} create new invent", userId);
        Invent invent = new Invent();
        invent.setActive(true);
        invent.setStartDate(dto.getStartDate());
        invent.setFinishDate(dto.getFinishDate());
        invent.setInfo(dto.getInfo());
        invent.setUser(userRepo.getUser(userId));
        inventRepo.save(invent);
    }


    @SneakyThrows
    public void finishInvent(Long userId, Long inventId) {
        log.info(TAG + "Finish invent by user with id {}", userId);
        Invent invent = inventRepo.findById(inventId).orElseThrow(
                () -> new ResourceNotFoundException("Invent with id " + inventId + " not found"));
        LocalDate now = LocalDate.now();
        if (now.isAfter(invent.getStartDate()) || now.equals(invent.getStartDate())) {
            invent.setFinishDate(now);
            inventRepo.save(invent);
        } else {
            throw new InventIsNotStartedYet("Invent with " + inventId + " is not started yet");
        }
    }


    @SneakyThrows
    public void changeVisibility(Long userId, DtoActive dto) {
        log.info(TAG + "Change visibility of invent with id {} by user with id {}", dto.getId(), userId);
        Invent invent = inventRepo.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Invent with id " + dto.getId() + " not found"));
        if (!invent.isActive()) {
            throw new InventIsAlreadyNotActive("Invent with " + dto.getId() + " is already not active");
        }
        invent.setActive(false);
        inventRepo.save(invent);
    }

    public InventDto getInventById(Long inventId) {
        log.info(TAG + "Get invent by id {}", inventId);
        return inventMapper.toDto(inventRepo.findById(inventId).orElseThrow(
                () -> new ResourceNotFoundException("Invent with id " + inventId + " not found")));
    }

    public InventDto getCurrentInvent() {
        log.info(TAG + "Get current invent");
        return inventMapper.toDto(inventRepo.findActiveInventByDate(LocalDate.now()).orElseThrow(
                () -> new ResourceNotFoundException("No active invent at this moment")));

    }
}
