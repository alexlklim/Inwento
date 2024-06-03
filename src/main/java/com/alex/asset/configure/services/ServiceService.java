package com.alex.asset.configure.services;


import com.alex.asset.configure.domain.ContactPerson;
import com.alex.asset.configure.domain.ServiceProvider;
import com.alex.asset.configure.repo.ContactPersonRepo;
import com.alex.asset.configure.repo.ServiceProviderRepo;
import com.alex.asset.exceptions.product.ValueIsNotUnique;
import com.alex.asset.exceptions.shared.ResourceNotFoundException;
import com.alex.asset.security.domain.Role;
import com.alex.asset.security.domain.User;
import com.alex.asset.utils.dictionaries.UtilConfigurator;
import com.alex.asset.utils.dictionaries.UtilProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ServiceService {
    private final String TAG = "SERVICE_SERVICE - ";

    private final ContactPersonRepo contactPersonRepo;
    private final ServiceProviderRepo serviceProviderRepo;


//    public void addContactPerson(Map<String, Object> updates, User user) {
//        ContactPerson contactPerson = new ContactPerson();
//        contactPerson.setActive(true);
//        contactPersonRepo.save(applyUpdatesForContactPerson(updates, contactPerson, user));
//    }
//    public void updateContactPerson(Map<String, Object> updates, Long contactPersonId, User user) {
//        if (updates.containsKey(UtilConfigurator.ID)) updates.remove(UtilProduct.ID);
//        ContactPerson contactPerson = contactPersonRepo.findById(contactPersonId)
//                .orElseThrow(() -> new ResourceNotFoundException("Contact person not found with id " + contactPersonId));
//        serviceProviderRepo.save(applyUpdatesForServiceProvider(updates, contactPerson, user));
//    }
//    private ContactPerson applyUpdatesForContactPerson(Map<String, Object> updates, ContactPerson contactPerson, User user) {
//    }

    public void addServiceProvider(Map<String, Object> updates, User user) {
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setActive(true);
        serviceProviderRepo.save(applyUpdatesForServiceProvider(updates, serviceProvider, user));
    }

    public void updateServiceProvider(Map<String, Object> updates, Long serviceProviderId, User user) {
        if (updates.containsKey(UtilConfigurator.ID)) updates.remove(UtilProduct.ID);
        ServiceProvider serviceProvider = serviceProviderRepo.findById(serviceProviderId)
                .orElseThrow(() -> new ResourceNotFoundException("Service Provider not found with id " + serviceProviderId));
        serviceProviderRepo.save(applyUpdatesForServiceProvider(updates, serviceProvider, user));
    }

    private ServiceProvider applyUpdatesForServiceProvider(Map<String, Object> updates, ServiceProvider serviceProvider, User user) {
        updates.forEach((key, value) -> {
            switch (key) {
                case UtilConfigurator.ACTIVE -> {
                    if (user.getRoles() == Role.ADMIN) serviceProvider.setActive((Boolean) value);
                }
                case UtilConfigurator.SERVICE_PROVIDER_COMPANY -> {
                    if (serviceProviderRepo.existsByCompany((String) value))
                        throw new ValueIsNotUnique("Company name of service provider is not unique");
                    else serviceProvider.setCompany((String) value);
                }
                case UtilConfigurator.SERVICE_PROVIDER_NIP -> {
                    if (serviceProviderRepo.existsByNip((String) value))
                        throw new ValueIsNotUnique("Nip of service provider is not unique");
                    else serviceProvider.setNip((String) value);
                }
                case UtilConfigurator.SERVICE_PROVIDER_ADDRESS -> {
                    if (serviceProviderRepo.existsByAddress((String) value))
                        throw new ValueIsNotUnique("Address of service provider is not unique");
                    else serviceProvider.setAddress((String) value);
                }
                default -> {}
            }
        });

        return serviceProvider;
    }
}
