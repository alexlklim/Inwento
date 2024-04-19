package com.alex.asset.company.service;

import com.alex.asset.company.domain.Company;

import java.util.List;
import java.util.Map;

public class CompanyMapper {


    public static Map<String, Object> toDTOWithCustomFields(
            Map<String, Object> dtoMap, Company company, List<String> fields, Long userId) {
        for (String field : fields) {
            switch (field) {
                case "company": dtoMap.put("company", company.getCompany()); break;
                case "city": dtoMap.put("city", company.getCity()); break;
                case "street": dtoMap.put("street", company.getStreet()); break;
                case "zip_code": dtoMap.put("zip_code", company.getZipCode()); break;
                case "nip": dtoMap.put("nip", company.getNip()); break;
                case "regon": dtoMap.put("regon", company.getRegon()); break;
                case "phone": dtoMap.put("phone", company.getPhone()); break;
                case "email": dtoMap.put("email", company.getEmail()); break;
                case "label_width": dtoMap.put("label_width", company.getLabelWidth()); break;
                case "label_height": dtoMap.put("label_height", company.getLabelHeight()); break;
                case "label_type": dtoMap.put("label_type", company.getLabelType()); break;
                case "email_host": dtoMap.put("email_host", company.getHost()); break;
                case "email_port": dtoMap.put("email_port", company.getPort()); break;
                case "email_username": dtoMap.put("email_username", company.getUsername()); break;
                case "email_password": dtoMap.put("email_password", company.getPassword()); break;
                case "email_protocol": dtoMap.put("email_protocol", company.getProtocol()); break;
                case "email_configured": dtoMap.put("email_configured", company.getIsEmailConfigured()); break;
                default: break;
            }
        }
        return dtoMap;
    }


}
