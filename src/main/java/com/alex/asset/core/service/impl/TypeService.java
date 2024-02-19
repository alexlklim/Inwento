package com.alex.asset.core.service.impl;


import com.alex.asset.core.repo.product.type.SubtypeRepo;
import com.alex.asset.core.repo.product.type.TypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TypeService {
    private final TypeRepo typeRepo;
    private final SubtypeRepo subtypeRepo;


//    public boolean addType(List<String> list){
//        for (String str: list){
//            typeRepo.save(new Type(str));
//        }
//    }
}
