package com.alex.asset.security.domain;

import com.alex.asset.security.dto.RegisterDto;
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
}
