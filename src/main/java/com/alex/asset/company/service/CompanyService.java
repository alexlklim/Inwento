package com.alex.asset.company.service;


import com.alex.asset.company.domain.Company;
import com.alex.asset.company.domain.CompanyDto;
import com.alex.asset.company.domain.DataDto;
import com.alex.asset.configure.services.ConfigureService;
import com.alex.asset.configure.services.TypeService;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.notification.NotificationService;
import com.alex.asset.notification.domain.Reason;
import com.alex.asset.security.UserMapper;
import com.alex.asset.security.domain.Role;
import com.alex.asset.security.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final LogService logService;
    private final NotificationService notificationService;


    public CompanyDto getInfoAboutCompany() {
        log.info(TAG + "Get information about company");
        Company company = companyRepo.findAll().get(0);
        return CompanyMapper.toDto(company);
    }


    @Modifying
    @Transactional
    public CompanyDto updateCompany(CompanyDto dto, Long userId) {
        log.info(TAG + "Update company by user with id {}", userId);
        Company updatedCompany = CompanyMapper.updateCompany(companyRepo.findAll().get(0), dto);
        logService.addLog(userId, Action.UPDATE, Section.COMPANY, dto.toString());
        notificationService.sendSystemNotificationToUsersWithRole(Reason.COMPANY_WAS_UPDATED, Role.ADMIN);
        return CompanyMapper.toDto(companyRepo.save(updatedCompany));
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


}
