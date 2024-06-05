package com.alex.asset.company.service;

import com.alex.asset.company.domain.Company;
import com.alex.asset.utils.dictionaries.UtilCompany;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CompanyMapper {
    public static Map<String, Object> toDTOWithCustomFields(
            Map<String, Object> dtoMap, Company company, List<String> fields) {
        Map<String, Supplier<Object>> df = new HashMap<>();

        df.put(UtilCompany.COMPANY, () -> getCompanyInfo(company));
        df.put(UtilCompany.LABEL_CONFIG, () -> getLabelInfo(company));
        df.put(UtilCompany.EMAIL_CONFIG, () -> getEmailInfo(company));
        df.put(UtilCompany.BARCODE, () -> getBarcodeInfo(company));
        df.put(UtilCompany.RFID_CODE, () -> getRfidCodeInfo(company));


        fields.forEach(field -> dtoMap.put(field, df.getOrDefault(field, () -> "").get()));
        return dtoMap;
    }

    private static Map<String, Object> getBarcodeInfo(Company company) {
        Map<String, Object> map = new HashMap<>();
        map.put(UtilCompany.BARCODE_LENGTH,  company.getBarCodeLength() != null ? company.getBarCodeLength() : "");
        map.put(UtilCompany.BARCODE_LENGTH_MAX,  company.getBarCodeLengthMax() != null ? company.getBarCodeLengthMax() : "");
        map.put(UtilCompany.BARCODE_LENGTH_MIN,  company.getBarCodeLengthMin() != null ? company.getBarCodeLengthMin() : "");
        map.put(UtilCompany.BARCODE_PREFIX, company.getBarCodePrefix() != null ? company.getBarCodePrefix() : "");
        map.put(UtilCompany.BARCODE_SUFFIX, company.getBarCodeSuffix() != null ? company.getBarCodeSuffix() : "");
        map.put(UtilCompany.BARCODE_POSTFIX, company.getBarCodePostfix() != null ? company.getBarCodePostfix() : "");
        return map;
    }
    private static Map<String, Object> getRfidCodeInfo(Company company) {
        Map<String, Object> map = new HashMap<>();
        map.put(UtilCompany.RFID_LENGTH,  company.getRfidCodeLength() != null ? company.getRfidCodeLength() : "");
        map.put(UtilCompany.RFID_LENGTH_MAX, company.getRfidCodeLengthMax() != null ? company.getRfidCodeLengthMax() : "");
        map.put(UtilCompany.RFID_LENGTH_MIN, company.getRfidCodeLengthMin() != null ? company.getRfidCodeLengthMin() : "");
        map.put(UtilCompany.RFID_PREFIX,  company.getRfidCodePrefix() != null ? company.getRfidCodePrefix() : "");
        map.put(UtilCompany.RFID_SUFFIX,  company.getRfidCodeSuffix() != null ? company.getRfidCodeSuffix() : "");
        map.put(UtilCompany.RFID_POSTFIX,  company.getRfidCodePostfix() != null ? company.getRfidCodePostfix() : "");
        return map;
    }
    private static Map<String, Object> getEmailInfo(Company company) {
        Map<String, Object> map = new HashMap<>();
        map.put(UtilCompany.EMAIL_HOST,  company.getHost() != null ? company.getHost() : "");
        map.put(UtilCompany.EMAIL_PORT, company.getPort() != null ? company.getPort() : "");
        map.put(UtilCompany.EMAIL_USERNAME,  company.getUsername() != null ? company.getUsername() : "");
        map.put(UtilCompany.EMAIL_PASSWORD,  company.getPassword() != null ? company.getPassword() : "");
        map.put(UtilCompany.EMAIL_PROTOCOL, company.getProtocol() != null ? company.getProtocol() : "");
        map.put(UtilCompany.EMAIL_CONFIGURED,  company.getIsEmailConfigured() != null ? company.getIsEmailConfigured() : "");
        return map;
    }

    private static Map<String, Object> getLabelInfo(Company company) {
        Map<String, Object> map = new HashMap<>();
        map.put(UtilCompany.LABEL_WIDTH,  company.getLabelWidth() != null ? company.getLabelWidth() : "");
        map.put(UtilCompany.LABEL_HEIGHT,  company.getLabelHeight() != null ? company.getLabelHeight() : "");
        map.put(UtilCompany.LABEL_TYPE,  company.getLabelType() != null ? company.getLabelType() : "");
        return map;
    }


    private static Map<String, Object> getCompanyInfo(Company company) {
        Map<String, Object> map = new HashMap<>();
        map.put(UtilCompany.COMPANY, company.getCompany() != null ? company.getCompany() : "");
        map.put(UtilCompany.CITY,  company.getCity() != null ? company.getCity() : "");
        map.put(UtilCompany.STREET,  company.getStreet() != null ? company.getStreet() : "");
        map.put(UtilCompany.ZIP_CODE,  company.getZipCode() != null ? company.getZipCode() : "");
        map.put(UtilCompany.NIP,  company.getNip() != null ? company.getNip() : "");
        map.put(UtilCompany.REGON, company.getRegon() != null ? company.getRegon() : "");
        map.put(UtilCompany.PHONE,  company.getPhone() != null ? company.getPhone() : "");
        map.put(UtilCompany.EMAIL,  company.getEmail() != null ? company.getEmail() : "");
        return map;
    }
}
