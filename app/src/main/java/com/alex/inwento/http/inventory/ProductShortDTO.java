package com.alex.inwento.http.inventory;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductShortDTO {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;

    @SerializedName("active")
    private Boolean active;

    @SerializedName("branch")
    private String branch;

    @SerializedName("location")
    private String location;

    @SerializedName("bar_code")
    private String barCode;
    @SerializedName("rfid_code")
    private String rfidCode;

    public static boolean doesProductExist(List<ProductShortDTO> productList, String barcodeToFind) {
        for (ProductShortDTO product : productList) {
            if (product.getBarCode() != null && product.getBarCode().equalsIgnoreCase(barcodeToFind)) {
                return true; // Found the product with the barcode
            }
        }
        return false;
    }


    public static ProductShortDTO getIndexByIdInList(List<ProductShortDTO> productList, int idToFind) {
        System.out.println("AAAAAAAAAA: " + "getIndexByIdInList");

        for (ProductShortDTO dto: productList){
            if (dto.getId() == idToFind){
                System.out.println("AAAAAAAAAA: " + productList.indexOf(dto));
                return dto;
            }
        }
        return null;
    }
}
