package com.alex.asset.company.mappers;

import com.alex.asset.company.domain.Company;
import com.alex.asset.company.dto.CompanyDto;

public class CompanyMapper {

    public static CompanyDto toDto(Company entity){
        CompanyDto dto = new CompanyDto();
        dto.setCompany(entity.getCompany());
        dto.setCity(entity.getCity());
        dto.setStreet(entity.getStreet());
        dto.setZipCode(entity.getZipCode());
        dto.setNip(entity.getNip());
        dto.setRegon(entity.getRegon());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        return dto;
    }

    public static Company updateCompany(Company company, CompanyDto dto) {
        company.setCompany(dto.getCompany());
        company.setCity(dto.getCity());
        company.setStreet(dto.getStreet());
        company.setZipCode(dto.getZipCode());

        company.setNip(dto.getNip());
        company.setRegon(dto.getRegon());

        company.setPhone(dto.getPhone());
        company.setEmail(dto.getEmail());
        return company;
    }
}
