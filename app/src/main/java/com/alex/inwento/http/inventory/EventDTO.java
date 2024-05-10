package com.alex.inwento.http.inventory;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
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
public class EventDTO {
    @SerializedName("id")
    private int id;

    @SerializedName("inventory_id")
    private int inventoryId;
    @SerializedName("info")
    private String info;

    @SerializedName("branch")
    private String branch;
    @SerializedName("start_date")
    private LocalDate startDate;
    @SerializedName("finish_date")
    private LocalDate finishDate;


    @SerializedName("user_id")
    private int userId;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("user_email")
    private String userEmail;



    @SerializedName("scanned_product_amount")
    private int scannedProductAmount;
    @SerializedName("unknown_product_amount")
    private int unknownProductAmount;
    @SerializedName("total_product_amount")
    private int totalProductAmount;


    @SerializedName("scanned_products")
    private List<ProductShortDTO> scannedProducts;

    @SerializedName("not_scanned_products")
    private List<ProductShortDTO> notScannedProducts;

    @SerializedName("unknown_products")
    private List<UnknownProductDTO> unknownProducts;
}
