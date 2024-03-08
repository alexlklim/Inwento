package com.alex.asset.company.repo;

import com.alex.asset.company.dto.DataDto;
import com.alex.asset.company.dto.EmpDto;
import com.alex.asset.security.domain.User;
import com.alex.asset.company.dto.UserDto;

import java.util.List;

public class UserMapper {

    public static UserDto toDTO(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstname());
        dto.setLastName(user.getLastname());
        dto.setPhone(user.getPhone());
        dto.setActive(user.isActive());
        dto.setLastActivity(user.getLastActivity());
        dto.setCreatedAt(user.getCreated());
        dto.setUpdatedAt(user.getUpdated());
        dto.setRole(List.of(user.getRoles().name()));
        return dto;
    }

    public static DataDto.Employee toEmployee(User user) {
        DataDto.Employee dto = new DataDto.Employee();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstname());
        dto.setLastName(user.getLastname());
        return dto;
    }



    public static EmpDto toEmpDTO(User user) {
        EmpDto dto = new EmpDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstname());
        dto.setLastName(user.getLastname());
        dto.setPhone(user.getPhone());
        dto.setActive(user.isActive());
        dto.setLastActivity(user.getLastActivity());
        dto.setRole(List.of(user.getRoles().name()));
        return dto;
    }


}
