package com.alex.inwento.http.inventory;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.List;

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
    public UnknownProductDTO() {
    }

    public UnknownProductDTO(String code, String typeCode) {
        this.code = code;
        this.typeCode = typeCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }


    @Override
    public String toString() {
        return "UnknownProductDTO{" +
                "code='" + code + '\'' +
                ", typeCode='" + typeCode + '\'' +
                '}';
    }
}
