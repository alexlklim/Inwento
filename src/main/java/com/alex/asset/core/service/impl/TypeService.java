package com.alex.asset.core.service.impl;


import com.alex.asset.core.domain.Company;
import com.alex.asset.core.domain.fields.Subtype;
import com.alex.asset.core.domain.fields.Type;
import com.alex.asset.core.repo.CompanyRepo;
import com.alex.asset.core.repo.product.type.SubtypeRepo;
import com.alex.asset.core.repo.product.type.TypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TypeService {
    private final TypeRepo typeRepo;
    private final SubtypeRepo subtypeRepo;

    private final CompanyRepo companyRepo;


    public Map<String, List<String>> getTypesMap(UUID companyId){
        Map<String, List<String>> typesMap = new HashMap<>();
        Company company = companyRepo.findById(companyId).orElse(null);
        List<Type> types = typeRepo.findByActiveTrueAndCompany(company);
        for (Type type: types){
            List<String> subtypeNames =new ArrayList<>();
            List<Subtype> subtypes = subtypeRepo.findByActiveTrueAndType(type);
            for (Subtype subtype : subtypes) {
                subtypeNames.add(subtype.getSubtype());
            }
            typesMap.put(type.getType(), subtypeNames);
        }
        return typesMap;


    }
}
