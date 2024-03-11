package com.alex.asset.invents.mapper;

import com.alex.asset.configure.domain.Branch;
import com.alex.asset.configure.repo.BranchRepo;
import com.alex.asset.invents.domain.Event;
import com.alex.asset.invents.domain.Invent;
import com.alex.asset.invents.dto.EventDto;
import com.alex.asset.invents.dto.InventDto;
import com.alex.asset.invents.repo.InventRepo;
import com.alex.asset.product.domain.Product;
import com.alex.asset.product.dto.ProductDto;
import com.alex.asset.product.mappers.ProductMapper;
import com.alex.asset.security.domain.User;
import com.alex.asset.utils.expceptions.errors.ResourceNotFoundException;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EventMapper {

    private final ProductMapper productMapper;
    private final InventRepo inventRepo;
    private final BranchRepo branchRepo;


    public EventDto toDto(Event entity) {
        EventDto dto = new EventDto();
        dto.setId(entity.getId());
        dto.setActive(entity.isActive());
        dto.setInventId(entity.getInvent().getId());
        dto.setUserId(entity.getUser().getId());
        dto.setBranchId(entity.getBranch().getId());
        dto.setProductsOk(entity.getProductsOk().stream().map(productMapper::toDto).toList());
        dto.setProductsShortage(entity.getProductsShortage().stream().map(productMapper::toDto).toList());
        return dto;
    }

    public Event toEntity(EventDto dto, User user) {
        Event entity = new Event();
        entity.setActive(true);
        entity.setInvent(inventRepo.findById(dto.getInventId()).orElseThrow(
                () -> new ResourceNotFoundException("Invent with id " + dto.getInventId() + " not found")
        ));
        entity.setUser(user);
        entity.setBranch(branchRepo.findById(dto.getBranchId()).orElseThrow(
                () -> new ResourceNotFoundException("Branch with id " + dto.getBranchId() + " not found")
        ));
        entity.setProductsOk(new ArrayList<>());
        entity.setProductsShortage(new ArrayList<>());
        return entity;
    }





}
