package com.alex.asset.core.service;


import com.alex.asset.core.domain.fields.Subtype;
import com.alex.asset.core.domain.fields.Type;
import com.alex.asset.core.dto.DataDto;
import com.alex.asset.core.dto.simple.ActiveDto;
import com.alex.asset.core.repo.product.type.SubtypeRepo;
import com.alex.asset.core.repo.product.type.TypeRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class TypeService {

    private final String TAG = "TYPE_SERVICE - ";


    private final TypeRepo typeRepo;
    private final SubtypeRepo subtypeRepo;

    public Type getTypeById(Long id){
        return typeRepo.get(id);
    }

    public Subtype getSubtypeById(Long id){
        return subtypeRepo.get(id);
    }


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

    public void addTypes(List<String> list) {
        for (String type: list){
            if (!typeRepo.existsByType(type))
              typeRepo.save(new Type(type));
        }
    }

    public boolean changeVisibilityOfType(ActiveDto dto) {
        if (typeRepo.existsById(dto.getId())){
            log.warn(TAG + "Type with id {} not found", dto.getId());
            return false;
        }
        typeRepo.updateVisibility(dto.isActive(), dto.getId());
        return true;
    }
    public boolean changeVisibilityOfSubtype(ActiveDto dto) {
        if (!typeRepo.existsById(dto.getId())) {
            log.warn(TAG + "Subtype with id {} not found", dto.getId());
            return false;
        }
        subtypeRepo.updateVisibility(dto.isActive(), dto.getId());
        return true;
    }
}
