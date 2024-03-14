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
import com.alex.asset.utils.expceptions.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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


    @SneakyThrows
    public Type getTypeById(Long id) {
        log.info(TAG + "Get type by id {}", id);
        return typeRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Type with id " + id + " was not found")
        );
    }

    @SneakyThrows
    public Subtype getSubtypeById(Long id) {
        log.info(TAG + "Get subtype by id {}", id);
        return subtypeRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Subtype with id " + id + " was not found")
        );
    }


    public List<DataDto.Type> getTypes() {
        log.info(TAG + "Get types");
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
        log.info(TAG + "Add types {} by user with id {}", list, userId);
        for (String type : list) {
            if (!typeRepo.existsByType(type)) {
                typeRepo.save(new Type(type));
                logService.addLog(userId, Action.CREATE, Section.TYPE, "Add type " + type);
            }
        }
    }

    @SneakyThrows
    public void changeVisibilityOfType(DtoActive dto, Long userId) {
        log.info(TAG + "Change visibility of type {} by user with id {}", dto.getId(), userId);
        Type type = typeRepo.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Type with id " + dto.getId() + " was not found"));
        type.setActive(dto.isActive());
        typeRepo.save(type);
        logService.addLog(userId, Action.UPDATE, Section.TYPE, "Change visibility of type " + type.getType());
    }

    @SneakyThrows
    public void changeVisibilityOfSubtype(DtoActive dto, Long userId) {
        log.info(TAG + "Change visibility of subtype {} by user with id {}", dto.getId(), userId);
        Subtype subtype = subtypeRepo.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Subtype with id " + dto.getId() + " was not found"));
        subtype.setActive(dto.isActive());
        subtypeRepo.save(subtype);
        logService.addLog(userId, Action.UPDATE, Section.SUBTYPE, "Change visibility of type " + subtype.getSubtype());
    }

    @SneakyThrows
    public void addSubtypes(Long typeId, List<String> list, Long userId) {
        log.info(TAG + "Add subtypes {} by user with id {}", list, userId);
        Type type = typeRepo.findById(typeId).orElseThrow(
                () -> new ResourceNotFoundException("Type with id " + typeId + " was not found"));

        list.stream().map(subtype -> {
            Subtype newSubtype = new Subtype(subtype, type);
            subtypeRepo.save(newSubtype);
            logService.addLog(userId, Action.CREATE, Section.SUBTYPE, "Add subtype " + newSubtype.getSubtype());
            return newSubtype;
        }).forEach(subtype -> {
        });

    }
}
