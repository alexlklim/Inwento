package com.alex.asset.company.service;

import com.alex.asset.company.domain.Company;

import java.util.List;
import java.util.Map;

public class CompanyMapper {


    public static Map<String, Object> toDTOWithCustomFields(
            Map<String, Object> dtoMap, Company company, List<String> fields) {
        for (String field : fields) {
            switch (field) {
                case "company": dtoMap.put("company", company.getCompany() != null ? company.getCompany() : ""); break;
                case "city": dtoMap.put("city", company.getCity() != null ? company.getCity() : ""); break;
                case "street": dtoMap.put("street", company.getStreet() != null ? company.getStreet() : ""); break;
                case "zip_code": dtoMap.put("zip_code", company.getZipCode() != null ? company.getZipCode() : ""); break;
                case "nip": dtoMap.put("nip", company.getNip() != null ? company.getNip() : ""); break;
                case "regon": dtoMap.put("regon", company.getRegon() != null ? company.getRegon() : ""); break;
                case "phone": dtoMap.put("phone", company.getPhone() != null ? company.getPhone() : ""); break;
                case "email": dtoMap.put("email", company.getEmail() != null ? company.getEmail() : ""); break;

                case "label_width": dtoMap.put("label_width", company.getLabelWidth()); break;
                case "label_height": dtoMap.put("label_height", company.getLabelHeight()); break;
                case "label_type": dtoMap.put("label_type", company.getLabelType()); break;

                case "email_host": dtoMap.put("email_host", company.getHost() != null ? company.getHost() : ""); break;
                case "email_port": dtoMap.put("email_port", company.getPort() != null ? company.getPort() : ""); break;
                case "email_username": dtoMap.put("email_username", company.getUsername() != null ? company.getUsername() : ""); break;
                case "email_password": dtoMap.put("email_password", company.getPassword() != null ? company.getPassword() : ""); break;
                case "email_protocol": dtoMap.put("email_protocol", company.getProtocol() != null ? company.getProtocol() : ""); break;
                case "email_configured": dtoMap.put("email_configured", company.getIsEmailConfigured()); break;

                case "bar_code_length": dtoMap.put("bar_code_length", company.getBarCodeLength() != null ? company.getBarCodeLength() : ""); break;
                case "bar_code_length_max": dtoMap.put("bar_code_length_max", company.getBarCodeLengthMax() != null ? company.getBarCodeLengthMax() : ""); break;
                case "bar_code_length_min": dtoMap.put("bar_code_length_min", company.getBarCodeLengthMin() != null ? company.getBarCodeLengthMin() : ""); break;
                case "bar_code_prefix": dtoMap.put("bar_code_prefix", company.getBarCodePrefix() != null ? company.getBarCodePrefix() : ""); break;
                case "bar_code_suffix": dtoMap.put("bar_code_suffix", company.getBarCodeSuffix() != null ? company.getBarCodeSuffix() : ""); break;
                case "bar_code_postfix": dtoMap.put("bar_code_postfix", company.getBarCodePostfix() != null ? company.getBarCodePostfix() : ""); break;

                case "rfid_length": dtoMap.put("rfid_length", company.getRfidLength() != null ? company.getRfidLength() : ""); break;
                case "rfid_length_max": dtoMap.put("rfid_length_max", company.getRfidLengthMax() != null ? company.getRfidLengthMax() : ""); break;
                case "rfid_length_min": dtoMap.put("rfid_length_min", company.getRfidLengthMin() != null ? company.getRfidLengthMin() : ""); break;
                case "rfid_prefix": dtoMap.put("rfid_prefix", company.getRfidPrefix() != null ? company.getRfidPrefix() : ""); break;
                case "rfid_suffix": dtoMap.put("rfid_suffix", company.getRfidSuffix() != null ? company.getRfidSuffix() : ""); break;
                case "rfid_postfix": dtoMap.put("rfid_postfix", company.getRfidPostfix() != null ? company.getRfidPostfix() : ""); break;


                default: break;
            }
        }
        return dtoMap;
    }


}
