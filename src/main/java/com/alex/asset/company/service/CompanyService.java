package com.alex.asset.company.service;


import com.alex.asset.company.domain.Company;
import com.alex.asset.company.domain.DataDto;
import com.alex.asset.configure.mappers.ConfigureMapper;
import com.alex.asset.configure.services.LocationService;
import com.alex.asset.exceptions.company.LabelSizeIsIncorrectException;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.notification.NotificationService;
import com.alex.asset.notification.domain.Reason;
import com.alex.asset.security.UserMapper;
import com.alex.asset.security.domain.Role;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.dictionaries.UtilCompany;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {
    private final String TAG = "COMPANY_SERVICE - ";
    private final LocationService locationService;
    private final LogService logService;
    private final NotificationService notificationService;
    private final UserRepo userRepo;
    private final CompanyRepo companyRepo;


    @SneakyThrows
    public Map<String, Object> getInfoAboutCompany(List<String> fields) {
        log.info(TAG + "getInfoAboutCompany");
        Map<String, Object> map = new HashMap<>();
        return CompanyMapper.toDTOWithCustomFields(
                map,
                companyRepo.findAll().get(0),
                fields.isEmpty() ? UtilCompany.getAll() : fields);
    }


    @SneakyThrows
    @Modifying
    @Transactional
    public void updateCompany(Map<String, Object> updates, Long userId) throws LabelSizeIsIncorrectException {
        log.info(TAG + "updateCompany userId {}", userId);
        Company company = companyRepo.findAll().get(0);
        updates.forEach((key, value) -> {
            switch (key) {
                case UtilCompany.COMPANY:
                    company.setCompany((String) value);
                    break;
                case UtilCompany.CITY:
                    company.setCity((String) value);
                    break;
                case UtilCompany.STREET:
                    company.setStreet((String) value);
                    break;
                case UtilCompany.ZIP_CODE:
                    company.setZipCode((String) value);
                    break;
                case UtilCompany.NIP:
                    company.setNip((String) value);
                    break;
                case UtilCompany.REGON:
                    company.setRegon((String) value);
                    break;
                case UtilCompany.PHONE:
                    company.setPhone((String) value);
                    break;
                case UtilCompany.EMAIL:
                    company.setEmail((String) value);
                    break;

                case UtilCompany.LABEL_HEIGHT: {
                    Double labelHeight = (Double) value;
                    if (labelHeight > Double.MIN_VALUE && labelHeight < Double.MAX_VALUE)
                        company.setLabelHeight(labelHeight);
                    else throw new LabelSizeIsIncorrectException("Label height is incorrect: " + labelHeight);
                    break;
                }
                case UtilCompany.LABEL_WIDTH: {
                    Double labelWidth = (Double) value;
                    if (labelWidth > Double.MIN_VALUE && labelWidth < Double.MAX_VALUE)
                        company.setLabelWidth(labelWidth);
                    else throw new LabelSizeIsIncorrectException("Label width is incorrect: " + labelWidth);
                    break;
                }
                case UtilCompany.LABEL_TYPE:
                    company.setLabelType((String) value);
                    break;
                case UtilCompany.EMAIL_HOST:
                    company.setHost((String) value);
                    break;
                case UtilCompany.EMAIL_PORT:
                    company.setPort((String) value);
                    break;
                case UtilCompany.EMAIL_USERNAME:
                    company.setUsername((String) value);
                    break;
                case UtilCompany.EMAIL_PASSWORD:
                    company.setPassword((String) value);
                    break;
                case UtilCompany.EMAIL_PROTOCOL:
                    company.setProtocol((String) value);
                    break;
                case UtilCompany.EMAIL_CONFIGURED:
                    company.setIsEmailConfigured((boolean) value);
                    break;

                case UtilCompany.RFID_LENGTH:
                    company.setRfidCodeLength((Integer) value);
                    break;
                case UtilCompany.RFID_LENGTH_MAX:
                    company.setRfidCodeLengthMax((Integer) value);
                    break;
                case UtilCompany.RFID_LENGTH_MIN:
                    company.setRfidCodeLengthMin((Integer) value);
                    break;
                case UtilCompany.RFID_PREFIX:
                    company.setRfidCodePrefix((String) value);
                    break;
                case UtilCompany.RFID_SUFFIX:
                    company.setRfidCodeSuffix((String) value);
                    break;
                case UtilCompany.RFID_POSTFIX:
                    company.setRfidCodePostfix((String) value);
                    break;
                case UtilCompany.BARCODE_LENGTH:
                    company.setBarCodeLength((Integer) value);
                    break;
                case UtilCompany.BARCODE_LENGTH_MAX:
                    company.setBarCodeLengthMax((Integer) value);
                    break;
                case UtilCompany.BARCODE_LENGTH_MIN:
                    company.setBarCodeLengthMin((Integer) value);
                    break;
                case UtilCompany.BARCODE_PREFIX:
                    company.setBarCodePrefix((String) value);
                    break;
                case UtilCompany.BARCODE_SUFFIX:
                    company.setBarCodeSuffix((String) value);
                    break;
                case UtilCompany.BARCODE_POSTFIX:
                    company.setBarCodePostfix((String) value);
                    break;

                default:
                    break;
            }
        });
        companyRepo.save(company);
        logService.addLog(userId, Action.UPDATE, Section.COMPANY, company.getCompany());
        notificationService.sendSystemNotificationToUsersWithRole(Reason.COMPANY_WAS_UPDATED, Role.ADMIN);
    }


    public DataDto getData() {
        DataDto dto = new DataDto();
        dto.setBranches(ConfigureMapper.convertBranchToDTOs(locationService.getBranches()));
        dto.setLocations(ConfigureMapper.convertLocationToDTOs(locationService.getLocations()));
        dto.setEmployees(userRepo.getActiveUsers()
                .stream().map(UserMapper::toEmployee)
                .collect(Collectors.toList()));
        return dto;
    }

}
