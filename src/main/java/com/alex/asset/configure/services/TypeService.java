package com.alex.asset.configure.services;


import com.alex.asset.company.domain.DataDto;
import com.alex.asset.configure.domain.Subtype;
import com.alex.asset.configure.domain.Type;
import com.alex.asset.configure.repo.SubtypeRepo;
import com.alex.asset.configure.repo.TypeRepo;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.utils.dto.DtoActive;
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
    private final LogService logService;

    public Type getTypeById(Long id) {
        return typeRepo.get(id);
    }

    public Subtype getSubtypeById(Long id) {
        return subtypeRepo.get(id);
    }


    public List<DataDto.Type> getTypes() {
        return convertTypesToDTOs(typeRepo.getActive());
    }


    private List<DataDto.Type> convertTypesToDTOs(List<Type> types) {
        return types.stream()
                .map(type -> new DataDto.Type(type.getId(), type.getType(),
                        convertSubtypesToDTOs(subtypeRepo.findByActiveTrueAndType(type))))
                .collect(Collectors.toList());
    }


    private List<DataDto.Subtype> convertSubtypesToDTOs(List<Subtype> subtypes) {
        return subtypes.stream()
                .map(subtype -> new DataDto.Subtype(subtype.getId(), subtype.getSubtype()))
                .collect(Collectors.toList());
    }

    public void addTypes(List<String> list, Long userId) {
        for (String type : list) {
            if (!typeRepo.existsByType(type)) {
                typeRepo.save(new Type(type));
                logService.addLog(userId, Action.CREATE, Section.TYPE, "Add type " + type);
            }
        }
    }

    public boolean changeVisibilityOfType(DtoActive dto, Long userId) {
        if (!typeRepo.existsById(dto.getId())) {
            log.warn(TAG + "Type with id {} not found", dto.getId());
            return false;
        }
        Type type = typeRepo.get(dto.getId());
        type.setActive(dto.isActive());
        typeRepo.save(type);
        logService.addLog(userId, Action.UPDATE, Section.TYPE, "Change visibility of type " + type.getType());
        return true;
    }

    public boolean changeVisibilityOfSubtype(DtoActive dto, Long userId) {
        if (!typeRepo.existsById(dto.getId())) {
            log.warn(TAG + "Subtype with id {} not found", dto.getId());
            return false;
        }
        Subtype subtype = subtypeRepo.get(dto.getId());
        subtype.setActive(dto.isActive());
        subtypeRepo.save(subtype);
        logService.addLog(userId, Action.UPDATE, Section.SUBTYPE, "Change visibility of type " + subtype.getSubtype());
        return true;
    }

    public boolean addSubtypes(Long typeId, List<String> list, Long userId) {
        Type type = typeRepo.findById(typeId).orElse(null);
        if (type == null) {
            log.warn(TAG + "Type with id {} not found", typeId);
            return false;
        }

        list.stream().map(subtype -> {
            Subtype newSubtype = new Subtype(subtype, type);
            subtypeRepo.save(newSubtype);
            logService.addLog(userId, Action.CREATE, Section.SUBTYPE, "Add subtype " + newSubtype.getSubtype());
            return newSubtype;
        }).forEach(subtype -> {
        });

        return true;
    }
}
