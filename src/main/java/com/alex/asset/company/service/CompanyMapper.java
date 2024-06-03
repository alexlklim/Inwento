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
        Map<String, Supplier<Object>> df = new HashMap<>();

        df.put(UtilCompany.COMPANY, () -> company.getCompany() != null ? company.getCompany() : "");
        df.put(UtilCompany.CITY, () -> company.getCity() != null ? company.getCity() : "");
        df.put(UtilCompany.STREET, () -> company.getStreet() != null ? company.getStreet() : "");
        df.put(UtilCompany.ZIP_CODE, () -> company.getZipCode() != null ? company.getZipCode() : "");
        df.put(UtilCompany.NIP, () -> company.getNip() != null ? company.getNip() : "");
        df.put(UtilCompany.REGON, () -> company.getRegon() != null ? company.getRegon() : "");
        df.put(UtilCompany.PHONE, () -> company.getPhone() != null ? company.getPhone() : "");
        df.put(UtilCompany.EMAIL, () -> company.getEmail() != null ? company.getEmail() : "");
        df.put(UtilCompany.LABEL_WIDTH, () -> company.getLabelWidth() != null ? company.getLabelWidth() : "");
        df.put(UtilCompany.LABEL_HEIGHT, () -> company.getLabelHeight() != null ? company.getLabelHeight() : "");
        df.put(UtilCompany.LABEL_TYPE, () -> company.getLabelType() != null ? company.getLabelType() : "");
        df.put(UtilCompany.EMAIL_HOST, () -> company.getHost() != null ? company.getHost() : "");
        df.put(UtilCompany.EMAIL_PORT, () -> company.getPort() != null ? company.getPort() : "");
        df.put(UtilCompany.EMAIL_USERNAME, () -> company.getUsername() != null ? company.getUsername() : "");
        df.put(UtilCompany.EMAIL_PASSWORD, () -> company.getPassword() != null ? company.getPassword() : "");
        df.put(UtilCompany.EMAIL_PROTOCOL, () -> company.getProtocol() != null ? company.getProtocol() : "");
        df.put(UtilCompany.EMAIL_CONFIGURED, () -> company.getIsEmailConfigured() != null ? company.getIsEmailConfigured() : "");
        df.put(UtilCompany.BAR_CODE_LENGTH, () -> company.getBarCodeLength() != null ? company.getBarCodeLength() : "");
        df.put(UtilCompany.BAR_CODE_LENGTH_MAX, () -> company.getBarCodeLengthMax() != null ? company.getBarCodeLengthMax() : "");
        df.put(UtilCompany.BAR_CODE_LENGTH_MIN, () -> company.getBarCodeLengthMin() != null ? company.getBarCodeLengthMin() : "");
        df.put(UtilCompany.BAR_CODE_PREFIX, () -> company.getBarCodePrefix() != null ? company.getBarCodePrefix() : "");
        df.put(UtilCompany.BAR_CODE_SUFFIX, () -> company.getBarCodeSuffix() != null ? company.getBarCodeSuffix() : "");
        df.put(UtilCompany.BAR_CODE_POSTFIX, () -> company.getBarCodePostfix() != null ? company.getBarCodePostfix() : "");
        df.put(UtilCompany.RFID_LENGTH, () -> company.getRfidCodeLength() != null ? company.getRfidCodeLength() : "");
        df.put(UtilCompany.RFID_LENGTH_MAX, () -> company.getRfidCodeLengthMax() != null ? company.getRfidCodeLengthMax() : "");
        df.put(UtilCompany.RFID_LENGTH_MIN, () -> company.getRfidCodeLengthMin() != null ? company.getRfidCodeLengthMin() : "");
        df.put(UtilCompany.RFID_PREFIX, () -> company.getRfidCodePrefix() != null ? company.getRfidCodePrefix() : "");
        df.put(UtilCompany.RFID_SUFFIX, () -> company.getRfidCodeSuffix() != null ? company.getRfidCodeSuffix() : "");
        df.put(UtilCompany.RFID_POSTFIX, () -> company.getRfidCodePostfix() != null ? company.getRfidCodePostfix() : "");


        fields.forEach(field -> dtoMap.put(field, df.getOrDefault(field, () -> "").get()));
        return dtoMap;
    }


}
