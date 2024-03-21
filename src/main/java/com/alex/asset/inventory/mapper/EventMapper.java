package com.alex.asset.inventory.mapper;

import com.alex.asset.inventory.domain.event.Event;
import com.alex.asset.inventory.dto.EventV2Get;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EventMapper {

    public EventV2Get toDto(Event entity) {
        EventV2Get dto = new EventV2Get();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUser().getFirstname() + " " + entity.getUser().getLastname());
        dto.setEmail(entity.getUser().getEmail());
        dto.setBranch(entity.getBranch().getBranch());
        return dto;
    }


}
