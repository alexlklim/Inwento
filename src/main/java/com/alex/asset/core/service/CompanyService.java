package com.alex.asset.core.service;


import com.alex.asset.core.domain.Company;
import com.alex.asset.core.dto.CompanyDto;
import com.alex.asset.core.dto.DataDto;
import com.alex.asset.core.dto.simple.ActiveDto;
import com.alex.asset.core.mappers.CompanyMapper;
import com.alex.asset.core.mappers.UserMapper;
import com.alex.asset.core.repo.CompanyRepo;
import com.alex.asset.security.domain.dto.UserDto;
import com.alex.asset.security.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {
    private final String TAG = "COMPANY_SERVICE - ";
    private final UserRepo userRepo;
    private final CompanyRepo companyRepo;

    private final TypeService typeService;
    private final ConfigureService configureService;




    public CompanyDto getInfoAboutCompany() {
        log.info(TAG + "Get information about company");
        Company company = companyRepo.findAll().get(0);
        return CompanyMapper.toDto(company);
    }

    private List<UserDto> getActiveEmployee() {
        return userRepo.getActiveUsers()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Modifying
    @Transactional
    public CompanyDto updateCompany(CompanyDto dto) {
        log.info(TAG + "Update company");
        Company updatedCompany = CompanyMapper.updateCompany(companyRepo.findAll().get(0), dto);
        return CompanyMapper.toDto(companyRepo.save(updatedCompany));
    }


    public void deleteUser(String email) {
        userRepo.delete(Objects.requireNonNull(userRepo.getUser(email)));
    }

    public UserDto getInfoAboutEmpById(Long id) {
        return UserMapper.toDTO(userRepo.getUser(id));
    }

    public List<UserDto> getAllEmployee(){
        return userRepo.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());

    }

    public DataDto getAllFields() {
        DataDto dto = new DataDto();
        dto.setEmployees(userRepo.getActiveUsers()
                .stream().map(UserMapper::toEmployee)
                .collect(Collectors.toList()));
        dto.setTypes(typeService.getTypes());
        dto.setUnits(configureService.getUnits());
        dto.setAssetStatuses(configureService.getAssetStatuses());
        dto.setBranches(configureService.getBranches());
        dto.setMPKs(configureService.getMPKs());
        return dto;
    }

    @Modifying
    @Transactional
    public boolean changeUserVisibility(ActiveDto dto) {
        if (checkIfRoleADMIN(dto.getId())) {
            log.error(TAG + "User which you trying to change visibility is ADMIN");
            return false;
        }
        userRepo.updateVisibility(dto.isActive(), dto.getId());
        return true;
    }

    private boolean checkIfRoleADMIN(Long id) {
        return userRepo.checkIfRole("ADMIN", id);
    }


}
