package com.alex.inwento.database.domain;

public class Inventory {
    private int id;
    private String startDate;
    private String finishDate;
    private String info;
    private boolean isFinished;
    private int unknownProductAmount;
    private int totalProductAmount;
    private int scannedProductAmount;


    public Inventory() {
    }

    public Inventory(int id, String startDate, String finishDate, String info,
                     boolean isFinished, int unknownProductAmount,
                     int totalProductAmount, int scannedProductAmount) {
        this.id = id;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.info = info;
        this.isFinished = isFinished;
        this.unknownProductAmount = unknownProductAmount;
        this.totalProductAmount = totalProductAmount;
        this.scannedProductAmount = scannedProductAmount;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
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


    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", startDate='" + startDate + '\'' +
                ", finishDate='" + finishDate + '\'' +
                ", info='" + info + '\'' +
                ", isFinished=" + isFinished +
                ", unknownProductAmount=" + unknownProductAmount +
                ", totalProductAmount=" + totalProductAmount +
                ", scannedProductAmount=" + scannedProductAmount +
                '}';
    }
}
