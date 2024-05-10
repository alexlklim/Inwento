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

}
