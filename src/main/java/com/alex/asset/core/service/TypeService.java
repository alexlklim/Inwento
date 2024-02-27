package com.alex.asset.core.service;


import com.alex.asset.core.domain.fields.Subtype;
import com.alex.asset.core.domain.fields.Type;
import com.alex.asset.core.dto.DataDto;
import com.alex.asset.core.repo.product.type.SubtypeRepo;
import com.alex.asset.core.repo.product.type.TypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeService {

    private final String TAG = "TYPE_SERVICE - ";


    private final TypeRepo typeRepo;
    private final SubtypeRepo subtypeRepo;

    public List<DataDto.Type> getTypes(){
        return convertTypesToDTOs(typeRepo.getActive());
    }



    private List<DataDto.Type> convertTypesToDTOs(List<Type> types){
        return types.stream()
                .map(type -> new DataDto.Type(type.getId(), type.getType(),
                        convertSubtypesToDTOs(subtypeRepo.findByActiveTrueAndType(type))))
                .collect(Collectors.toList());
    }


    private List<DataDto.Subtype> convertSubtypesToDTOs(List<Subtype> subtypes){
        return subtypes.stream()
                .map(subtype -> new DataDto.Subtype(subtype.getId(), subtype.getSubtype()))
                .collect(Collectors.toList());
    }
}
