package com.alex.asset.company.service;


import com.alex.asset.company.domain.Company;
import com.alex.asset.company.domain.DataDto;
import com.alex.asset.configure.domain.Location;
import com.alex.asset.configure.services.ConfigureService;
import com.alex.asset.configure.services.LocationService;
import com.alex.asset.configure.services.TypeService;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.notification.NotificationService;
import com.alex.asset.notification.domain.Reason;
import com.alex.asset.security.UserMapper;
import com.alex.asset.security.domain.Role;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.Utils;
import com.alex.asset.utils.exceptions.errors.LabelSizeIsIncorrectException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final LocationService locationService;
    private final LogService logService;
    private final NotificationService notificationService;


    @SneakyThrows
    public Map<String, Object> getInfoAboutCompany(List<String> companyFields) {
        log.info(TAG + "Get information about company");
        if (companyFields == null || companyFields.isEmpty()) companyFields = Utils.COMPANY_FIELDS;
        Map<String, Object> map = new HashMap<>();
        if (companyFields.contains("all_configuration")) {
            map.put("all_configuration", getAllFields());
        }
        return CompanyMapper.toDTOWithCustomFields(map, companyRepo.findAll().get(0), companyFields);
    }


    @SneakyThrows
    @Modifying
    @Transactional
    public Map<String, Object> updateCompany(Map<String, Object> updates, Long userId) throws LabelSizeIsIncorrectException {
        log.info(TAG + "Update company by user with id {}", userId);
        Company company = companyRepo.findAll().get(0);
        updates.forEach((key, value) -> {
            switch (key) {
                case "company": company.setCompany((String) value); break;
                case "city": company.setCity((String) value); break;
                case "street": company.setStreet((String) value); break;
                case "zip_code": company.setZipCode((String) value); break;
                case "nip": company.setNip((String) value); break;
                case "regon": company.setRegon((String) value); break;
                case "phone": company.setPhone((String) value); break;
                case "email": company.setEmail((String) value); break;

                case "label_height": {
                    Double labelHeight = (Double) value;
                    if (labelHeight > Double.MIN_VALUE && labelHeight < Double.MAX_VALUE)
                        company.setLabelHeight(labelHeight);
                    else throw new LabelSizeIsIncorrectException("Label height is incorrect: " + labelHeight);
                    break;
                }
                case "label_width": {
                    Double labelWidth = (Double) value;
                    if (labelWidth > Double.MIN_VALUE && labelWidth < Double.MAX_VALUE)
                        company.setLabelWidth(labelWidth);
                    else throw new LabelSizeIsIncorrectException("Label width is incorrect: " + labelWidth);
                    break;
                }
                case "label_type": company.setLabelType((String) value); break;
                case "email_host": company.setHost((String) value); break;
                case "email_port": company.setPort((String) value); break;
                case "email_username": company.setUsername((String) value); break;
                case "email_password": company.setPassword((String) value); break;
                case "email_protocol": company.setProtocol((String) value); break;
                case "email_configured": company.setIsEmailConfigured((boolean) value); break;


                case "rfid_length": company.setRfidLength((Integer) value); break;
                case "rfid_length_max": company.setRfidLengthMax((Integer) value); break;
                case "rfid_length_min": company.setRfidLengthMin((Integer) value); break;
                case "rfid_prefix": company.setRfidPrefix((String) value); break;
                case "rfid_suffix": company.setRfidSuffix((String) value); break;
                case "rfid_postfix": company.setRfidPostfix((String) value); break;
                case "bar_code_length": company.setBarCodeLength((Integer) value); break;
                case "bar_code_length_max": company.setBarCodeLengthMax((Integer) value); break;
                case "bar_code_length_min": company.setBarCodeLengthMin((Integer) value); break;
                case "bar_code_prefix": company.setBarCodePrefix((String) value); break;
                case "bar_code_suffix": company.setBarCodeSuffix((String) value); break;
                case "bar_code_postfix": company.setBarCodePostfix((String) value); break;


                default:
                    break;
            }
        });
        companyRepo.save(company);
        logService.addLog(userId, Action.UPDATE, Section.COMPANY, company.getCompany());
        notificationService.sendSystemNotificationToUsersWithRole(Reason.COMPANY_WAS_UPDATED, Role.ADMIN);
        return getInfoAboutCompany(Utils.COMPANY_FIELDS_SIMPLE);
    }

    @SneakyThrows
    private DataDto getAllFields() {
        log.info(TAG + "Get all fields");
        DataDto dto = new DataDto();
        dto.setEmployees(userRepo.getActiveUsers()
                .stream().map(UserMapper::toEmployee)
                .collect(Collectors.toList()));
        dto.setTypes(typeService.getTypes());
        dto.setUnits(configureService.getUnits());
        dto.setAssetStatuses(configureService.getAssetStatuses());
        dto.setBranches(locationService.getBranches());
        dto.setLocations(convertLocationToData(locationService.getLocations()));
        dto.setMPKs(configureService.getMPKs());
        return dto;
    }

    public DataDto getData() {
        DataDto dto = new DataDto();

        dto.setBranches(locationService.getBranches());
        dto.setLocations(convertLocationToData(locationService.getLocations()));
        dto.setEmployees(userRepo.getActiveUsers()
                .stream().map(UserMapper::toEmployee)
                .collect(Collectors.toList()));

        return dto;
    }


    private List<DataDto.Location> convertLocationToData(List<Location> locations) {
        List<DataDto.Location> list = new ArrayList<>();
        for (Location location : locations) {
            list.add(new DataDto.Location(location.getId(), location.getLocation(), location.getBranch().getId()));
        }

        return list;
    }
}
