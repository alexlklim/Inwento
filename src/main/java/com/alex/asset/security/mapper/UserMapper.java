package com.alex.asset.security.mapper;

import com.alex.asset.security.domain.User;
import com.alex.asset.security.domain.dto.UserDto;
import com.alex.asset.security.domain.dto.UserDtoShort;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class UserMapper {

    public static UserDto toDto(User user){
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user, dto);
        dto.setId(user.getId());
        dto.setCompanyId(user.getCompany().getId());
        dto.setCreatedAt(user.getCreated());
        dto.setUpdatedAt(user.getUpdated());
        dto.setCompanyName(user.getCompany().getCompany());
        dto.setRole(List.of(user.getRoles().name()));
        return dto;
    }

    public static UserDtoShort toShortDto(User user){
        UserDtoShort dto = new UserDtoShort();
        BeanUtils.copyProperties(user, dto);
        dto.setEmail(user.getEmail());
        dto.setCompanyName(user.getCompany().getCompany());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        return dto;
    }



}

