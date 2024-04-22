package com.alex.inwento.http.inventory;

import com.google.gson.annotations.SerializedName;

public class ProductDTO {

    @SerializedName("id")
    private Long id;
    @SerializedName("title")
    private String title;
    @SerializedName("price")
    private double price;
    @SerializedName("active")
    private boolean active;


    @SerializedName("branch_id")
    private int branchId;
    @SerializedName("branch")
    private String branch;

    @SerializedName("rfid_code")
    private String rfidCode;
    @SerializedName("bar_code")
    private String barCode;

    @SerializedName("liable_name")
    private String liableName;
    @SerializedName("receiver")
    private String receiver;


    public ProductDTO() {
    }

    public ProductDTO(Long id, String title, double price, boolean active, int branchId, String branch, String rfidCode, String barCode, String liableName, String receiver) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.active = active;
        this.branchId = branchId;
        this.branch = branch;
        this.rfidCode = rfidCode;
        this.barCode = barCode;
        this.liableName = liableName;
        this.receiver = receiver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getRfidCode() {
        return rfidCode;
    }

    public void setRfidCode(String rfidCode) {
        this.rfidCode = rfidCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getLiableName() {
        return liableName;
    }

    public void setLiableName(String liableName) {
        this.liableName = liableName;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", active=" + active +
                ", branchId=" + branchId +
                ", branch='" + branch + '\'' +
                ", rfidCode='" + rfidCode + '\'' +
                ", barCode='" + barCode + '\'' +
                ", liableName='" + liableName + '\'' +
                ", receiver='" + receiver + '\'' +
                '}';
    }
}
