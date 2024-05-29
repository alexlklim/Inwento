package com.alex.asset.configure.mappers;

import com.alex.asset.company.domain.DataDto;
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
@Service
@RequiredArgsConstructor
public class ConfigureMapper {
    private final SubtypeRepo subtypeRepo;

    public List<DataDto.Type> convertTypesToDTOs(List<Type> types) {
        return types.stream()
                .map(type -> new DataDto.Type(type.getId(), type.getType(),
                        convertSubtypesToDTOs(subtypeRepo.findByActiveTrueAndType(type))))
                .collect(Collectors.toList());
    }

    public List<DataDto.Subtype> convertSubtypesToDTOs(List<Subtype> subtypes) {
        return subtypes.stream()
                .map(subtype -> new DataDto.Subtype(subtype.getId(), subtype.getSubtype()))
                .collect(Collectors.toList());
    }


    public List<DataDto.Location> convertLocationToDTOs(List<Location> locations) {
        List<DataDto.Location> list = new ArrayList<>();
        for (Location location : locations) {
            list.add(new DataDto.Location(location.getId(), location.getLocation(), location.getBranch().getId()));
        }

        return list;
    }
}
