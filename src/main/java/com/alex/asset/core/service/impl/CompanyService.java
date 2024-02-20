package com.alex.asset.core.service.impl;


import com.alex.asset.core.domain.Company;
import com.alex.asset.core.dto.CompanyDto;
import com.alex.asset.core.dto.EmployeeDto;
import com.alex.asset.core.dto.FieldsDto;
import com.alex.asset.core.mappers.CompanyMapper;
import com.alex.asset.core.repo.CompanyRepo;
import com.alex.asset.core.service.ConverterService;
import com.alex.asset.security.config.jwt.CustomPrincipal;
import com.alex.asset.security.config.jwt.UserService;
import com.alex.asset.security.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepo companyRepo;
    private final UserService userService;
    private final TypeService typeService;
    private final  FieldService fieldService;


    public CompanyDto getInfo(CustomPrincipal principal) {
        log.info("Get information about company: {}  from user: {}", principal.getComapnyUUID(), principal.getEmail());
        Optional<Company> optionalCompany = companyRepo.findById(principal.getComapnyUUID());
        if (optionalCompany.isEmpty()) return null;
        Company company = optionalCompany.get();
        CompanyDto dto = CompanyMapper.toDto(company);
        User user = userService.getById(company.getOwnerId());
        dto.setOwner(user.getFirstname() + " " + user.getLastname());
        dto.setEmployees(getListOfEmployee(company.getId()));
        return dto;
    }


    public CompanyDto add(CompanyDto dto, CustomPrincipal principal) {
        log.info("Creating company {} for client: {}", dto.getCompany(), principal.getEmail());
        Company company = CompanyMapper.toEntity(dto);
        company.setOwnerId(principal.getUserUUID());
        Company companyFromDB = companyRepo.save(company);
        userService.updateInfoAboutCompany(companyFromDB, principal);
        return CompanyMapper.toDto(companyFromDB);
    }

    public CompanyDto update(CompanyDto dto, CustomPrincipal principal) {
        User user = userService.getById(principal.getUserUUID());
        Optional<Company> optionalCompany = companyRepo.findByOwnerId(user.getId());
        if (optionalCompany.isEmpty()) {
            log.error("User {} isn't the owner of any company", user.getEmail());
            return null;
        }
        Company updatedCompany = CompanyMapper.updateCompany(optionalCompany.get(), dto);
        companyRepo.save(updatedCompany);
        return CompanyMapper.toDto(updatedCompany);
    }

    public boolean makeInactive(CustomPrincipal principal) {
        User user = userService.getById(principal.getUserUUID());
        Optional<Company> optionalCompany = companyRepo.findByOwnerId(user.getId());
        if (optionalCompany.isEmpty()) {
            log.error("User {} isn't the owner of any company", user.getEmail());
            return false;
        }
        Company inactiveCompany = optionalCompany.get();
        inactiveCompany.setActive(false);
        companyRepo.save(inactiveCompany);
        return true;
    }


    public void addUserToEmployee(EmployeeDto dto, CustomPrincipal principal) {
        User user = userService.getById(dto.getId());
        // if user from dto not found return null
        if (user == null) return;
        Company company = companyRepo.findById(principal.getComapnyUUID()).orElse(null);
        // if company doesn't exist return null
        if (company == null) return;
        // if user is active. email from user == email from dto, and user not belong to any company -> add user to emp
        if (user.isEnabled() && dto.getEmail().equals(user.getEmail()) && user.getCompanyUUID() == null) {
            user.setCompanyName(company.getCompany());
            user.setCompanyUUID(company.getId());
            userService.save(user);
            log.info("User {} was added to company {} like employee", user.getEmail(), company.getCompany());
        }
    }


    public FieldsDto getAllFields(UUID companyUUID) {
        Company company = companyRepo.findById(companyUUID).orElse(null);
        if (company == null) return null;
        return new FieldsDto().toBuilder()
                .employees(getListOfEmployee(companyUUID))
                .units(ConverterService.convertUnitsToString(company.getUnits()))
                .assetStatuses(ConverterService.convertAssetStatusesToString(company.getAssetStatus()))
                .ksts(ConverterService.convertKSTToString(company.getKsts()))
                .types(typeService.getTypesMap(companyUUID))
                .branches(fieldService.getBranchNames(company))
                .suppliers(fieldService.getSupplierNames(company))
                .producers(fieldService.getProducerNames(company))
                .mpks(fieldService.getMPKNames(company))
                .productCounter(company.getProductCounter())
                .build();
    }


    private List<String> getListOfEmployee(UUID companyId) {
        List<String> employeeNames = new LinkedList<>();
        for (User user : userService.getByCompanyId(companyId)) {
            employeeNames.add(user.getFirstname() + " " + user.getLastname());
        }
        return employeeNames;
    }


}
