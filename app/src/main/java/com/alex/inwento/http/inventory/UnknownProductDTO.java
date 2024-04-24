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
public class UnknownProductDTO {
    @SerializedName("code")
    String code;

    @SerializedName("type_code")
    String typeCode;


    public static boolean doesProductExist(List<UnknownProductDTO> productList, String codeToFind) {
        for (UnknownProductDTO product : productList) {
            if (product.getCode() != null && product.getCode().equalsIgnoreCase(codeToFind)) {
                return true; // Found the product with the code
            }
        }
        return false; // Product with the code not found
    }
}
