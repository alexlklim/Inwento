package com.alex.asset.core.service;


import com.alex.asset.core.domain.Company;
import com.alex.asset.core.domain.Product;
import com.alex.asset.core.dto.ProductDto;
import com.alex.asset.core.mappers.ProductMapper;
import com.alex.asset.core.repo.CompanyRepo;
import com.alex.asset.core.repo.ProductRepo;
import com.alex.asset.security.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final String TAG = "PRODUCT_SERVICE - ";

    private final ProductMapper productMapper;
    private final ProductRepo productRepo;
    private final CompanyRepo companyRepo;
    private final UserRepo userRepo;



    public List<Product> getAll(){
        return productRepo.findAll();
    }
    public List<Product> getActive(){
        return productRepo.getActive();
    }

//    public Product add(ProductDto dto){
//
//    }
//    public Product add(ProductDto dto){
//
//    }




}
