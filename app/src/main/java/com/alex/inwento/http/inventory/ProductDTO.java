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
    @SerializedName("active")
    private boolean active;


    @SerializedName("scrapping")
    private boolean isScrapped;


    @SerializedName("branch_id")
    private int branchId;
    @SerializedName("branch")
    private String branch;


    @SerializedName("location_id")
    private Long locationId;
    @SerializedName("location")
    private String location;

    @SerializedName("rfid_code")
    private String rfidCode;
    @SerializedName("bar_code")
    private String barCode;

    @SerializedName("serial_number")
    private String serialNumber;


    @SerializedName("liable_name")
    private String liableName;
    @SerializedName("receiver")
    private String receiver;

    @SerializedName("producer")
    private String producer;




}
