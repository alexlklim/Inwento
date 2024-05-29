package com.alex.asset.configure.services.services;

import com.alex.asset.configure.domain.Branch;
import com.alex.asset.configure.domain.Location;
import com.alex.asset.configure.repo.BranchRepo;
import com.alex.asset.configure.repo.LocationRepo;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.utils.dto.DtoData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BranchService {

    private final String TAG = "BRANCH_SERVICE - ";

    private final LogService logService;

    private final BranchRepo branchRepo;
    private final LocationRepo locationRepo;


    public void addBranch(DtoData dto, Long userId) {
        log.info(TAG + "Add branch {}", dto.getName());
        if (!branchRepo.existsByBranch(dto.getName())) {
            logService.addLog(userId, Action.CREATE, Section.BRANCH, dto.toString());
            branchRepo.save(new Branch(dto.getName()));
        }
    }

    public void updateBranch(DtoData dto, Long userId) {
        log.info(TAG + "Update branch {}", dto.getId());
        branchRepo.update(dto.isActive(), dto.getId());
        logService.addLog(userId, Action.UPDATE, Section.BRANCH, dto.toString());
    }


    public void addLocation(DtoData dto, Long userId) {
        log.info(TAG + "Add location {}", dto.getName());
        Branch branch = branchRepo.findById(dto.getParentId()).orElse(null);
        if (branch != null) {
            if (!locationRepo.existsByLocationAndBranch(dto.getName(), branch)) {
                logService.addLog(userId, Action.CREATE, Section.LOCATION, dto.toString());
                locationRepo.save(new Location(dto.getName(), branch));
            }
        }
    }

    public void updateLocation(DtoData dto, Long userId) {
        log.info(TAG + "Update location {}", dto.getId());
        locationRepo.update(dto.isActive(), dto.getId());
        logService.addLog(userId, Action.UPDATE, Section.LOCATION, dto.toString());
    }

    public List<Branch> getAllBranches() {
        return branchRepo.findAll();
    }

    public List<Location> getAllLocations() {
        return locationRepo.findAll();
    }
}
