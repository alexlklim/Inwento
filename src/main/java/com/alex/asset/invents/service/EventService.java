package com.alex.asset.invents.service;


import com.alex.asset.invents.dto.InventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EventService {


    public boolean startEvent(InventDto dto){
        return false;
    }


    public boolean finishEvent(){
        return false;
    }



    public boolean updateEvent(Long inventId ,InventDto dto){
        return false;
    }


    public void changeEventVisibility(Long inventId){
        return;
    }


}
