package com.alex.asset.configure.services;


import com.alex.asset.configure.domain.ContactPerson;
import com.alex.asset.configure.domain.ServiceProvider;
import com.alex.asset.configure.repo.ContactPersonRepo;
import com.alex.asset.configure.repo.ServiceProviderRepo;
import com.alex.asset.exceptions.product.ValueIsNotUniqueException;
import com.alex.asset.exceptions.shared.ResourceNotFoundException;
import com.alex.asset.security.domain.Role;
import com.alex.asset.security.domain.User;
import com.alex.asset.utils.dictionaries.UtilConfigurator;
import com.alex.asset.utils.dictionaries.UtilProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ServiceService {
    private final String TAG = "SERVICE_SERVICE - ";

    private final ContactPersonRepo contactPersonRepo;
    private final ServiceProviderRepo serviceProviderRepo;


    public void addContactPerson(Map<String, Object> updates, User user) {
        log.info(TAG + "addContactPerson");
        ContactPerson contactPerson = new ContactPerson();
        contactPerson.setActive(true);
        contactPersonRepo.save(applyUpdatesForContactPerson(updates, contactPerson, user));
    }

    public void updateContactPerson(Map<String, Object> updates, Long contactPersonId, User user) {
        log.info(TAG + "updateContactPerson");
        if (updates.containsKey(UtilConfigurator.ID)) updates.remove(UtilProduct.ID);
        ContactPerson contactPerson = contactPersonRepo.findById(contactPersonId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact person not found with id " + contactPersonId));
        contactPersonRepo.save(applyUpdatesForContactPerson(updates, contactPerson, user));
    }

    private ContactPerson applyUpdatesForContactPerson(
            Map<String, Object> updates, ContactPerson contactPerson, User user) {
        log.info(TAG + "applyUpdatesForContactPerson");
        System.out.println(contactPerson);
        System.out.println(updates);
        updates.forEach((key, value) -> {
            switch (key) {
                case UtilConfigurator.ACTIVE -> {
                    if (user.getRoles() == Role.ADMIN) contactPerson.setActive((Boolean) value);}
                case UtilConfigurator.CONTACT_PERSON_FIRSTNAME -> contactPerson.setFirstname((String) value);
                case UtilConfigurator.CONTACT_PERSON_LASTNAME -> contactPerson.setLastname((String) value);
                case UtilConfigurator.CONTACT_PERSON_PHONE_NUMBER -> {
                    if (contactPerson.getPhoneNumber() != null &&
                            !contactPerson.getPhoneNumber().equalsIgnoreCase((String) value) &&
                            contactPersonRepo.existsByPhoneNumber((String) value)) {
                        throw new ValueIsNotUniqueException("Phone number of contact person is not unique");
                    } else if (contactPerson.getPhoneNumber() == null && contactPersonRepo.existsByPhoneNumber((String) value)) {
                        throw new ValueIsNotUniqueException("Phone number of contact person is not unique");
                    } else {
                        contactPerson.setPhoneNumber((String) value);
                    }
                }
                case UtilConfigurator.CONTACT_PERSON_EMAIL -> {
                    if (contactPerson.getEmail() != null &&
                            !contactPerson.getEmail().equalsIgnoreCase((String) value) &&
                            contactPersonRepo.existsByEmail((String) value)) {
                        throw new ValueIsNotUniqueException("Email of contact person is not unique");
                    } else if (contactPerson.getEmail() == null && contactPersonRepo.existsByEmail((String) value)) {
                        throw new ValueIsNotUniqueException("Email of contact person is not unique");
                    } else {
                        contactPerson.setEmail((String) value);
                    }
                }
                case UtilConfigurator.CONTACT_PERSON_SERVICE_PROVIDER_ID -> {
                    ServiceProvider serviceProvider = serviceProviderRepo.findById(((Number) value).longValue()).orElseThrow(
                                   () -> new ResourceNotFoundException("Service provider not found with id " + value));
                    contactPerson.setServiceProvider(serviceProvider);
                    serviceProvider.getContactPersons().add(contactPerson);
                    serviceProviderRepo.save(serviceProvider);
                    contactPersonRepo.save(contactPerson);
                }
                default -> {}
            }
        });
        return contactPerson;
    }



    public void addServiceProvider(Map<String, Object> updates, User user) {
        log.info(TAG + "addServiceProvider");
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setActive(true);
        serviceProviderRepo.save(applyUpdatesForServiceProvider(updates, serviceProvider, user));
    }

    public void updateServiceProvider(
            Map<String, Object> updates, Long serviceProviderId, User user) {
        log.info(TAG + "updateServiceProvider");
        if (updates.containsKey(UtilConfigurator.ID)) updates.remove(UtilProduct.ID);
        ServiceProvider serviceProvider = serviceProviderRepo.findById(serviceProviderId)
                .orElseThrow(() -> new ResourceNotFoundException("Service Provider not found with id " + serviceProviderId));
        serviceProviderRepo.save(applyUpdatesForServiceProvider(updates, serviceProvider, user));
    }

    private ServiceProvider applyUpdatesForServiceProvider(Map<String, Object> updates, ServiceProvider serviceProvider, User user) {
        log.info(TAG + "applyUpdatesForServiceProvider");
        updates.forEach((key, value) -> {
            switch (key) {
                case UtilConfigurator.ACTIVE -> {
                    if (user.getRoles() == Role.ADMIN) serviceProvider.setActive((Boolean) value);
                }
                case UtilConfigurator.SERVICE_PROVIDER_COMPANY -> {
                    if (serviceProvider.getCompany() != null &&
                            !serviceProvider.getCompany().equalsIgnoreCase((String) value) &&
                            serviceProviderRepo.existsByCompany((String) value)) {
                        throw new ValueIsNotUniqueException("Company name of service provider is not unique");
                    } else if (serviceProvider.getCompany() == null && serviceProviderRepo.existsByCompany((String) value)) {
                        throw new ValueIsNotUniqueException("Company name of service provider is not unique");
                    } else {
                        serviceProvider.setCompany((String) value);
                    }
                }
                case UtilConfigurator.SERVICE_PROVIDER_NIP -> {
                    if (serviceProvider.getNip() != null &&
                            !serviceProvider.getNip().equalsIgnoreCase((String) value) &&
                            serviceProviderRepo.existsByNip((String) value)) {
                        throw new ValueIsNotUniqueException("Nip of service provider is not unique");
                    } else if (serviceProvider.getNip() == null && serviceProviderRepo.existsByNip((String) value)) {
                        throw new ValueIsNotUniqueException("Nip of service provider is not unique");
                    } else {
                        serviceProvider.setNip((String) value);
                    }
                }
                case UtilConfigurator.SERVICE_PROVIDER_ADDRESS -> {
                    if (serviceProvider.getAddress() != null &&
                            !serviceProvider.getAddress().equalsIgnoreCase((String) value) &&
                            serviceProviderRepo.existsByAddress((String) value)) {
                        throw new ValueIsNotUniqueException("Address of service provider is not unique");
                    } else if (serviceProvider.getAddress() == null && serviceProviderRepo.existsByAddress((String) value)) {
                        throw new ValueIsNotUniqueException("Address of service provider is not unique");
                    } else {
                        serviceProvider.setAddress((String) value);
                    }
                }

                default -> {}
            }
        });

        return serviceProvider;
    }

    public List<ServiceProvider> getAllServiceProviders() {
        return serviceProviderRepo.findAll();
    }

    public Object getAllActiveServiceProviders() {
        return serviceProviderRepo.getActive();
    }
}
