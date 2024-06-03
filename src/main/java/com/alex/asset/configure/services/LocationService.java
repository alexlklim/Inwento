package com.alex.asset.configure.services;

import com.alex.asset.configure.domain.Branch;
import com.alex.asset.configure.domain.Location;
import com.alex.asset.configure.repo.BranchRepo;
import com.alex.asset.configure.repo.LocationRepo;
import com.alex.asset.exceptions.shared.ObjectAlreadyExistException;
import com.alex.asset.exceptions.shared.ResourceNotFoundException;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.utils.dto.DtoData;
import com.alex.asset.utils.dto.DtoName;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LocationService {
     private final String TAG = "LOCATION_SERVICE - ";
     private final LogService logService;
     private final BranchRepo branchRepo;
     private final LocationRepo locationRepo;

    public Branch getBranchByBranch(String branch, Long userId) {
        Branch branchFromDB = branchRepo.findBranchByBranch(branch).orElse(null);
        if (branchFromDB == null){
            return addBranch(new DtoData(branch), userId);
        }
        return branchFromDB;
    }

    public List<Location> getLocations(){
        return locationRepo.getActive();
    }
    public List<Branch> getBranches() {
        return branchRepo.getActive();
    }

    @SneakyThrows
    public Location getLocationByLocationAndBranch(String location, String branchName, Long userId) {
        Branch branch = branchRepo.findBranchByBranch(branchName)
                .orElse(null);
        if (branch == null) return null;
        Location locationFromDB = locationRepo.findLocationByLocationAndBranch(location, branch).orElse(null);
        if (locationFromDB == null){
            return addLocation(new DtoName(location), branch.getId(), userId);
        }
        return locationFromDB;
    }
    @SneakyThrows
    public Branch addBranch(DtoData dto, Long userId) {
        log.info(TAG + "Add branch {}", dto.getName());
        if (branchRepo.existsByBranch(dto.getName()))
            return null;
        logService.addLog(userId, Action.CREATE, Section.BRANCH, dto.toString());
        return branchRepo.save(new Branch(dto.getName()));
    }



    @SneakyThrows
    public Location addLocation(DtoName dto, Long branchId, Long userId) {
        log.info(TAG + "Add location {}", dto.getName());
        Branch branch = branchRepo.findById(branchId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Branch not found with id " + branchId));
        if (locationRepo.existsByLocationAndBranch(dto.getName(), branch))
            throw new ObjectAlreadyExistException("Location with name " + dto.getName() + " already exists");
        logService.addLog(userId, Action.CREATE, Section.LOCATION, dto.toString());
        return locationRepo.save(new Location(dto.getName(), branch));
    }




}
