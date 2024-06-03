package com.alex.asset.configure.mappers;

import com.alex.asset.company.domain.DataDto;
import com.alex.asset.configure.domain.Branch;
import com.alex.asset.configure.domain.Location;
import com.alex.asset.configure.domain.Subtype;
import com.alex.asset.configure.domain.Type;
import com.alex.asset.configure.repo.SubtypeRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
public class ConfigureMapper {
    public static List<DataDto.Type> convertTypesToDTOs(List<Type> types) {
        return types.stream()
                .map(type -> new DataDto.Type(
                        type.getId(),
                        type.getType(),
                        type.isActive()))
                .collect(Collectors.toList());
    }

    public static List<DataDto.Subtype> convertSubtypesToDTOs(List<Subtype> subtypes) {
        return subtypes.stream()
                .map(subtype -> new DataDto.Subtype(
                        subtype.getId(),
                        subtype.getSubtype(),
                        subtype.isActive()))
                .collect(Collectors.toList());
    }


    public static List<DataDto.Location> convertLocationToDTOs(List<Location> locations) {
        List<DataDto.Location> list = new ArrayList<>();
        for (Location location : locations) {
            list.add(new DataDto.Location(
                    location.getId(),
                    location.getLocation(),
                    location.getBranch().getId(),
                    location.isActive()));
        }

        return list;
    }


    public static List<DataDto.Branch> convertBranchToDTOs(List<Branch> branches) {
        List<DataDto.Branch> list = new ArrayList<>();
        for (Branch branch : branches) {
            list.add(new DataDto.Branch(
                    branch.getId(),
                    branch.getBranch(),
                    branch.isActive()));
        }

        return list;
    }
}
