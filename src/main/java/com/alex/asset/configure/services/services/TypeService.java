package com.alex.asset.configure.services.services;

import com.alex.asset.configure.domain.Subtype;
import com.alex.asset.configure.domain.Type;
import com.alex.asset.configure.repo.SubtypeRepo;
import com.alex.asset.configure.repo.TypeRepo;
import com.alex.asset.exceptions.shared.ResourceNotFoundException;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.security.domain.User;
import com.alex.asset.utils.dto.DtoData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class TypeService {

    private final String TAG = "TYPE_SERVICE - ";
    private final TypeRepo typeRepo;
    private final SubtypeRepo subtypeRepo;
    private final LogService logService;

    public void addType(DtoData dto, User user) {
        log.info(TAG + "Add type {}", dto.getName());
        if (!typeRepo.existsByType(dto.getName())) {
            typeRepo.save(new Type(dto.getName()));
            logService.addLog(user, Action.CREATE, Section.TYPE, dto.getName());
        }
    }

    public void updateType(DtoData dto, User user) {
        log.info(TAG + "Update type {}", dto.getId());
        Type type = typeRepo.findById(dto.getId()).orElse(null);
        if (type != null) {
            type.setActive(dto.isActive());
            typeRepo.save(type);
        }
        logService.addLog(user, Action.CREATE, Section.TYPE, String.valueOf(dto.getId()));
    }


    public void addSubtype(DtoData dto, User user) {
        log.info(TAG + "Add type {}", dto.getName());
        Type type = typeRepo.findById(dto.getParentId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Type not found with id " + dto.getParentId()));
        if (subtypeRepo.existsBySubtypeAndType(dto.getName(), type))
            return;
        logService.addLog(user, Action.CREATE, Section.SUBTYPE, dto.toString());
        subtypeRepo.save(new Subtype(dto.getName(), type));
    }

    public void updateSubtype(DtoData dto, User user) {
        log.info(TAG + "Add SubType {}", dto.getId());
        Subtype subtype = subtypeRepo.findById(dto.getId()).orElse(null);
        if (subtype != null) {
            subtype.setActive(dto.isActive());
            subtypeRepo.save(subtype);
        }
        logService.addLog(user, Action.UPDATE, Section.SUBTYPE, String.valueOf(dto.getId()));
    }

    public Optional<Type> getTypeById(Long typeId) {
        return typeRepo.findById(typeId);
    }
    public Optional<Subtype> getSubtypeById(Long subtypeId) {
        return subtypeRepo.findById(subtypeId);
    }
    public List<Type> getAllTypes() {
        return typeRepo.findAll();
    }

    public List<Subtype> getAllSubtypes() {
        return subtypeRepo.findAll();
    }

    public List<Type> getAllActiveTypes() {
        return typeRepo.getActive();
    }

    public List<Subtype> getAllActiveSubtypes() {
        return subtypeRepo.getActive();
    }
}
