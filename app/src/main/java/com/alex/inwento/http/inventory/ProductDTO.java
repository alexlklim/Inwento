package com.alex.inwento.http.inventory;

import com.google.gson.annotations.SerializedName;

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
public class ProductDTO {

    @SerializedName("id")
    private int id;

    @SerializedName("description")
    private String description;

    @SerializedName("title")
    private String title;


    // branch
    @SerializedName("branch_id")
    private int branchId;
    @SerializedName("branch")
    private String branch;
    //location
    @SerializedName("location_id")
    private Long locationId;
    @SerializedName("location")
    private String location;

    // unique data
    @SerializedName("rfid_code")
    private String rfidCode;
    @SerializedName("bar_code")
    private String barCode;
    @SerializedName("serial_number")
    private String serialNumber;


    // who use it
    @SerializedName("liable_name")
    private String liableName;
    @SerializedName("receiver")
    private String receiver;

    // producer and supplier
    @SerializedName("producer")
    private String producer;
    @SerializedName("supplier")
    private String supplier;


    // documents
    @SerializedName("document_date")
    private boolean documentDate;
    @SerializedName("warranty_period")
    private boolean warrantyPeriod;
    @SerializedName("inspection_date")
    private boolean inspectionDate;


    // scrapping
    @SerializedName("scrapping")
    private boolean isScrapped;
    @SerializedName("scrapping_date")
    private boolean scrappingDate;
    @SerializedName("scrapping_reason")
    private boolean scrappingReason;

}
