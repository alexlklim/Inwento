package com.alex.asset.configure.services.services;

import com.alex.asset.configure.domain.MPK;
import com.alex.asset.configure.repo.MpkRepo;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.security.domain.User;
import com.alex.asset.utils.dto.DtoData;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MpkService {

    private final String TAG = "FIELD_SERVICE - ";

    private final LogService logService;


    private final MpkRepo mpkRepo;

    public Optional<MPK> getMPKById(Long mpkId){
        return mpkRepo.findById(mpkId);
    }

    public List<MPK> getAll(){
        return mpkRepo.findAll();
    }
    public void createMPK(DtoData dto, User user) {
        log.info(TAG + "Add MPK {}", dto.getName());
        if (mpkRepo.existsByMpk(dto.getName())) return;
        logService.addLog(user, Action.CREATE, Section.MPK, dto.getName());
        mpkRepo.save(new MPK(dto.getName()));
    }


    @SneakyThrows
    public void updateMPK(DtoData dto, User user) {
        log.info(TAG + "Update MPK {}", dto.getId());
        MPK mpk = mpkRepo.findById(dto.getId()).orElseThrow(null);
        if (mpk != null){
            mpk.setActive(dto.isActive());
            mpkRepo.save(mpk);
            logService.addLog(user, Action.UPDATE, Section.MPK, dto.toString());
        }
    }



}
