package com.alex.asset.company.service;

import com.alex.asset.company.domain.Company;
import com.alex.asset.utils.dictionaries.UtilCompany;

import java.util.List;
import java.util.Map;

public class CompanyMapper {


    public static Map<String, Object> toDTOWithCustomFields(
            Map<String, Object> dtoMap, Company company, List<String> fields) {
        for (String field : fields) {
            switch (field) {
                case UtilCompany.COMPANY:
                    dtoMap.put(UtilCompany.COMPANY, company.getCompany() != null ? company.getCompany() : "");
                    break;
                case UtilCompany.CITY:
                    dtoMap.put(UtilCompany.CITY, company.getCity() != null ? company.getCity() : "");
                    break;
                case UtilCompany.STREET:
                    dtoMap.put(UtilCompany.STREET, company.getStreet() != null ? company.getStreet() : "");
                    break;
                case UtilCompany.ZIP_CODE:
                    dtoMap.put(UtilCompany.ZIP_CODE, company.getZipCode() != null ? company.getZipCode() : "");
                    break;
                case UtilCompany.NIP:
                    dtoMap.put(UtilCompany.NIP, company.getNip() != null ? company.getNip() : "");
                    break;
                case UtilCompany.REGON:
                    dtoMap.put(UtilCompany.REGON, company.getRegon() != null ? company.getRegon() : "");
                    break;
                case UtilCompany.PHONE:
                    dtoMap.put(UtilCompany.PHONE, company.getPhone() != null ? company.getPhone() : "");
                    break;
                case UtilCompany.EMAIL:
                    dtoMap.put(UtilCompany.EMAIL, company.getEmail() != null ? company.getEmail() : "");
                    break;
                case UtilCompany.LABEL_WIDTH:
                    dtoMap.put(UtilCompany.LABEL_WIDTH, company.getLabelWidth());
                    break;
                case UtilCompany.LABEL_HEIGHT:
                    dtoMap.put(UtilCompany.LABEL_HEIGHT, company.getLabelHeight());
                    break;
                case UtilCompany.LABEL_TYPE:
                    dtoMap.put(UtilCompany.LABEL_TYPE, company.getLabelType());
                    break;

                case UtilCompany.EMAIL_HOST:
                    dtoMap.put(UtilCompany.EMAIL_HOST, company.getHost() != null ? company.getHost() : "");
                    break;
                case UtilCompany.EMAIL_PORT:
                    dtoMap.put(UtilCompany.EMAIL_PORT, company.getPort() != null ? company.getPort() : "");
                    break;
                case UtilCompany.EMAIL_USERNAME:
                    dtoMap.put(UtilCompany.EMAIL_USERNAME, company.getUsername() != null ? company.getUsername() : "");
                    break;
                case UtilCompany.EMAIL_PASSWORD:
                    dtoMap.put(UtilCompany.EMAIL_PASSWORD, company.getPassword() != null ? company.getPassword() : "");
                    break;
                case UtilCompany.EMAIL_PROTOCOL:
                    dtoMap.put(UtilCompany.EMAIL_PROTOCOL, company.getProtocol() != null ? company.getProtocol() : "");
                    break;
                case UtilCompany.EMAIL_CONFIGURED:
                    dtoMap.put(UtilCompany.EMAIL_CONFIGURED, company.getIsEmailConfigured());
                    break;


                case UtilCompany.BAR_CODE_LENGTH:
                    dtoMap.put(UtilCompany.BAR_CODE_LENGTH, company.getBarCodeLength() != null ? company.getBarCodeLength() : "");
                    break;
                case UtilCompany.BAR_CODE_LENGTH_MAX:
                    dtoMap.put(UtilCompany.BAR_CODE_LENGTH_MAX, company.getBarCodeLengthMax() != null ? company.getBarCodeLengthMax() : "");
                    break;
                case UtilCompany.BAR_CODE_LENGTH_MIN:
                    dtoMap.put(UtilCompany.BAR_CODE_LENGTH_MIN, company.getBarCodeLengthMin() != null ? company.getBarCodeLengthMin() : "");
                    break;
                case UtilCompany.BAR_CODE_PREFIX:
                    dtoMap.put(UtilCompany.BAR_CODE_PREFIX, company.getBarCodePrefix() != null ? company.getBarCodePrefix() : "");
                    break;
                case UtilCompany.BAR_CODE_SUFFIX:
                    dtoMap.put(UtilCompany.BAR_CODE_SUFFIX, company.getBarCodeSuffix() != null ? company.getBarCodeSuffix() : "");
                    break;
                case UtilCompany.BAR_CODE_POSTFIX:
                    dtoMap.put(UtilCompany.BAR_CODE_POSTFIX, company.getBarCodePostfix() != null ? company.getBarCodePostfix() : "");
                    break;


                case UtilCompany.RFID_LENGTH:
                    dtoMap.put(UtilCompany.RFID_LENGTH, company.getRfidCodeLength() != null ? company.getRfidCodeLength() : "");
                    break;
                case UtilCompany.RFID_LENGTH_MAX:
                    dtoMap.put(UtilCompany.RFID_LENGTH_MAX, company.getRfidCodeLengthMax() != null ? company.getRfidCodeLengthMax() : "");
                    break;
                case UtilCompany.RFID_LENGTH_MIN:
                    dtoMap.put(UtilCompany.RFID_LENGTH_MIN, company.getRfidCodeLengthMin() != null ? company.getRfidCodeLengthMin() : "");
                    break;
                case UtilCompany.RFID_PREFIX:
                    dtoMap.put(UtilCompany.RFID_PREFIX, company.getRfidCodePrefix() != null ? company.getRfidCodePrefix() : "");
                    break;
                case UtilCompany.RFID_SUFFIX:
                    dtoMap.put(UtilCompany.RFID_SUFFIX, company.getRfidCodeSuffix() != null ? company.getRfidCodeSuffix() : "");
                    break;
                case UtilCompany.RFID_POSTFIX:
                    dtoMap.put(UtilCompany.RFID_POSTFIX, company.getRfidCodePostfix() != null ? company.getRfidCodePostfix() : "");
                    break;

                default: break;
            }
        }
        return dtoMap;
    }


}
