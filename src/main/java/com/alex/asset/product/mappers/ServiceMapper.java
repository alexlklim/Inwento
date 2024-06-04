package com.alex.asset.product.mappers;

import com.alex.asset.product.domain.ServicedAsset;
import com.alex.asset.utils.dictionaries.UtilProduct;
import com.alex.asset.utils.dictionaries.UtilsServicedAsset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ServiceMapper {

    public static Map<String, Object> toDTOWithCustomFields(
            ServicedAsset servicedAsset, List<String> fields) {
        Map<String, Object> dtoMap = new HashMap<>();
        Map<String, Supplier<Object>> df = new HashMap<>();

        df.put(UtilsServicedAsset.SERVICE_START_DATE,
                () -> servicedAsset.getServiceStartDate() != null ? servicedAsset.getServiceStartDate() : "");
        df.put(UtilsServicedAsset.SERVICE_END_DATE,
                () -> servicedAsset.getServiceEndDate() != null ? servicedAsset.getServiceEndDate() : "");
        df.put(UtilsServicedAsset.PLANNED_SERVICE_PERIOD,
                () -> servicedAsset.getPlannedServicePeriod() != null ? servicedAsset.getPlannedServicePeriod() : "");
        df.put(UtilsServicedAsset.DELIVERY,
                () -> servicedAsset.getDelivery() != null ? servicedAsset.getDelivery() : "");

        df.put(UtilsServicedAsset.PRODUCT_ID,
                () -> servicedAsset.getProduct() != null ? servicedAsset.getProduct().getTitle() : "");
        df.put(UtilsServicedAsset.PRODUCT,
                () -> servicedAsset.getProduct() != null ?
                        ProductMapper.toDTOWithCustomFields(
                                servicedAsset.getProduct(),
                                UtilProduct.getFieldsShortView()) : "");


        df.put(UtilsServicedAsset.SERVICE_PROVIDER_ID,
                () -> servicedAsset.getServiceProvider() != null ? servicedAsset.getServiceProvider().getId() : "");
        df.put(UtilsServicedAsset.SERVICE_PROVIDER_COMPANY,
                () -> servicedAsset.getServiceProvider() != null ? servicedAsset.getServiceProvider().getCompany() : "");
        df.put(UtilsServicedAsset.SERVICE_PROVIDER_NIP,
                () -> servicedAsset.getServiceProvider() != null ? servicedAsset.getServiceProvider().getNip() : "");
        df.put(UtilsServicedAsset.SERVICE_PROVIDER_ADDRESS,
                () -> servicedAsset.getServiceProvider() != null ? servicedAsset.getServiceProvider().getAddress() : "");

        df.put(UtilsServicedAsset.CONTACT_PERSON_ID,
                () -> servicedAsset.getContactPerson() != null ? servicedAsset.getContactPerson().getId() : "");
        df.put(UtilsServicedAsset.CONTACT_PERSON_NAME,
                () -> servicedAsset.getContactPerson() != null ?
                        servicedAsset.getContactPerson().getLastname() + " " + servicedAsset.getContactPerson().getLastname() : "");
        df.put(UtilsServicedAsset.CONTACT_PERSON_PHONE_NUMBER,
                () -> servicedAsset.getContactPerson() != null ? servicedAsset.getContactPerson().getPhoneNumber() : "");
        df.put(UtilsServicedAsset.CONTACT_PERSON_EMAIL,
                () -> servicedAsset.getContactPerson() != null ? servicedAsset.getContactPerson().getEmail() : "");


        df.put(UtilsServicedAsset.SEND_BY_ID,
                () -> servicedAsset.getSendBy() != null ? servicedAsset.getSendBy().getId() : "");
        df.put(UtilsServicedAsset.SEND_BY_NAME,
                () -> servicedAsset.getSendBy() != null ?
                        servicedAsset.getSendBy().getFirstname() + " " + servicedAsset.getSendBy().getLastname()  : "");
        df.put(UtilsServicedAsset.SEND_BY_EMAIL,
                () -> servicedAsset.getSendBy() != null ? servicedAsset.getSendBy().getEmail() : "");



        df.put(UtilsServicedAsset.RECEIVER_BY_ID,
                () -> servicedAsset.getReceivedBy() != null ? servicedAsset.getReceivedBy().getId() : "");
        df.put(UtilsServicedAsset.RECEIVER_BY_NAME,
                () -> servicedAsset.getReceivedBy() != null ?
                        servicedAsset.getReceivedBy().getFirstname() + " " + servicedAsset.getReceivedBy().getLastname()  : "");
        df.put(UtilsServicedAsset.RECEIVER_BY_EMAIL,
                () -> servicedAsset.getReceivedBy() != null ? servicedAsset.getReceivedBy().getEmail() : "");

        fields.forEach(field -> dtoMap.put(field, df.getOrDefault(field, () -> "").get()));


        dtoMap.put(UtilsServicedAsset.ID, servicedAsset.getId());
        dtoMap.put(UtilsServicedAsset.ACTIVE, servicedAsset.isActive());
        return dtoMap;
    }



    public static List<Map<String, Object>> toDTOsWithCustomFields(List<ServicedAsset> servicedAssets, List<String> fields) {
       return servicedAssets.stream()
               .map(servicedAsset -> toDTOWithCustomFields(servicedAsset, fields))
               .toList();
    }
}
