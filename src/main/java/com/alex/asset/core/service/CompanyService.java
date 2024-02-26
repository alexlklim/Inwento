package com.alex.asset.core.service;


import com.alex.asset.core.domain.Company;
import com.alex.asset.core.dto.CompanyDto;
import com.alex.asset.core.dto.EmployeeDto;
import com.alex.asset.core.dto.FieldsDto;
import com.alex.asset.core.mappers.CompanyMapper;
import com.alex.asset.core.repo.CompanyRepo;
import com.alex.asset.security.config.jwt.CustomPrincipal;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {
    private final String TAG = "COMPANY_SERVICE - ";
    
    private final TypeService typeService;
    private final  FieldService fieldService;
    private final UserRepo userRepo;
    private final CompanyRepo companyRepo;



    @Transactional
    public CompanyDto getInfoAboutCompany(Long companyId, Long userId) {
        log.info(TAG + "Get information about company from user");
        Company company = companyRepo.getCompany(companyId);
        CompanyDto dto = CompanyMapper.toDto(company);
        User user = company.getOwner();
        dto.setOwner(user.getFirstname() + " " + user.getLastname());
        dto.setEmployees(getListOfEmployeeForCompany(company));
        dto.setSecretCode(company.getSecretCode());
        return dto;
    }

    @Transactional
    public CompanyDto update(CompanyDto dto, CustomPrincipal principal) {
        log.info(TAG + "Updating company {} for client: {}", dto.getCompany(), principal.getName());
        User user = userRepo.getUser(principal.getUserId());

        Optional<Company> optionalCompany = companyRepo.findByOwner(user);
        if (optionalCompany.isEmpty()) {
            log.error(TAG + "User {} isn't the owner of any company", user.getEmail());
            return null;
        }
        Company updatedCompany = CompanyMapper.updateCompany(optionalCompany.get(), dto);
        companyRepo.save(updatedCompany);
        return CompanyMapper.toDto(updatedCompany);
    }




    @Transactional
    public CompanyDto addCompanyForUser(Long userId, CompanyDto dto) {
        Company company = CompanyMapper.toEntity(dto);
        User user = userRepo.getUser(userId);
        company.setOwner(user);
        company.setActive(true);
        Company companyFromDB = companyRepo.save(company);
        user.setCompany(companyFromDB);
        userRepo.save(user);
        return CompanyMapper.toDto(companyFromDB);
    }



    public boolean makeInactive(CustomPrincipal principal) {
        log.warn(TAG + "make inactive company {}", principal.getCompanyId());
        Company company = companyRepo.getCompany(principal.getCompanyId());

        company.setActive(false);
        companyRepo.save(company);
        return true;
    }


    public void addUserToEmployee(EmployeeDto dto, CustomPrincipal principal) {
        User user = userRepo.getUser(principal.getUserId());
        Company company = companyRepo.getCompany(principal.getCompanyId());
        if (user.isEnabled() && dto.getEmail().equals(user.getEmail()) && user.getCompany() == null) {
            user.setCompany(company);
            userRepo.save(user);
        }
    }
    

    public FieldsDto getAllFields(CustomPrincipal principal) {
        log.info(TAG + "get all fields for company ");

        Company company = companyRepo.getCompany(principal.getCompanyId());
        if (company == null) return null;
        return new FieldsDto().toBuilder()
                .employees(getListOfEmployeeForCompany(company))
                .units(ConverterService.convertUnitsToString(company.getUnits()))
                .assetStatuses(ConverterService.convertAssetStatusesToString(company.getAssetStatus()))
                .ksts(ConverterService.convertKSTToString(company.getKsts()))
                .types(typeService.getTypesMap(company.getId()))
                .branches(fieldService.getBranchNames(company))
                .suppliers(fieldService.getSupplierNames(company))
                .producers(fieldService.getProducerNames(company))
                .mpks(fieldService.getMPKNames(company))
                .build();
    }


    private List<String> getListOfEmployeeForCompany(Company company) {
        log.info(TAG + "get list of employee for company");
        List<String> employeeNames = new LinkedList<>();
        for (User user : userRepo.findByCompany(company)) {
            employeeNames.add(user.getFirstname() + " " + user.getLastname());
        }
        return employeeNames;
    }


}
