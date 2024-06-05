package com.alex.asset.product.service;

import com.alex.asset.configure.domain.ContactPerson;
import com.alex.asset.configure.repo.ContactPersonRepo;
import com.alex.asset.configure.repo.ServiceProviderRepo;
import com.alex.asset.exceptions.product.ValueIsNotUnique;
import com.alex.asset.exceptions.shared.ResourceNotFoundException;
import com.alex.asset.product.domain.Activity;
import com.alex.asset.product.domain.ServicedAsset;
import com.alex.asset.product.mappers.ServiceMapper;
import com.alex.asset.product.repo.ProductRepo;
import com.alex.asset.product.repo.ServicedAssetRepo;
import com.alex.asset.security.domain.Role;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.dictionaries.UtilProduct;
import com.alex.asset.utils.dictionaries.UtilsServicedAsset;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SerProductService {
    private final String TAG = "SER_PRODUCT_SERVICE - ";

    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final ServiceProviderRepo serviceProviderRepo;
    private final ContactPersonRepo contactPersonRepo;
    private final ServicedAssetRepo servicedAssetRepo;
    private final ProductHistoryService productHistoryService;



    public Map<String, Object> serviceProduct(Map<String, Object> updates, Long userId) {
        log.info(TAG + "serviceProduct userId " + userId);
        if (updates.containsKey(UtilsServicedAsset.ID)) {
            ServicedAsset servicedAsset = servicedAssetRepo.findById(
                            ((Number) updates.get(UtilProduct.ID)).longValue())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Serviced Asset not found"));
            updates.remove(UtilProduct.ID);
            return updateServicedAsset(updates, servicedAsset, userId);
        }

        return updateServicedAsset(updates, new ServicedAsset(), userId);

    }


    @SneakyThrows
    private Map<String, Object> updateServicedAsset(
            Map<String, Object> updates, ServicedAsset servicedAsset, Long userId)
            throws ValueIsNotUnique {
        log.info(TAG + "updateServicedAsset userId " + userId);
        User user = userRepo.getUser(userId);
        updates.forEach((key, value) -> {
            switch (key) {
                case UtilsServicedAsset.ACTIVE:
                    if (userRepo.getUser(userId).getRoles() == Role.ADMIN) {
                        servicedAsset.setActive((Boolean) value);
                    }
                    break;
                case UtilsServicedAsset.SERVICE_START_DATE:
                    servicedAsset.setServiceStartDate(LocalDate.parse((String) value));
                    break;
                case UtilsServicedAsset.SERVICE_END_DATE:
                    servicedAsset.setServiceEndDate(LocalDate.parse((String) value));
                    break;
                case UtilsServicedAsset.PLANNED_SERVICE_PERIOD:
                    if (value instanceof String) {
                        try {
                            servicedAsset.setPlannedServicePeriod(Integer.parseInt((String) value));
                        } catch (NumberFormatException e) {
                            log.error(TAG + "setPlannedServicePeriod");
                        }
                    } else if (value instanceof Integer) {
                        servicedAsset.setPlannedServicePeriod((Integer) value);
                    }
                    break;
                case UtilsServicedAsset.PRODUCT_ID:
                    servicedAsset.setProduct(
                            productRepo.findById(((Number) value).longValue())
                                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + value)));
                    break;
                case UtilsServicedAsset.SERVICE_PROVIDER_ID:
                    servicedAsset.setServiceProvider(
                            serviceProviderRepo.findById(((Number) value).longValue())
                                    .orElseThrow(() -> new ResourceNotFoundException("Service Provider not found with id " + value)));
                    break;
                case UtilsServicedAsset.CONTACT_PERSON_ID:
                    ContactPerson contactPerson = contactPersonRepo.findById(((Number) value).longValue())
                            .orElseThrow(() -> new ResourceNotFoundException("Contact person not found with id " + value));
                    if (contactPerson.getServiceProvider() != servicedAsset.getServiceProvider())
                        throw new ResourceNotFoundException("Contact person for this service provider not found");
                    servicedAsset.setContactPerson(contactPerson);
                    break;
                case UtilsServicedAsset.SEND_BY_ID:
                    servicedAsset.setSendBy(
                            userRepo.findById(((Number) value).longValue()).orElseThrow(
                                    () -> new ResourceNotFoundException("User not found with id " + value)));
                    break;
                case UtilsServicedAsset.RECEIVER_BY_ID:
                    servicedAsset.setReceivedBy(
                            userRepo.findById(((Number) value).longValue()).orElseThrow(
                                    () -> new ResourceNotFoundException("User not found with id " + value)));
                default:
                    break;
            }
        });
        servicedAsset.getProduct().getProductHistories().add(
                productHistoryService.createProductHistory(user, servicedAsset.getProduct(), Activity.SERVICE));
        servicedAssetRepo.save(servicedAsset);
        return new HashMap<>();
    }

    public List<Map<String, Object>> getAllServicedAsset(List<String> fields, Long userId) {
        log.info(TAG + "getAllServicedAsset userID " + userId);
        return ServiceMapper.toDTOsWithCustomFields(servicedAssetRepo.findAll(), fields);

    }

    @SneakyThrows
    public Map<String, Object> getServicedAssetById(Long assetId, List<String> fields, Long userId) {
        log.info(TAG + "getServicedAssetById userId " + userId);
        return ServiceMapper.toDTOWithCustomFields(
                servicedAssetRepo.findById(assetId).orElseThrow(
                        () -> new ResourceNotFoundException("Serviced asset not found with id " + assetId)
                ), fields
        );
    }
}
