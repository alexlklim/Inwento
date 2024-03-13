package com.alex.asset.inventory.mapper;


import com.alex.asset.inventory.domain.Inventory;
import com.alex.asset.inventory.dto.InventoryDto;
import org.springframework.stereotype.Service;

@Service
public class InventoryMapper {


    public InventoryDto toDto(Inventory entity) {
        InventoryDto dto = new InventoryDto();
        dto.setId(entity.getId());
        dto.setStartDate(entity.getStartDate());
        dto.setFinishDate(entity.getFinishDate());
        dto.setFinished(entity.isFinished());
        dto.setInfo(entity.getInfo());
        return dto;
    }
}
