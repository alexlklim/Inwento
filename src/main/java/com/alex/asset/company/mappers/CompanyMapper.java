package com.alex.asset.company.mappers;

import com.alex.asset.company.domain.Company;
import com.alex.asset.company.dto.CompanyDto;

public class CompanyMapper {

    public static CompanyDto toDto(Company entity){
        CompanyDto dto = new CompanyDto();
        dto.setCompany(entity.getCompany());

        dto.setCity(entity.getCity());


        dto.setZipCode(entity.getZipCode());


        dto.setNip(entity.getNip());
        dto.setRegon(entity.getRegon());


        return dto;
    }

    public static Company updateCompany(Company company, CompanyDto dto) {
        company.setCompany(dto.getCompany());
        company.setCity(dto.getCity());
        company.setPhone(dto.getPhone());
        company.setNip(dto.getNip());
        company.setRegon(dto.getRegon());
        return company;
    }
}
