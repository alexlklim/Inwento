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
public class InventoryDTO {
    @SerializedName("id")
    private Long id;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("info")
    private String info;
    @SerializedName("unknown_product_amount")
    private int unknownProductAmount;
    @SerializedName("total_product_amount")
    private int totalProductAmount;
    @SerializedName("scanned_product_amount")
    private int scannedProductAmount;
    @SerializedName("finished")
    private Boolean isFinished;

    @SerializedName("events")
    private List<EventDTO> events;

}
