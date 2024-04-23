package com.alex.inwento.http.inventory;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.List;

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
    private List<Object> scannedProducts;
    @SerializedName("not_scanned_products")
    private List<Object> notScannedProducts;
    @SerializedName("unknown_products")
    private List<Object> unknownProducts;


    public EventDTO() {
    }

    public EventDTO(int id, int inventoryId, String info, String branch, LocalDate startDate,
                    LocalDate finishDate, int userId, String userName, String userEmail,
                    int scannedProductAmount, int unknownProductAmount, int totalProductAmount,
                    List<Object> scannedProducts, List<Object> notScannedProducts, List<Object> unknownProducts) {
        this.id = id;
        this.inventoryId = inventoryId;
        this.info = info;
        this.branch = branch;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.scannedProductAmount = scannedProductAmount;
        this.unknownProductAmount = unknownProductAmount;
        this.totalProductAmount = totalProductAmount;
        this.scannedProducts = scannedProducts;
        this.notScannedProducts = notScannedProducts;
        this.unknownProducts = unknownProducts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getScannedProductAmount() {
        return scannedProductAmount;
    }

    public void setScannedProductAmount(int scannedProductAmount) {
        this.scannedProductAmount = scannedProductAmount;
    }

    public int getUnknownProductAmount() {
        return unknownProductAmount;
    }

    public void setUnknownProductAmount(int unknownProductAmount) {
        this.unknownProductAmount = unknownProductAmount;
    }

    public int getTotalProductAmount() {
        return totalProductAmount;
    }

    public void setTotalProductAmount(int totalProductAmount) {
        this.totalProductAmount = totalProductAmount;
    }

    public List<Object> getScannedProducts() {
        return scannedProducts;
    }

    public void setScannedProducts(List<Object> scannedProducts) {
        this.scannedProducts = scannedProducts;
    }

    public List<Object> getNotScannedProducts() {
        return notScannedProducts;
    }

    public void setNotScannedProducts(List<Object> notScannedProducts) {
        this.notScannedProducts = notScannedProducts;
    }

    public List<Object> getUnknownProducts() {
        return unknownProducts;
    }

    public void setUnknownProducts(List<Object> unknownProducts) {
        this.unknownProducts = unknownProducts;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Override
    public String toString() {
        return "EventDTO{" +
                "id=" + id +
                ", inventoryId=" + inventoryId +
                ", info='" + info + '\'' +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", scannedProductAmount=" + scannedProductAmount +
                ", unknownProductAmount=" + unknownProductAmount +
                ", totalProductAmount=" + totalProductAmount +
                ", scannedProducts=" + scannedProducts +
                ", notScannedProducts=" + notScannedProducts +
                ", unknownProducts=" + unknownProducts +
                '}';
    }
}
