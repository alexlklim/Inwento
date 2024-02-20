package com.alex.asset.core.mappers;

import com.alex.asset.core.domain.Company;
import com.alex.asset.core.dto.CompanyDto;

import java.util.UUID;

public class CompanyMapper {

    public static Company toEntity(CompanyDto dto){
        Company company = new Company();
        company.setActive(true);
        company.setCompany(dto.getCompany());
        company.setInfo(dto.getInfo());
        company.setCountry(dto.getCountry());
        company.setCity(dto.getCity());
        company.setAddress(dto.getAddress());
        company.setProductCounter(1);
        company.setSecretCode(UUID.randomUUID());
        return company;
    }

    public static CompanyDto toDto(Company entity){
        CompanyDto dto = new CompanyDto();
        dto.setActive(entity.isActive());
        dto.setCompany(entity.getCompany());
        dto.setInfo(entity.getInfo());
        dto.setCountry(entity.getCountry());
        dto.setCity(entity.getCity());
        dto.setAddress(entity.getAddress());
        dto.setProductCounter(entity.getProductCounter());
        return dto;
    }

    public static Company updateCompany(Company company, CompanyDto dto) {
        company.setInfo(dto.getInfo());
        company.setCountry(dto.getCountry());
        company.setCity(dto.getCity());
        company.setAddress(dto.getAddress());
        return company;

    }
}
