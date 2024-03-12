package com.alex.asset.invents.mapper;


import com.alex.asset.invents.domain.Invent;
import com.alex.asset.invents.dto.InventDto;
import org.springframework.stereotype.Service;

@Service
public class InventMapper {


    public InventDto toDto(Invent entity) {
        InventDto dto = new InventDto();
        dto.setId(entity.getId());
        dto.setStartDate(entity.getStartDate());
        dto.setFinishDate(entity.getFinishDate());
        dto.setFinished(entity.isFinished());
        dto.setInfo(entity.getInfo());
        return dto;
    }
}
