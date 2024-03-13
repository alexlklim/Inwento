package com.alex.asset.invents.service;


import com.alex.asset.configure.repo.BranchRepo;
import com.alex.asset.invents.domain.event.Event;
import com.alex.asset.invents.dto.EventV1Create;
import com.alex.asset.invents.dto.EventV2Get;
import com.alex.asset.invents.mapper.EventMapper;
import com.alex.asset.invents.repo.EventRepo;
import com.alex.asset.invents.repo.InventRepo;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.dto.DtoActive;
import com.alex.asset.utils.expceptions.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EventService {
    private final String TAG = "EVENT_SERVICE - ";

    private final EventMapper eventMapper;
    private final EventRepo eventRepo;
    private final UserRepo userRepo;
    private final InventRepo inventRepo;
    private final BranchRepo branchRepo;

//    private final ProductRepo productRepo;




    public EventV2Get getEvent(Long eventId) {
        log.info(TAG + "Get invent with id {}", eventId);
        return eventMapper.toDto(eventRepo.findById(eventId).orElseThrow(
                () -> new ResourceNotFoundException("Event with id " + eventId + " not found")));


    }

    @SneakyThrows
    public List<EventV2Get> getEventsForSpecificUserAndInvent(Long userId, Long inventId) {
        log.info(TAG + "Get event for user with id {} and invent with id {}", userId, inventId);
        return eventRepo.findAllByUserAndInventOrderByCreatedDesc(userRepo.getUser(userId), inventRepo.findById(inventId).orElseThrow(
                        () -> new ResourceNotFoundException("Invent with id " + inventId + " not found")))
                .stream()
                .map(eventMapper::toDto)
                .toList();

    }

    @SneakyThrows
    public List<EventV2Get> getAllEventsForSpecificInvent(Long inventId) {
        log.info(TAG + "Get event for invent with id {}", inventId);
        return eventRepo.findAllByInventOrderByCreatedDesc(inventRepo.findById(inventId).orElseThrow(
                () -> new ResourceNotFoundException("Invent with id " + inventId + " not found")))
                .stream()
                .map(eventMapper::toDto)
                .toList();


    }

    @SneakyThrows
    public EventV2Get createEvent(Long userId, EventV1Create dto) {
        log.info(TAG + "Create event by user with id {}", userId);
        Event event = new Event();
        event.setActive(true);
        event.setInvent(inventRepo.getCurrentInvent(LocalDate.now()).orElseThrow(
                () -> new ResourceNotFoundException("No active event at this time")));
        event.setUser(userRepo.getUser(userId));
        event.setBranch(branchRepo.findById(dto.getBranchId()).orElseThrow(
                () -> new ResourceNotFoundException("Branch with id " + dto.getBranchId() + " not found")));
        event.setInfo(dto.getInfo());
        event.setProducts(new ArrayList<>());
        event.setUnknownProducts(new ArrayList<>());
        return eventMapper.toDto(eventRepo.save(event));
    }


    @SneakyThrows
    public void updateVisibility(Long userId, DtoActive dto) {
        log.info(TAG + "Update visibility of event by user with id {} for event with id {}", userId, dto.getId());
        Event event = eventRepo.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Event with this id not found"));
        event.setActive(dto.isActive());
        eventRepo.save(event);

    }



    @SneakyThrows
    public void addProductsToEventByBarCode(Long userId, Long eventId, List<String> list) {
//        log.info(TAG + "Add products to event by bar code by user with id {}", userId);
//        Event event = eventRepo.findById(eventId).orElseThrow(() -> new ResourceNotFoundException("Event not found"));
//
//        for (String barCode : list){
//            Product product = productRepo.findByBarCodeAndActive(barCode, true)
//                    .orElse(null);
//            if (product != null){
//                event.getProducts().add(product);
//            } else {
//                event.getUnknownProducts().add(barCode);
//            }
//        }




    }
}
