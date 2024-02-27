package com.alex.asset.core.service;


import com.alex.asset.core.domain.Company;
import com.alex.asset.core.domain.fields.Branch;
import com.alex.asset.core.domain.fields.MPK;
import com.alex.asset.core.domain.fields.constants.AssetStatus;
import com.alex.asset.core.domain.fields.constants.Unit;
import com.alex.asset.core.dto.CompanyDto;
import com.alex.asset.core.dto.DataDto;
import com.alex.asset.core.mappers.CompanyMapper;
import com.alex.asset.core.mappers.UserMapper;
import com.alex.asset.core.repo.CompanyRepo;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.domain.dto.UserDto;
import com.alex.asset.security.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private final FieldService fieldService;




    public CompanyDto getInfoAboutCompany() {
        log.info(TAG + "Get information about company");
        Company company = companyRepo.findAll().get(0);
        return CompanyMapper.toDto(company, getActiveEmployee());
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
        return CompanyMapper.toDto(companyRepo.save(updatedCompany), getActiveEmployee());
    }


    @Modifying
    @Transactional
    public void makeUserInactive(String email){
        userRepo.makeUserInactive(userRepo.getUser(email));
    }

    @Modifying
    @Transactional
    public void makeUserActive(String email){
        userRepo.makeUserActive(userRepo.getUser(email));
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
        dto.setUnits(fieldService.getUnits());
        dto.setAssetStatuses(fieldService.getAssetStatuses());
        dto.setBranches(fieldService.getBranches());
        dto.setMPKs(fieldService.getMPKs());
        return dto;
    }
}
