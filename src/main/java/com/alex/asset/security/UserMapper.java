package com.alex.asset.security;

import com.alex.asset.company.domain.DataDto;
import com.alex.asset.security.domain.Role;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.dto.RegisterDto;
import com.alex.asset.security.dto.UserDto;
import com.alex.asset.utils.DateService;
import lombok.Data;

@Data
public class UserMapper {

    public static User toUser(RegisterDto dto) {
        User user = new User();
        user.setFirstname(dto.getFirstName());
        user.setLastname(dto.getLastName());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setRoles(Role.fromString(dto.getRole()));
        user.setActive(true);
        user.setLastActivity(DateService.getDateNow());
        return user;
    }


    public static UserDto toDto(User entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstname());
        dto.setLastName(entity.getLastname());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setActive(entity.isActive());
        dto.setRole(entity.getRoles().name());
        dto.setLastActivity(DateService.getDateNow());
        dto.setLastActivity(entity.getLastActivity());
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
}
