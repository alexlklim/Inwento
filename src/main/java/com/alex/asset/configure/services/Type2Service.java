package com.alex.asset.configure.services;


import com.alex.asset.company.domain.DataDto;
import com.alex.asset.configure.mappers.ConfigureMapper;
import com.alex.asset.configure.domain.Subtype;
import com.alex.asset.configure.domain.Type;
import com.alex.asset.configure.repo.SubtypeRepo;
import com.alex.asset.configure.repo.TypeRepo;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.exceptions.shared.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class Type2Service {
    private final String TAG = "TYPE_SERVICE - ";
    private final TypeRepo typeRepo;
    private final SubtypeRepo subtypeRepo;
    private final LogService logService;


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
    public void addSubtypes(Long typeId, List<String> list, Long userId) {
        log.info(TAG + "Add subtypes {} by user with id {}", list, userId);
        Type type = typeRepo.findById(typeId).orElseThrow(
                () -> new ResourceNotFoundException("Type with id " + typeId + " was not found"));


        list.forEach(subtype -> {
            Subtype newSubtype = new Subtype(subtype, type);
            subtypeRepo.save(newSubtype);
            logService.addLog(userId, Action.CREATE, Section.SUBTYPE, "Add subtype " + newSubtype.getSubtype());
        });
    }


    @SneakyThrows
    public Type getTypeByType(String type, Long userId) {
        Type typeFromDB = typeRepo.findTypeByType(type).orElse(null);
        if (typeFromDB == null) {
            addTypes(List.of(type), userId);
            return typeRepo.findTypeByType(type).orElse(null);
        }
        return typeFromDB;
    }


    @SneakyThrows
    public Subtype getSubtypeBySubtypeAndType(Type type, String subtype, Long userId) {
        log.info(TAG + "Get subtype by subtype {} and type {}", subtype, type.getType());
        Subtype subtypeFromDb = subtypeRepo.findSubtypeBySubtypeAndType(subtype, type).orElse(null);
        if (subtypeFromDb == null) {
            addSubtypes(type.getId(), List.of(subtype), userId);
            return subtypeRepo.findSubtypeBySubtypeAndType(subtype, type).orElse(null);
        }
        return subtypeFromDb;

    }



}
