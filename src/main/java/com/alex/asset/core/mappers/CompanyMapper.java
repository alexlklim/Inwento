package com.alex.asset.core.mappers;

import com.alex.asset.core.domain.Company;
import com.alex.asset.core.dto.CompanyDto;
import com.alex.asset.security.domain.dto.UserDto;

import java.util.List;

public class CompanyMapper {

    public static CompanyDto toDto(Company entity){
        CompanyDto dto = new CompanyDto();
        dto.setCompany(entity.getCompany());
        dto.setInfo(entity.getInfo());
        dto.setCountry(entity.getCountry());
        dto.setCity(entity.getCity());
        dto.setAddress(entity.getAddress());
        dto.setOwnerFirstName(entity.getOwner().getFirstname());
        dto.setOwnerLastName(entity.getOwner().getLastname());
        dto.setOwnerEmail(entity.getOwner().getEmail());
        return dto;
    }

    public static Company updateCompany(Company company, CompanyDto dto) {
        company.setCompany(dto.getCompany());
        company.setInfo(dto.getInfo());
        company.setCountry(dto.getCountry());
        company.setCity(dto.getCity());
        company.setAddress(dto.getAddress());
        return company;
    }
}
