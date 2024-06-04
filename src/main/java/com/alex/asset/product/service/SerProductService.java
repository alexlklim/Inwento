package com.alex.asset.product.service;

import com.alex.asset.configure.domain.ContactPerson;
import com.alex.asset.configure.repo.ContactPersonRepo;
import com.alex.asset.configure.repo.ServiceProviderRepo;
import com.alex.asset.exceptions.product.ValueIsNotUnique;
import com.alex.asset.exceptions.shared.ResourceNotFoundException;
import com.alex.asset.product.domain.ServicedAsset;
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
import java.util.Map;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SerProductService {
    private final String TAG = "PRODUCT_SERVICE - ";

    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final ServiceProviderRepo serviceProviderRepo;
    private final ContactPersonRepo contactPersonRepo;
    private final ServicedAssetRepo servicedAssetRepo;



    public Map<String, Object> serviceProduct(Map<String, Object> updates, Long userId) {
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
        log.info(TAG + "Update product by user with id {}", userId);
        User user = userRepo.getUser(userId);
        System.out.println(updates);
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
        servicedAssetRepo.save(servicedAsset);
        return new HashMap<>();
    }

}
