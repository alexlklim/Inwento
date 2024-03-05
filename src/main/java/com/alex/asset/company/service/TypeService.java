package com.alex.asset.company.service;


import com.alex.asset.company.domain.Subtype;
import com.alex.asset.company.domain.Type;
import com.alex.asset.company.dto.DataDto;
import com.alex.asset.utils.dto.ActiveDto;
import com.alex.asset.company.repo.SubtypeRepo;
import com.alex.asset.company.repo.TypeRepo;
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

    public boolean addSubtypes(Long id, List<String> list) {
        Type type = typeRepo.findById(id).orElse(null);
        if (type == null) {
            log.warn(TAG + "Type with id {} not found", id);
            return false;
        }
        list.stream().map(subtype -> new Subtype(subtype, type)).forEach(subtypeRepo::save);
        return true;
    }
}