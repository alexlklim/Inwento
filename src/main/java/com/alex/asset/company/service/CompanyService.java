package com.alex.asset.company.service;


import com.alex.asset.company.domain.*;
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
    public CompanyDto updateCompany(CompanyDto companyDto, Long userId) {
        log.info(TAG + "Update company by user with id {}", userId);
        Company updatedCompany = CompanyMapper.updateCompany(companyRepo.findAll().get(0), companyDto);
        logService.addLog(userId, Action.UPDATE, Section.COMPANY, companyDto.toString());
        notificationService.sendSystemNotificationToUsersWithRole(Reason.COMPANY_WAS_UPDATED, Role.ADMIN);
        return CompanyMapper.toDto(companyRepo.save(updatedCompany));
    }


    public DataDto getAllFields() {
        log.info(TAG + "Get all fields");
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


    public LabelDto getLabelConfig() {
        log.info(TAG + "Get label info");
        Company company = companyRepo.findAll().get(0);
        LabelDto labelDto = new LabelDto();
        labelDto.setLabelHeight(company.getLabelHeight());
        labelDto.setLabelWidth(company.getLabelWidth());
        labelDto.setLabelType(company.getLabelType());
        return labelDto;
    }

    public void updateLabelConfig(Long userId, LabelDto labelDto) {
        log.info(TAG + "Update label info by user wid id {}", userId);
        Company company = companyRepo.findAll().get(0);
        company.setLabelHeight(labelDto.getLabelHeight());
        company.setLabelWidth(labelDto.getLabelWidth());
        company.setLabelType(labelDto.getLabelType());
        companyRepo.save(company);
        logService.addLog(userId, Action.UPDATE, Section.COMPANY, labelDto.toString());
    }


    public EmailDto getEmailConfig() {
        log.info(TAG + "Get email info");
        Company company = companyRepo.findAll().get(0);
        return new EmailDto().toBuilder()
                .host(company.getHost())
                .port(company.getPort())
                .protocol(company.getProtocol())
                .username(company.getUsername())
                .password(company.getPassword())
                .isEmailConfigured(company.getIsEmailConfigured())
                .build();
    }

    public void updateEmailConfig(Long userId, EmailDto emailDto) {
        log.info(TAG + "Update email info by user wid id {}", userId);
        Company company = companyRepo.findAll().get(0);
        company.setHost(emailDto.getHost());
        company.setPort(emailDto.getPort());
        company.setUsername(emailDto.getUsername());
        company.setPassword(emailDto.getPassword());
        company.setProtocol(emailDto.getProtocol());
        company.setIsEmailConfigured(true);
        companyRepo.save(company);

        logService.addLog(userId, Action.UPDATE, Section.COMPANY, emailDto.toString());

    }


}
