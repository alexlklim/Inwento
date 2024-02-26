package com.alex.asset.core.service;

import com.alex.asset.core.domain.Company;
import com.alex.asset.core.dto.EmployeeDto;
import com.alex.asset.core.repo.CompanyRepo;
import com.alex.asset.security.config.jwt.CustomPrincipal;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.domain.dto.UserDto;
import com.alex.asset.security.domain.dto.UserDtoShort;
import com.alex.asset.security.mapper.UserMapper;
import com.alex.asset.security.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final CompanyRepo companyRepo;


    public void updateInfoAboutCompany(Company company, CustomPrincipal principal) {
        log.info("Information about company for user: {} was updated", principal.getName());
        User user = userRepo.findByEmail(principal.getName()).orElse(null);
        if (user == null) return;
        user.setCompany(company);
        userRepo.save(user);
    }


    public UserDtoShort getEmployeeById(Long id, CustomPrincipal principal) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null) return null;
        return UserMapper.toShortDto(user);
    }

    public UserDto getEmployeeMe(CustomPrincipal principal) {
        User user = userRepo.getUser(principal.getUserId());
        if (user == null) return null;
        return UserMapper.toDto(user);
    }

    public void save(User user) {
        userRepo.save(user);
    }

    @Transactional
    public boolean deleteEmpFromActiveEmp(Long id, CustomPrincipal principal) {
        User user = userRepo.getUser(principal.getUserId());
        if (principal.getCompanyId().equals(user.getCompany().getId())){
            user.setCompany(null);
            userRepo.save(user);
            return true;
        }
        return false;
    }


    public List<EmployeeDto> getEmployeeListFromMyCompany(CustomPrincipal principal) {
        List<EmployeeDto> list = new ArrayList<>();

        Company company = companyRepo.findById(principal.getCompanyId()).orElse(null);
        if (company == null) return null;


        for (User user: userRepo.findByCompany(company)){
            if (user.isEnabled()){
                String name = user.getFirstname() + " " + user.getLastname();
                list.add(new EmployeeDto(user.getId(), user.getEmail(), name));
            }
        }
        return list;

    }
}
