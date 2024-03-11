package com.alex.asset.invents.service;


import com.alex.asset.invents.dto.InventDto;
import com.alex.asset.invents.dto.InventV1Representation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class InventService {


    public boolean startInvent(Long userId, InventDto dto){
        return false;
    }


    public boolean finishInvent(){
        return false;
    }



    public void stopInvent(){
        return;
    }


    public boolean updateInvent(Long inventId , InventDto dto){
        return false;
    }

    public InventV1Representation isAnyInventActive() {

        return null;
    }
}
