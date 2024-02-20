package com.alex.asset.security.mapper;

import com.alex.asset.security.domain.User;
import com.alex.asset.security.domain.dto.UserDto;
import com.alex.asset.security.domain.dto.UserDtoShort;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class UserMapper {

    public static UserDto toDto(User user){
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user, dto);
        dto.setUuid(user.getId());
        dto.setCompanyId(user.getCompanyUUID());
        dto.setCreatedAt(user.getCreated());
        dto.setUpdatedAt(user.getUpdated());
        dto.setCompanyName(user.getCompanyName());
        dto.setRole(List.of(user.getRoles().name()));
        return dto;
    }

    public static UserDtoShort toShortDto(User user){
        UserDtoShort dto = new UserDtoShort();
        BeanUtils.copyProperties(user, dto);
        dto.setEmail(user.getEmail());
        dto.setCompanyName(user.getCompanyName());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        return dto;
    }


    public static User toEntity(UserDto dto){


        return null;
    }
}

