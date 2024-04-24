package com.alex.inwento.http.inventory;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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
        return false; // Product with the barcode not found
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

    public ProductShortDTO() {
    }

    public ProductShortDTO(int id, String title, String branch, String location, String barCode, String rfidCode) {
        this.id = id;
        this.title = title;
        this.branch = branch;
        this.location = location;
        this.barCode = barCode;
        this.rfidCode = rfidCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getRfidCode() {
        return rfidCode;
    }

    public void setRfidCode(String rfidCode) {
        this.rfidCode = rfidCode;
    }

    @Override
    public String toString() {
        return "ProductShortDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", branch='" + branch + '\'' +
                ", barCode='" + barCode + '\'' +
                '}';
    }



}
