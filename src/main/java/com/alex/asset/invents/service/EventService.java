package com.alex.asset.invents.service;


import com.alex.asset.invents.dto.EventDto;
import com.alex.asset.invents.mapper.EventMapper;
import com.alex.asset.invents.repo.EventRepo;
import com.alex.asset.invents.repo.InventRepo;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.expceptions.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @SneakyThrows
    public List<EventDto> getMyEventsForCurrentInvent(Long userId, Long inventId) {
        log.info(TAG + "Get all events by user {} for invent with id {} ", userId, inventId);
        return eventRepo.findByUserAndInventOrderByCreatedDesc(
                userRepo.getUser(userId), inventRepo.findById(inventId).orElseThrow(
                        () -> new ResourceNotFoundException("Event with id " + inventId + " not found")))
                .stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }


    public EventDto getEvent(Long eventId) {
        log.info(TAG + "Get invent with id {}", eventId);
        return eventMapper.toDto(eventRepo.findById(eventId).orElseThrow(
                () -> new ResourceNotFoundException("Event with id " + eventId + " not found")));


    }
}
