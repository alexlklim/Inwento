package com.alex.inwento.http.inventory;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.List;

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

    public InventoryDTO() {
    }

    public InventoryDTO(Long id, String startDate, String info,
                        int unknownProductAmount, int totalProductAmount, int scannedProductAmount,
                        Boolean isFinished, List<EventDTO> events) {
        this.id = id;
        this.startDate = startDate;

        this.info = info;
        this.unknownProductAmount = unknownProductAmount;
        this.totalProductAmount = totalProductAmount;
        this.scannedProductAmount = scannedProductAmount;
        this.isFinished = isFinished;
        this.events = events;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
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

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "InventoryDTO{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", info='" + info + '\'' +
                ", unknownProductAmount=" + unknownProductAmount +
                ", totalProductAmount=" + totalProductAmount +
                ", scannedProductAmount=" + scannedProductAmount +
                ", isFinished=" + isFinished +
                ", events=" + events +
                '}';
    }
}
