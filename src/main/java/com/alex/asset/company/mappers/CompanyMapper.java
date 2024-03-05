package com.alex.asset.company.mappers;

import com.alex.asset.company.domain.Company;
import com.alex.asset.company.dto.CompanyDto;

public class CompanyMapper {

    public static CompanyDto toDto(Company entity){
        CompanyDto dto = new CompanyDto();
        dto.setCompany(entity.getCompany());
        dto.setInfo(entity.getInfo());
        dto.setCountry(entity.getCountry());
        dto.setCity(entity.getCity());
        dto.setAddress(entity.getAddress());

        dto.setZipCode(entity.getZipCode());

        dto.setLogo(entity.getLogo());
        dto.setNip(entity.getNip());
        dto.setRegon(entity.getRegon());

        dto.setLastInventoryDate(entity.getLastInventoryDate());
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
        company.setLogo(dto.getLogo());
        company.setPhone(dto.getPhone());
        company.setNip(dto.getNip());
        company.setRegon(dto.getRegon());
        return company;
    }
}
