package com.alex.asset.configure.services;


import com.alex.asset.company.service.CompanyRepo;
import com.alex.asset.configure.domain.ServiceProvider;
import com.alex.asset.configure.repo.ContactPersonRepo;
import com.alex.asset.configure.repo.ServiceProviderRepo;
import com.alex.asset.configure.services.ConfigureService;
import com.alex.asset.configure.services.services.BranchService;
import com.alex.asset.configure.services.services.MpkService;
import com.alex.asset.configure.services.services.TypeService;
import com.alex.asset.inventory.repo.EventRepo;
import com.alex.asset.inventory.repo.InventoryRepo;
import com.alex.asset.inventory.repo.ScannedProductRepo;
import com.alex.asset.logs.LogService;
import com.alex.asset.product.domain.Activity;
import com.alex.asset.product.domain.Comment;
import com.alex.asset.product.repo.ProductRepo;
import com.alex.asset.product.service.CommentService;
import com.alex.asset.security.domain.Role;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.dictionaries.UtilConfigurator;
import com.alex.asset.utils.dictionaries.UtilProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ServiceService {
    private final String TAG = "SERVICE_SERVICE - ";

    private final ContactPersonRepo contactPersonRepo;
    private final ServiceProviderRepo serviceProviderRepo;


    public void addServiceProvider(Map<String, Object> map, User user) {
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setActive(true);
//        updateServiceProvider(map, serviceProviderRepo.save(serv));

    }

    public void updateServiceProvider(Map<String, Object> updates, Long serviceProviderId, User user) {
        updates.remove(UtilProduct.ID);
        ServiceProvider serviceProvider = serviceProviderRepo.findById(serviceProviderId)
                .orElseThrow(() -> new ResolutionException("Service Provider not found with id " + serviceProviderId));

        updates.forEach((key, value) -> {
            switch (key) {
                case UtilConfigurator.ACTIVE:
                    if (user.getRoles() == Role.ADMIN) serviceProvider.setActive((Boolean) value);
                    break;
                case UtilConfigurator.SERVICE_PROVIDER_COMPANY: serviceProvider.setCompany((String) value); break;
                case UtilConfigurator.SERVICE_PROVIDER_NIP: serviceProvider.setNip((String) value); break;
                case UtilConfigurator.SERVICE_PROVIDER_ADDRESS: serviceProvider.setAddress((String) value); break;
                default: break;
            }
        });



    }
}
