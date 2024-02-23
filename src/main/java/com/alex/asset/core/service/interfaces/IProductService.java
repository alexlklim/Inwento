package com.alex.asset.core.service.interfaces;

import com.alex.asset.core.dto.Dto;
import com.alex.asset.core.dto.ProductDto;
import com.alex.asset.core.dto.ShortProduct;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IProductService {



    List<ProductDto> getAllByCompany(Long companyId);
    // get company
    // get all visible product for this company

    List<ShortProduct> filterGetAllByCompanyAndTitle(Long companyId, String title);
    // get company
    // get all visible product for this company with specific title


    Long addEmptyProductForCompany(Long companyId, Long userId);
    ProductDto updateProductForCompany(Long companyId, ProductDto dto);
    ProductDto getProductForCompanyById(Long companyId, Long id);

    boolean makeInvisible(Long companyId, Long productId);
    // check if product belong this company
    // make it invisible



    void deleteProductForCompanyById(Long companyId, Long productId);
    // check if product belong this company
    // delete product




}
