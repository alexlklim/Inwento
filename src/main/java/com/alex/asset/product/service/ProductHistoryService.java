package com.alex.asset.product.service;


import com.alex.asset.product.domain.Activity;
import com.alex.asset.product.domain.Product;
import com.alex.asset.product.domain.ProductHistory;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductHistoryService {

    private final String TAG = "PRODUCT_HISTORY_SERVICE - ";
    private final UserRepo userRepo;



    public ProductHistory createProductHistory(Long userId, Product product, Activity activity) {
        log.info(TAG + "createProductHistory");
        return createProductHistory(userRepo.getUser(userId), product, activity);
    }

    public ProductHistory createProductHistory(User user, Product product, Activity activity) {
        log.info(TAG + "createProductHistory");
        return new ProductHistory().toBuilder()
                .activity(activity)
                .created(LocalDateTime.now())
                .product(product)
                .user(user)
                .build();
    }

}
