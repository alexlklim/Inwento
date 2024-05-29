package com.alex.asset.product.mappers;

import com.alex.asset.product.domain.ProductHistory;
import com.alex.asset.product.dto.ProductHistoryDto;

import java.util.List;

public class ProductHistoryMapper {

    public static List<ProductHistoryDto> toDTOs(List<ProductHistory> productHistories){
        return productHistories.stream().map(ProductHistoryMapper::toDTO).toList();
    }

    public static ProductHistoryDto toDTO(ProductHistory productHistory){
        return new ProductHistoryDto().toBuilder()
                .created(productHistory.getCreated())
                .username(productHistory.getUser().getFirstname() + productHistory.getUser().getLastname())
                .email(productHistory.getUser().getEmail())
                .activity(productHistory.getActivity())
                .build();
    }

}
