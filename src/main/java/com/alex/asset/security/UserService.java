package com.alex.asset.security;

import com.alex.asset.security.domain.Role;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.dto.UserDto;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.dto.DtoActive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final String TAG = "USER_SERVICE - ";
    private final UserRepo userRepo;


    public UserDto getInfoAboutUserById(Long id) {
        return UserMapper.toDto(userRepo.getUser(id));
    }

    public List<UserDto> getAllUsers() {
        log.info(TAG + "Get all users");
        return userRepo.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());

    }


    @Modifying
    @Transactional
    public boolean changeUserVisibility(DtoActive dto) {
        log.info(TAG + "Change user visibility");
        userRepo.updateVisibility(dto.isActive(), dto.getId());
        return true;
    }






    public UserDto updateUser(Long id, UserDto dto) {
        log.error(TAG + "Update user with id {}", id);
        User user = userRepo.getUser(id);
        user.setFirstname(dto.getFirstName());
        user.setLastname(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setRoles(Role.fromString(dto.getRole()));
        return UserMapper.toDto(userRepo.save(user));

    }
}
