package com.alex.asset.company.service;

import com.alex.asset.company.domain.Company;
import com.alex.asset.utils.dictionaries.UtilCompany;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CompanyMapper {
    public static Map<String, Object> toDTOWithCustomFields(
            Map<String, Object> dtoMap, Company company, List<String> fields
    ) {
        Map<String, Supplier<Object>> dataFetchers = new HashMap<>();
        dataFetchers.put(UtilCompany.COMPANY, company::getCompany);
        dataFetchers.put(UtilCompany.CITY, company::getCity);
        dataFetchers.put(UtilCompany.STREET, company::getStreet);
        dataFetchers.put(UtilCompany.ZIP_CODE, company::getZipCode);
        dataFetchers.put(UtilCompany.NIP, company::getNip);
        dataFetchers.put(UtilCompany.REGON, company::getRegon);
        dataFetchers.put(UtilCompany.PHONE, company::getPhone);
        dataFetchers.put(UtilCompany.EMAIL, company::getEmail);
        dataFetchers.put(UtilCompany.LABEL_WIDTH, company::getLabelWidth);
        dataFetchers.put(UtilCompany.LABEL_HEIGHT, company::getLabelHeight);
        dataFetchers.put(UtilCompany.LABEL_TYPE, company::getLabelType);
        dataFetchers.put(UtilCompany.EMAIL_HOST, company::getHost);
        dataFetchers.put(UtilCompany.EMAIL_PORT, company::getPort);
        dataFetchers.put(UtilCompany.EMAIL_USERNAME, company::getUsername);
        dataFetchers.put(UtilCompany.EMAIL_PASSWORD, company::getPassword);
        dataFetchers.put(UtilCompany.EMAIL_PROTOCOL, company::getProtocol);
        dataFetchers.put(UtilCompany.EMAIL_CONFIGURED, company::getIsEmailConfigured);
        dataFetchers.put(UtilCompany.BAR_CODE_LENGTH, company::getBarCodeLength);
        dataFetchers.put(UtilCompany.BAR_CODE_LENGTH_MAX, company::getBarCodeLengthMax);
        dataFetchers.put(UtilCompany.BAR_CODE_LENGTH_MIN, company::getBarCodeLengthMin);
        dataFetchers.put(UtilCompany.BAR_CODE_PREFIX, company::getBarCodePrefix);
        dataFetchers.put(UtilCompany.BAR_CODE_SUFFIX, company::getBarCodeSuffix);
        dataFetchers.put(UtilCompany.BAR_CODE_POSTFIX, company::getBarCodePostfix);
        dataFetchers.put(UtilCompany.RFID_LENGTH, company::getRfidCodeLength);
        dataFetchers.put(UtilCompany.RFID_LENGTH_MAX, company::getRfidCodeLengthMax);
        dataFetchers.put(UtilCompany.RFID_LENGTH_MIN, company::getRfidCodeLengthMin);
        dataFetchers.put(UtilCompany.RFID_PREFIX, company::getRfidCodePrefix);
        dataFetchers.put(UtilCompany.RFID_SUFFIX, company::getRfidCodeSuffix);
        dataFetchers.put(UtilCompany.RFID_POSTFIX, company::getRfidCodePostfix);

        fields.forEach(field -> dtoMap.put(field, dataFetchers.getOrDefault(field, () -> "").get()));
        return dtoMap;
    }


}
