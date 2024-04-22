package com.alex.inwento.http.inventory;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class InventoryDTO {
    @SerializedName("id")
    private Long id;
    @SerializedName("start_date")
    private LocalDate startDate;
    @SerializedName("finish_date")
    private LocalDate finishDate;
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

    public InventoryDTO() {
    }

    public InventoryDTO(Long id, LocalDate startDate, LocalDate finishDate, String info, int unknownProductAmount, int totalProductAmount, int scannedProductAmount, Boolean isFinished) {
        this.id = id;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.info = info;
        this.unknownProductAmount = unknownProductAmount;
        this.totalProductAmount = totalProductAmount;
        this.scannedProductAmount = scannedProductAmount;
        this.isFinished = isFinished;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

    public int getScannedProductAmount() {
        return scannedProductAmount;
    }

    public void setScannedProductAmount(int scannedProductAmount) {
        this.scannedProductAmount = scannedProductAmount;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    @Override
    public String toString() {
        return "InventoryDTO{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", info='" + info + '\'' +
                ", unknownProductAmount=" + unknownProductAmount +
                ", totalProductAmount=" + totalProductAmount +
                ", scannedProductAmount=" + scannedProductAmount +
                ", isFinished=" + isFinished +
                '}';
    }
}
