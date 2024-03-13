package com.alex.asset.inventory.service;


import com.alex.asset.inventory.domain.Inventory;
import com.alex.asset.inventory.dto.InventoryDto;
import com.alex.asset.inventory.mapper.InventoryMapper;
import com.alex.asset.inventory.repo.InventoryRepo;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.notification.NotificationService;
import com.alex.asset.notification.domain.Reason;
import com.alex.asset.utils.dto.DtoActive;
import com.alex.asset.utils.expceptions.errors.InventIsAlreadyInProgress;
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
public class InventoryService {
    private final String TAG = "INVENTORY_SERVICE - ";

    private final InventoryRepo inventoryRepo;
    private final InventoryMapper inventoryMapper;

    private final LogService logService;
    private final NotificationService notificationService;


    public boolean isInventNow() {
        log.info(TAG + "Check is any invent active now or not");
        return inventoryRepo.isInventNow(LocalDate.now());
    }


    @SneakyThrows
    public void startInvent(Long userId, InventoryDto dto) {
        log.info(TAG + "User {} create new invent", userId);
        if (isInventNow()) throw new InventIsAlreadyInProgress("Invent is already in progress this time");
        Inventory inventory = new Inventory();
        inventory.setActive(true);
        inventory.setStartDate(dto.getStartDate());
        inventory.setFinished(dto.isFinished());
        if (dto.isFinished()) inventory.setFinishDate(LocalDate.now());
        else inventory.setFinishDate(null);
        inventory.setInfo(dto.getInfo());
//        inventory.setUser(userRepo.getUser(userId));
        inventoryRepo.save(inventory);

        logService.addLog(userId, Action.CREATE, Section.INVENT, dto.toString());
        notificationService.sendSystemNotificationToAllUsers(Reason.PLANNED_INVENTORY);

    }


    @SneakyThrows
    public void updateInvent(Long userId, Long inventId, InventoryDto dto) {
        log.info(TAG + "Update invent by user with id {}", userId);
        Inventory inventory = inventoryRepo.findById(inventId).orElseThrow(
                () -> new ResourceNotFoundException("Invent with id " + inventId + " not found"));
        inventory.setStartDate(dto.getStartDate());
        inventory.setFinished(dto.isFinished());
        if (dto.isFinished()) inventory.setFinishDate(LocalDate.now());
        else inventory.setFinishDate(null);
        inventory.setInfo(dto.getInfo());
        inventoryRepo.save(inventory);

        logService.addLog(userId, Action.UPDATE, Section.INVENT, dto.toString());

    }


    @SneakyThrows
    public void changeVisibility(Long userId, DtoActive dto) {
        log.info(TAG + "Change visibility of invent with id {} by user with id {}", dto.getId(), userId);
        Inventory inventory = inventoryRepo.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Invent with id " + dto.getId() + " not found"));
        inventory.setActive(dto.isActive());
        inventoryRepo.save(inventory);

        logService.addLog(userId, Action.UPDATE, Section.INVENT, dto.toString());
    }

    @SneakyThrows
    public InventoryDto getInventById(Long inventId) {
        log.info(TAG + "Get invent by id {}", inventId);
        return inventoryMapper.toDto(inventoryRepo.findById(inventId).orElseThrow(
                () -> new ResourceNotFoundException("Invent with id " + inventId + " not found")));
    }


    @SneakyThrows
    public InventoryDto getCurrentInvent() {
        log.info(TAG + "Get current invent");
        return inventoryMapper.toDto(inventoryRepo.getCurrentInvent(LocalDate.now()).orElseThrow(
                () -> new ResourceNotFoundException("No active invent at this moment")));
    }


}
