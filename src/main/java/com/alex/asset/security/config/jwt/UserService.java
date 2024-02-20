package com.alex.asset.security.config.jwt;

import com.alex.asset.core.domain.Company;
import com.alex.asset.core.dto.EmployeeDto;
import com.alex.asset.email.EmailService;
import com.alex.asset.security.domain.Role;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.domain.dto.PasswordDto;
import com.alex.asset.security.domain.dto.RegisterDto;
import com.alex.asset.security.domain.dto.UserDto;
import com.alex.asset.security.domain.dto.UserDtoShort;
import com.alex.asset.security.mapper.UserMapper;
import com.alex.asset.security.repo.UserRepository;
import com.alex.asset.utils.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final UserRepository userRepo;
    private final EmailService emailService;


    public boolean register(RegisterDto request) {
        log.info("Try to register user with email: {}", request.getEmail());
        try {
            User user = User.builder()
                    .firstname(request.getFirstName())
                    .lastname(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(Role.CLIENT)
                    .isEnabled(true)
                    .build();
            userRepo.save(user);
            log.info("User with email {} was successfully created", request.getEmail());
            emailService.accountWasCreated(user);
            return true;
        } catch (DataIntegrityViolationException e) {
            log.error(ErrorStatus.USER_ALREADY_EXISTS.name());
            log.error("User with email {} is already exists in DB", request.getEmail());
            return false;
        }
    }



    public boolean changePassword(PasswordDto dto, CustomPrincipal principal) {
        log.info("Try to change password for user with email: {}", principal.getEmail());
        User user = userRepo.findByEmail(principal.getEmail()).orElse(null);
        assert user != null;
        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) return false;
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        repository.save(user);
        return true;
    }


    public void changePasswordForgot(String email, String password) {
        log.info("Change password for user: {}", email);
        User user = userRepo.findByEmail(email).orElse(null);
        if (user == null) return;
        user.setPassword(passwordEncoder.encode(password));
        repository.save(user);
        emailService.passwordWasChanged(user.getEmail());
    }




    public boolean existsByEmail(String email){
        return repository.existsByEmail(email);
    }

    public User getByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    public User getById(UUID userId) {
        return userRepo.findById(userId).orElse(null);
        }

    public List<User> getByCompanyId(UUID id) {
        return userRepo.findByCompanyUUID(id);
    }

    public void updateInfoAboutCompany(Company company, CustomPrincipal principal) {
        log.info("Information about company for user: {} was updated", principal.getEmail());
        User user = getById(principal.getUserUUID());
        user.setCompanyUUID(company.getId());
        user.setCompanyName(company.getCompany());
        userRepo.save(user);
    }

    public List<EmployeeDto> getEmployeeList(UUID comapnyUUID) {
        List<EmployeeDto> list = new ArrayList<>();
        for (User user: getByCompanyId(comapnyUUID)){
            if (user.isEnabled()){
                String name = user.getFirstname() + " " + user.getLastname();
                list.add(new EmployeeDto(user.getId(), user.getEmail(), name));
            }
        }
        return list;

    }

    public UserDtoShort getEmployeeById(UUID id, CustomPrincipal principal) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null) return null;
        if (!user.getCompanyUUID().equals(principal.getComapnyUUID())) return null;
        return UserMapper.toShortDto(user);
    }

    public UserDto getEmployeeMe(CustomPrincipal principal) {
        User user = userRepo.findById(principal.getUserUUID()).orElse(null);
        if (user == null) return null;
        return UserMapper.toDto(user);
    }

    public void save(User user) {
        userRepo.save(user);
    }

    @Transactional
    public boolean deleteEmpFromActiveEmp(UUID id, CustomPrincipal principal) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null) return false;
        if (principal.getComapnyUUID().equals(user.getCompanyUUID())){
            user.setCompanyName(null);
            user.setCompanyUUID(null);
            userRepo.save(user);
            return true;
        }
        return false;
    }
}
